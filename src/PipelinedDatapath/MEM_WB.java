package PipelinedDatapath;

public class MEM_WB
{
	
	private int write[];
	private int read[];
	
	public MEM_WB() //Initialize and zero out the ID/EX array 
	{
		this.write = new int[] {0, 0, 0, 0, 0, 0};
		this.read = new int[] {0, 0, 0, 0, 0, 0};
	}

	
	/**
	 * Method to set a value into the Write array
	 * @param index
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
	 * Method to get a value from the Read array
	 * @param index
	 */
	public int getReadValue(int index) 
	{
		return read[index];
	}


	/**
	 * Method to format the MEM/WB Write stage header
	 * @return 
	 * MEM/WB Write Header
	 */
	public String mem_wb_write_header() 
	{
		String ex_mem_write_header = "MEM/WR Write (written to by the MEM stage): \nControl: ";
		
		return ex_mem_write_header;
			
	} //End Method: ex_mem_write_header
	
	
	/**
	 * Method to format the MEM/WB Read stage header
	 * @return 
	 * MEM/WB Read Header
	 */
	public String mem_wb_read_header() 
	{
		String ex_mem_read_header = "MEM/WR Read (read by the WB stage): \nControl: ";
		
		return ex_mem_read_header;
			
	} //End Method: ex_mem_read_header
	

	/**
	 * Method to concatenate and format the MEM/WB stage control results for the corresponding cycles
	 * @return formatted MEM/WB control results
	 */
	public String control_body() 
	{
		String control_body = "MemToReg = " + write[1] + ","
							+ "RegWrite = " +  write[2];
		
		return control_body;
		
	} //End Method: control_body

	
	/**
	 * Method to concatenate and format the MEM/WB Write results for the corresponding cycles
	 * @return formatted MEM/WB Write results
	 */
	public String mem_wb_write() 
	{
		String mem_wb_write = "LWDataValue = " + Integer.toHexString(write[3]) + ","
							+ " ALUResult = " + Integer.toHexString(write[4]) + ","
							+ " WriteRegNum = " + write[5] + "\n";
		
		return mem_wb_write;
		
	} //End Method: mem_wb_write
		
	
	/**
	 * Method to concatenate and format the MEM/WB Read results for the corresponding cycles
	 * @return formatted MEM/WB Read results
	 */
	public String mem_wb_read() 
	{
		String mem_wb_read = "LWDataValue = " + Integer.toHexString(read[3]) + ","
							+ " ALUResult = " + Integer.toHexString(read[4]) + ","
							+ " WriteRegNum = " + read[5] + "\n";
		
		return mem_wb_read;

	} //End Method: mem_wb_read
	
	

}