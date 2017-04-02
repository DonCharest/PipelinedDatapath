package PipelinedDatapath;

public class Pipeline 
{
	Main_Memory main_mem; Registers regs; IF_ID if_id;
	ID_EX id_ex; EX_MEM ex_mem; MEM_WB mem_wb;
	
	public Pipeline()
	{
		main_mem = new Main_Memory();
		regs = new Registers();
		if_id = new IF_ID();
		id_ex = new ID_EX();
		ex_mem = new EX_MEM();
		mem_wb = new MEM_WB();
	}
	
	
	/**
	 * Method to fetch the next instruction out of the InstructionCache[]
	 * and put it into the WRITE version of the IF_ID Pipeline.
	 * @param cycle
	 */
	public void IF_stage(int cycle)
	{
		if_id.setWriteValue(0, Simulation.getInstruction(cycle));
	}
	
	
	/**
	 * Method to read an instruction from the READ version of the IF_ID 
	 * pipeline, do the decoding and register fetching and write the 
	 * values to the WRITE version of the ID_EX pipeline register.
	 */
	public void ID_stage()
	{
		int instruction = if_id.getReadValue( 0 );

		if ( instruction == 0 )
		{
			id_ex.setWriteValue( 0, 0 );
		} 
		else
		{
			id_ex.setWriteValue( 0, 1 );
		}

		int readReg1 = ( instruction & 0x3E00000 ) >>> 21;  //set readReg1  (srcRS)
		int readReg2 = ( instruction & 0x1F0000 ) >>> 16;   //set readReg2  (srcRT)

		// fetch the data in the regs and write to ID/EX pipeline register
		id_ex.setWriteValue(8, regs.getRegister(readReg1));
		id_ex.setWriteValue(9, regs.getRegister(readReg2));

		//function & set control signals
		decode( instruction );

		// find offset and sign extend
		int offset_15_0 = instruction & 0xFFFF;

		if ((( offset_15_0 & 0x8000 ) >>> 15 ) == 1 )
		{   //if the 16th bit (leading bit) is 1,
			offset_15_0 = offset_15_0 + 0xFFFF0000; //then sign extend
		}

		id_ex.setWriteValue( 10, offset_15_0 );

		// instruction bits 20_16
		id_ex.setWriteValue( 11, ( instruction & 0x1F0000 ) >>> 16 ); 

		// instruction bits 15_11
		id_ex.setWriteValue( 12, ( instruction & 0xF800 ) >>> 11 );
	}

	
	/**
	 * This method takes in the instruction and uses bit-mapping
	 * to determine what the function is and then generates the 
	 * control signals. All of this data is stored in the IDEX 
	 * pipeline write array.
	 * @param instr
	 */
	private void decode( int instr )
	{
		
		if ( instr != 0 && ( instr >>> 26 ) == 0 ) // R-Type Instructions - checks function codes
		{
			switch( instr & 0x3f )
			{
			case 0x20: //add - addition
				id_ex.setWriteValue( 13, 0x20 );  	//function
				//set control
				break;

			case 0x22: //sub - subtraction
				id_ex.setWriteValue( 13, 0x22 );	
				break;
			}

			id_ex.setWriteValue(1, 1); //regDst
			id_ex.setWriteValue(2, 0b10); //aluOp
			id_ex.setWriteValue(3, 0); //aluSrc
			id_ex.setWriteValue(4, 0); //memRead
			id_ex.setWriteValue(5, 0); //memWrite
			id_ex.setWriteValue(6, 1); //regWrite
			id_ex.setWriteValue(7, 0); //memToReg
		} 
			
			else // I-Type instructions - checks op codes
		{
			switch ( instr >>> 26 )
			{
			case ( 0x20 ): //lb - load byte
				id_ex.setWriteValue(13, 0x20); //function
				id_ex.setWriteValue(1, 0); //regDst
				id_ex.setWriteValue(2, 0); //aluOp
				id_ex.setWriteValue(3, 1); //aluSrc
				id_ex.setWriteValue(4, 1); //memRead
				id_ex.setWriteValue(5, 0); //memWrite
				id_ex.setWriteValue(6, 1); //regWrite
				id_ex.setWriteValue(7, 1); //memToReg
				break;
				
			case ( 0x28 ): //sb - store byte
				id_ex.setWriteValue(13, 0x28); //function
				id_ex.setWriteValue(1, 0); //regDst
				id_ex.setWriteValue(2, 0); //aluOp
				id_ex.setWriteValue(3, 1); //aluSrc
				id_ex.setWriteValue(4, 0); //memRead
				id_ex.setWriteValue(5, 1); //memWrite
				id_ex.setWriteValue(6, 0); //regWrite
				id_ex.setWriteValue(7, 0); //memToReg
				break;
			}
		}
		
	} //End Method: decode
	

