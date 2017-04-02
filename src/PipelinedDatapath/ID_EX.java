package PipelinedDatapath;

public class ID_EX
{

	private int write[];
	private int read[];

	public ID_EX() //Initialize and zero out the ID/EX array 
	{
		this.write = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		this.read = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	}


	/**
	 * Method to set a value into the Write array
	 */
	public void setWriteValue(int index, int value) 
	{
		write[index] = value;
		
	
	/**
	 * Method to get a value from the Write array
	 * @param index
	 */
	}
	public int getWriteValue(int index) 
	{
		return write[index];
	}


	/**
	 * Method to get values from the Write array
	 */
	public int[] getWrite() 
	{
		return write;
	}

	
	/**
	 * Method to get values from the Read array
	 */
	public int[] getRead() 
	{
		return read;
	}

	
	/**
	 * Method to get values into the Read array
	 * @param index
	 */
	public int getReadValue(int index) 
	{
		return read[index];
	}


	/**
	 * Method to format the ID/EX Write stage header
	 * @return 
	 * Method to format the ID/EX Write Header
	 */
	public String id_ex_write_header() 
	{
		String id_ex_write_header = "ID/EX Write (written to by the ID stage): \nControl: ";
		
		return id_ex_write_header;
			
	} //End Method: id_ex_write_header
	
	
	/**
	 * Method to format the ID/EXM Read stage header
	 * @return ID/EX read Header
	 */
	public String id_ex_read_header() 
	{
		String id_ex_read_header = "ID/EX Read (read by the EX stage): \nControl: ";
		
		return id_ex_read_header;
			
	} //End Method: id_ex_read_header
	
	
	/**
	 * Method to concatenate and format the ID/EX stage control results for the corresponding cycles
	 * @return formatted ID/EX control results
	 */
	public String control_body() 
	{
		String control_body = "RegDst = " + write[1] + ","
						+ " ALUSrc = " + write[3] + ","
						+ " ALUOp = " + Integer.toBinaryString(write[2]) + ","
						+ " MemRead = " + write[4] + ","
						+ " MemWrite = " + write[5] +"\n"
						+ "MemToreg = " + write[7] + ","
						+ " RegWrite = " + write[6] + ",";
		
		return control_body;
			
	} //End Method: control_body
	
	
	/**
	 * Method to concatenate and format the ID/EX Write results for the corresponding cycles
	 * @return formatted ID/EX Write results
	 */
	public String id_ex_write() 
	{
		String id_ex_write = "ReadReg1Value = " + Integer.toHexString(write[8]) + ","
							+ " ReadReg2Value = " + Integer.toHexString(write[9]) + ",\n"	
							+ "SEOffset = " + Integer.toHexString(write[10]) + ","
							+ " WriteReg_20_16 = " + write[11] + ","
							+ " WriteReg_15_11 = " + write[12] + ","
							+ " Function = " + Integer.toHexString(write[13]) + "\n";
				
		return id_ex_write;
		
	} //End Method: id_ex_write
		
	
	/**
	 * Method to concatenate and format the ID/EX Read results for the corresponding cycles
	 * @return formatted ID/EX Read results
	 */
	public String id_ex_read() 
	{
		String id_ex_read = "ReadReg1Value = " + Integer.toHexString(read[8]) + ","
							+ " ReadReg2Value = " + Integer.toHexString(read[9]) + ",\n"	
							+ "SEOffset = " + Integer.toHexString(read[10]) + ","
							+ " WriteReg_20_16 = " + read[11] + ","
							+ " WriteReg_15_11 = " + read[12] + ","
							+ " Function = " + Integer.toHexString(read[13]) + "\n";
				
		return id_ex_read;

	} //End Method: id_ex_read

	
}