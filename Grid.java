package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	

	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. Youâ€™ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Donâ€™t change
	// â€œthisâ€� grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;
		char emptySlot = '.';
		boolean stop = false;

		if(this.isFull() == true)
			return null;


		// Find x,y of an empty cell.

		for(int row = 0; row < this.values.length && !stop; row++)
		{
			for(int col = 0; col < this.values[0].length && !stop; col++)
			{
				if(this.values[row][col] == emptySlot)
				{
					xOfNextEmptyCell = col;
					yOfNextEmptyCell = row;
					stop = true;
				}
			}
		}

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		Grid gridInstance;

		for(int num = 1; num <= 9; num++ )
		{
			gridInstance = new Grid(this);
			gridInstance.values[yOfNextEmptyCell][xOfNextEmptyCell] = num;
			grids.add(gridInstance);
			
		}

		return grids;
	}
	
	
	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		int[] checkForRepeatsArray ;
		// Check every row. If you find an illegal row, return false.
		for(int row = 0 ; row < this.values.length ; row++ )
		{
			checkForRepeatsArray = this.values[row];
			if(this.isThereRepeats(checkForRepeatsArray))
			{
				return false;
			}
		}

		// Check every column. If you find an illegal column, return false.
		checkForRepeatsArray = new int[this.values.length];
		for(int col = 0 ; col < this.values[0].length ; col++ )
		{
			//add the numbers in one column to one array.
			for( int row = 0 ; row < this.values.length ; row++ )
			{
				checkForRepeatsArray[row] = this.values[row][col];
			}
			//check if there is repeats
			if(this.isThereRepeats(checkForRepeatsArray))
			{
				return false;
			}
			
		}

		// Check every block. If you find an illegal block, return false.
		//we treat the sudoku as a grid of blocks in handelling this part.
		int xOfBlock; //from 0 to 2
		int yOfBlock; //from 0 to 2
		int temp = 0;

		for(xOfBlock = 0 ; xOfBlock < 3 ; xOfBlock++ )
		{
			for( yOfBlock = 0 ; yOfBlock < 3 ; yOfBlock++ )
			{
				
				for(int row = 0 + (3 * yOfBlock) ; row < 3 * (yOfBlock + 1) ; row ++)
				{
					for( int col = 0 + (3 * xOfBlock) ; col < 3 * (xOfBlock + 1) ; col++ )
					{
						checkForRepeatsArray[temp++] = this.values[row][col];
					}	
				}


				if(this.isThereRepeats(checkForRepeatsArray))
				{
					return false;
				}

				temp = 0;
			}
		}


		// All rows/cols/blocks are legal.
		return true;
	}

	
	//
	// COMPLETE THIS
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for(int row = 0 ; row < this.values.length ; row++ )
		{
			for( int col = 0 ; col < this.values[0].length ; col++ )
			{
				if(this.values[row][col] == 0)
				{
					return false;
				}
			}
		}

		return true;
	}
	

	//
	// COMPLETE THIS
	//
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid that = (Grid) x;
		for( int row = 0 ; row < this.values.length ; row++ )
		{
			for( int col = 0 ; col < this.values[row].length ; col++ )
			{
				if(this.values[row][col] != that.values[row][col])
				{
					return false;
				}
			}
		}

		return true;

	}

	private boolean isThereRepeats(int[] arr)
	{
		int temp;
		for(int i = 0 ; i < arr.length ; i++ )
		{
			temp = arr[i];
			if(temp != 0)
			{
				for(int j = i + 1 ; j < arr.length ; j ++)
				{
					if( temp == arr[j] )
						return true;
				}
			}
		
		}

		return false;
	}
}
