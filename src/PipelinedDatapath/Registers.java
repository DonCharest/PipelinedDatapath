package PipelinedDatapath;

public class Registers 
{
	
	private static int[] Regs = new int[32];
	
	/**
	 * Method to Initialize the Simulated Registers
	 * to an initial value of "0x100" + the Register number, 
	 * excluding register 0, which always has the value "0".
	 */
	public Registers()
	{
		Regs[0] = 0;

		for ( int i = 1; i < ( Regs.length ); i++ )
		{     
			Regs[i] = 0x100 + i;
		}
		
	} //End Method: Registers
	
	
	/**
	 * Method to set the value in Regs
	 * @param index
	 */
	public void setRegister(int index, int data)
	{
		Regs[index] = data;
		
	} //End Method: setRegister
	
	
	/**
	 * Method to get the data value stored in Regs
	 * @param index
	 * @return Regs stored value
	 */
	public int getRegister(int index)
	{	
		return Regs[index];
		
	} //End Method: getRegister
	
	
	/**
	 * Method to format and print (decimal) register values
	 * @return Values for regs: 0 - 31
	 */
	public String RegisterToString()
	{
		String registerString = "Registers: \n";
		
		for ( int i = 0; i < Regs.length; i++ )
		{
			registerString += i + ")  " + Integer.toHexString(Regs[i]) + " \t ";
			
			if( i % 5 == 0 )
			{
				registerString += "\n";
			}
		}
		
		return registerString;
	
	} //End Method: RegisterToString
	

} //End Class: Registers