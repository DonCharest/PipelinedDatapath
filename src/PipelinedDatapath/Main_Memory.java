package PipelinedDatapath;

public class Main_Memory
{
	
	private short Main_Mem[] = new short [1024];

	/**
	 * Method to Initialize the 1K Main Memory as follows:
	 * Main_Mem[0] = 0 ... Main_Mem[0xFF] = 0xFF. 
	 * 0xFF is the largest value that can be stored in a byte. 
	 * After reaching Max value "OxFF" restart at "0"  
	 * --> Main_Mem[0x100] = 0, and so on.
	 */
	public Main_Memory()
	{
		short Mem_Value = 0;
	
		for ( short i = 0; i < Main_Mem.length; i++ )
		{ 
			Main_Mem[i] = Mem_Value; 
			Mem_Value++;
	
			if ( Mem_Value > 0xFF )
			{ 
				Mem_Value = 0; 
			}
		}
		
	} //End Method: Main_Memory
	
	
	/**
	 * Method to set the data value in Main_Mem
	 * @param index
	 * @param data
	 */
	public void setMain_Mem(int index, short data)
	{
		Main_Mem[index] = data;
		
	} //End Method: setMain_Mem
	
	
	/**
	 * Method to get the data value stored in Main_Mem
	 * @param index
	 * @return Main_Mem stored value
	 */
	public short getMain_Mem( int index )
	{
		return Main_Mem[index];
		
	} //End Method: getMain_Mem
	
	
} //End Class: Main_Memory