	/**
	 * Method to perform the requested instruction on the specified operands that 
	 * are read out of the ID/EX pipeline register and then rights the appropriate 
	 * values to the WRITE version of the EX/MEM pipeline register.
	 */
	public void EX_stage()
	{
		if ( id_ex.getReadValue( 0 ) == 1 )
		{
			ex_mem.setWriteValue( 0, 1 );
		} 
		else 
		{
			ex_mem.setWriteValue( 0, 0 );
		}
		//write control signals to pipeline
		ex_mem.setWriteValue( 1, id_ex.getReadValue( 4 ) );  //memRead
		ex_mem.setWriteValue( 2, id_ex.getReadValue( 5 ) );  //memWrite
		ex_mem.setWriteValue( 3, id_ex.getReadValue( 7 ) );  //memToReg
		ex_mem.setWriteValue( 4, id_ex.getReadValue( 6 ) );  //regWrite

		//regDst mux
		if ( id_ex.getReadValue( 1 ) == 0 ) //regDst = "1"
		{
			ex_mem.setWriteValue( 5, id_ex.getReadValue( 11 ) ); //writeReg_20_16
		} 
		else //regDst = "0"
		{
			ex_mem.setWriteValue( 5, id_ex.getReadValue( 12 ) ); //writeReg_15_11
		}

		int rightOperand;
		if ( id_ex.getReadValue( 3 ) == 1 )
		{
			rightOperand = id_ex.getReadValue(10); //offset
		} 
		else 
		{
			rightOperand = id_ex.getReadValue(9); //readReg2
		}

		if ( id_ex.getReadValue( 2 ) == 0 )
		{	
			ex_mem.setWriteValue(6, id_ex.getReadValue(8) + rightOperand); //aluResult = readData1 + rightOperand
		} 
		else 
		{
			if( id_ex.getReadValue( 13 ) == 0x22 ) //function code = "sub"
			{
				ex_mem.setWriteValue( 6, id_ex.getReadValue( 8 ) - rightOperand );
			} 
			else //if not sub, function code must = "add"
			{
				ex_mem.setWriteValue( 6, id_ex.getReadValue( 8 ) + rightOperand );
			}
		}
		ex_mem.setWriteValue( 7, id_ex.getReadValue( 9 ) );

	} //End Method: EX_stage


	/**
	 * For load byte instructions: this Method will index into Main_Mem using address 
	 * calculated in the EX stage and get the value stored in the array.
	 * For all others, pass the info from the EX/MEM read version register to the to 
	 * the right version of the MEM/WB.
	 */
	public void MEM_stage()
	{
		if ( ex_mem.getReadValue(0) == 1 )
		{
			mem_wb.setWriteValue(0, 1);
		} 
		else 
		{
			mem_wb.setWriteValue(0, 0);
		}

		mem_wb.setWriteValue(1, ex_mem.getReadValue(3)); //memToReg to the MEM/WB
		mem_wb.setWriteValue(2, ex_mem.getReadValue(4)); //regWrite to the MEM/WB
		mem_wb.setWriteValue(5, ex_mem.getReadValue(5)); //writeRegNum to the MEM/WB
		mem_wb.setWriteValue(4, ex_mem.getReadValue(6)); //aluResult to the MEM/WB

		if ( ex_mem.getReadValue( 1 ) == 1 )
		{
			short lwDataValue = main_mem.getMain_Mem(ex_mem.getReadValue(6));
			mem_wb.setWriteValue(3, lwDataValue);
		}
		if( ex_mem.getReadValue( 2 ) == 1 )
		{
			main_mem.setMain_Mem( ex_mem.getReadValue( 6 ), ( short )ex_mem.getReadValue( 7 ) );	
		}

	} //End Method: MEM_stage


