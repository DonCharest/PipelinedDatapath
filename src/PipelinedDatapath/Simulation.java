package PipelinedDatapath;

/**
 * CS472 Project 3: Pipeline datapath
 * 
 * @author Don Charest
 */

public class Simulation
{

	private static int InstructionCache[] = { 0xa1020000, 0x810AFFFC, 0x00831820, 0x01263820, 0x01224820, 0x81180000,
			0x81510010, 0x00624022, 0x00000000, 0x00000000, 0x00000000, 0x00000000 };

	/**
	 * Method to get index test instructions from array: InstructionCache[]
	 * 
	 * @param InstructionCache
	 * @return - Program Test Instructions from: InstructionCache[]
	 */
	public static int getInstruction(int index)
	{
		return InstructionCache[index];

	} // End Method: getInstruction

	/**
	 * Main Method
	 * 
	 * @param IF_stage
	 * @param ID_stage
	 * @param EX_stage
	 * @param MEM_stage
	 * @param WB_stage
	 * @param Print_out_everything
	 * @param Copy_Write_to_read
	 */
	public static void main(String[] args)
	{
		Pipeline pipline = new Pipeline();

		for (int i = 0; i < InstructionCache.length; i++)

		{
			System.out.println("Clock Cycle " + (i + 1) + "\n");
			pipline.IF_stage(i);
			pipline.ID_stage();
			pipline.EX_stage();
			pipline.MEM_stage();
			pipline.WB_stage();
			pipline.Print_out_everything();
			pipline.Copy_write_to_read();
		}

	} // End Method: Main

} // End Class: Simulation