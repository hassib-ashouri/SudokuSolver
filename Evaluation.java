package sudoku;
/**
 * This enum provides ways to describe the legitemecy and status of the grid.
 */
enum Evaluation 
{
	/**
	 * The grid is legal, full. It could be a solution.
	 */
	ACCEPT,
	/**
	 * The grid is not legal.
	 */
	ABANDON, 
	/**
	 * The drid is legal with empty slots.
	 */
	CONTINUE;
}