	/**
	 * This Method writes information to the registers from the Read version MEM/WB
	 * (regWrite & memToReg)
	 */
	public void WB_stage()
	{

		if (mem_wb.getReadValue( 2 ) == 1 )
		{
			regs.setRegister( mem_wb.getReadValue( 5 ), mem_wb.getReadValue( 4 ) ); //aluRslt
		}

		if ( mem_wb.getReadValue( 1 ) == 1 )
		{	  
			regs.setRegister( mem_wb.getReadValue( 5 ), mem_wb.getReadValue( 3 ) );
		}

	} //End Method: WB_stage


	/**
	 * 
	 */
	public void Print_out_everything()
	{
		System.out.println( regs.RegisterToString() ); // Display Regs
		
		System.out.println( if_id.if_id_write() ); // IF/ID Write (written to by the IF stage)

		System.out.println( if_id.if_id_read() ); // IF/ID Read (read by the ID stage)

		if ( id_ex.getWriteValue( 0 ) == 0 ) // ID/EX Write (written to by the ID stage)
		{ 
			System.out.println( id_ex.id_ex_write_header() + " " + nOp() );
		} 
		else 
		{
			System.out.println( id_ex.id_ex_write_header() + " " + id_ex.control_body() );
			System.out.println( id_ex.id_ex_write() );
		}

		if ( id_ex.getReadValue( 0 ) == 0 ) // ID/EX Read (read by the EX stage)
		{
			System.out.println( id_ex.id_ex_read_header() + " " + nOp() );
		}
		else
		{
			System.out.println( id_ex.id_ex_read_header() + " " + id_ex.control_body() );
			System.out.println( id_ex.id_ex_read() );
		}

		if ( ex_mem.getWriteValue( 0 ) == 0 ) // EX/MEM Write (written to by the EX stage)
		{
			System.out.println( ex_mem.ex_mem_write_header() + " " + nOp() );
		} 
		else
		{
			System.out.println( ex_mem.ex_mem_write_header() + " " + ex_mem.control_body() );
			System.out.println( ex_mem.ex_mem_write() );
		}

		if ( ex_mem.getReadValue( 0 ) == 0 ) // EX/MEM Read (read by the MEM stage)
		{
			System.out.println( ex_mem.ex_mem_read_header() + " " + nOp() );
		} 
		else
		{
			System.out.println( ex_mem.ex_mem_read_header() + " " + ex_mem.control_body() );
			System.out.println( ex_mem.ex_mem_read() );
		}

		if ( mem_wb.getWriteValue( 0 ) == 0 ) // MEM/WR Write (written to by the MEM stage)
		{
			System.out.println( mem_wb.mem_wb_write_header() + " " + nOp() );
		} 
		else
		{
			System.out.println( mem_wb.mem_wb_write_header() + mem_wb.control_body() );
			System.out.println( mem_wb.mem_wb_write() );
		}

		if (mem_wb.getReadValue( 0 ) == 0 ) // MEM/WR Read (read by the WB stage)

		{
			System.out.println( mem_wb.mem_wb_read_header() + " " + nOp() );
		} 
		else
		{
			System.out.println( mem_wb.mem_wb_read_header() + mem_wb.control_body() );
			System.out.println( mem_wb.mem_wb_read() );
		}
		
	} //End Method: Print_out_everything

	
	/**
	 * Method  to auto fill No-Ops with zeros 
	 * @return nOp
	 */
	private String nOp() 
	{
		String nOp = "000000000\n";
		
		return nOp;
			
	} //End Method: nOp
	
	
	/**
	 * Method to copy right side of the pipeline registers to the read side
	 */
	public void Copy_write_to_read()
	{
		System.arraycopy(if_id.getWrite(), 0, if_id.getRead(), 0, if_id.getWrite().length);
		System.arraycopy(id_ex.getWrite(), 0, id_ex.getRead(), 0, id_ex.getWrite().length);
		System.arraycopy(ex_mem.getWrite(), 0, ex_mem.getRead(), 0, ex_mem.getWrite().length);
		System.arraycopy(mem_wb.getWrite(), 0, mem_wb.getRead(), 0, mem_wb.getWrite().length);
	}


} //End Class: Pipeline