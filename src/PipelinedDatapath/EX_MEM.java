package PipelinedDatapath;

public class EX_MEM
{
	
	private int write[];
	private int read[];
	
	public EX_MEM() //Initialize and zero out the EX/MEM array 
	{
		this.write = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		this.read = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
	}
	

	/**
	 * Method to set a value into the Write array
	 */
	public void setWriteValue(int index, int value) 
	{
		write[index] = value;
	}
	
	
	/**
	 * Method to get a value from the Write array
	 * @param index
	 */
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
	 * Method to format the EX/MEM Write stage header
	 * @return EX/MEM Write Header
	 */
	public String ex_mem_write_header() 
	{
		String ex_mem_write_header = "EX/MEM Write (written to by the EX stage): \nControl: ";
		
		return ex_mem_write_header;
			
	} //End Method: ex_mem_write_header
	
	
	/**
	 * Method to format the EX/MEM Read stage header
	 * @return EX/MEM read Header
	 */
	public String ex_mem_read_header() 
	{
		String ex_mem_read_header = "EX/MEM Read (read by the MEM stage): \nControl: ";
		
		return ex_mem_read_header;
			
	} //End Method: ex_mem_read_header
	
	
	/**
	 * Method to concatenate and format the EX/MEM stage control results for the corresponding cycles
	 * @return formatted EX/MEM control results
	 */
	public String control_body() 
	{
		String control_body = "MemRead = " + getWriteValue(1) 
				+ ", MemWrite = " + getWriteValue(2) 
				+ ", MemToReg = " + getWriteValue(3) 
				+ ", RegWrite = " + getWriteValue(4);
		
		return control_body;
		
	} //End Method: control_body

	
	/**
	 * Method to concatenate and format the EX/MEM Write results for the corresponding cycles
	 * @return formatted EX/MEM Write results
	 */
	public String ex_mem_write() 
	{
		String ex_mem_write = "ALUResult = " + Integer.toHexString(write[6]) + ","
							+ " SWValue = " + Integer.toHexString(write[7]) + ","
							+ " WriteRegNum = " + write[5] + "\n"; 
				
		return ex_mem_write;
		
	} //End Method: ex_mem_write
		
	
	/**
	 * Method to concatenate and format the EX/MEM Read results for the corresponding cycles
	 * @return formatted EX/MEM Read results
	 */
	public String ex_mem_read() 
	{
		String ex_mem_read = "ALUResult = " + Integer.toHexString(read[6]) + ","
							+ " SWValue = " + Integer.toHexString(read[7]) + ","
							+ " WriteRegNum = " + read[5] + "\n";
				
		return ex_mem_read;

	} //End Method: ex_mem_read
	

} //End Class: EX_MEM