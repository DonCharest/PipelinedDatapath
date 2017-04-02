package PipelinedDatapath;


public class IF_ID
{
	 int write[];
	 int read[];

	public IF_ID()  //Initialize and zero out the ID/EX array 
	{
		this.write = new int[] {0};
		this.read = new int[] {0};
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
	 * @param index
	 */
	public int getReadValue(int index)
	{
		return read[index];
	}	
	

	/**
	 * Method to get values into the Read array
	 */
	public int[] getRead() 
	{
		return read;
	}

	
	/**
	 * Method to set values into the Read array
	 */
	public void setRead(int[] read) 
	{
		this.read = read;
	}
	
	
	/**
	 * Method to format the IF/ID Write stage header and return the current instruction
	 * @return IF/ID Write Header
	 */
	public String if_id_write() 
	{
		String if_id_write = " \nIF/ID Write (written to by the IF stage): \ninst = " + Integer.toHexString(write[0]);
				
		return if_id_write;
			
	} //End Method: if_id_write
	
	
	/**
	 * Method to format the IF/ID Read stage header and return the current instruction
	 * @return IF/ID Read Header
	 */
	public String if_id_read() 
	{
		String if_id_read = " \nIF/ID Read (read by the ID stage): \ninst = " + Integer.toHexString(read[0]) + "\n";
		
		return if_id_read;
			
	} //End Method: if_id_read

}