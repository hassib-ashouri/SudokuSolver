package sudoku;

import java.util.*;

/**
 * This class represents a 9X9 sudoku grid.
 */
public class Grid 
{
	private int[][]						values;



	/** 
	 * ctor to create a grid instance.
	 * @param rows : array that holds a the rows as strings with the char '.' repesenting an empty slot.
	 */
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
	
	
	
	/**
	 * ctor of an instance of Grid using another grid object.
	 * @param src : A Grid object.
	 */
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	


	/**
	 * Prints the grid as rows of strings with empty slots represented by '.'.
	 */
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


	
	/**
	 * This method optain the possible 9 suduku grids by filling the first
	 * empty slot with numbers 1-9.
	 */
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;
		int emptySlot = 0 ;

		if( this.isFull() == true )
		{
			return null;
		}

		// Find x,y of an empty cell.

		for( int row = 0; row < this.values.length ; row++ )
		{
			for( int col = 0; col < this.values[0].length ; col++ )
			{
				if( this.values[row][col] == emptySlot )
				{
					// identify the empty slot.
					xOfNextEmptyCell = col;
					yOfNextEmptyCell = row;
					// break from the nested loop
					row = this.values.length;
					break;
				}
			}
		}

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		Grid gridInstance;

		for( int num = 1 ; num <= 9 ; num++ )
		{
			gridInstance = new Grid(this);
			gridInstance.values[yOfNextEmptyCell][xOfNextEmptyCell] = num;
			grids.add(gridInstance);
			
		}

		return grids;
	}
	
	
	/**
	 * Checks every row, column, and blok of 3X3 for reapeated number.
	 * @return true : if the grid is legal.
	 */
	public boolean isLegal()
	{
		int[] checkForRepeatsArray ; // holds numbers to be checked for repeats later on.


		// Goes through every line and checks for repeats.

		for( int row = 0 ;	row < this.values.length	; row++ )
		{
			checkForRepeatsArray = this.values[row];
			// check for repeats
			if(		this.isThereRepeats(checkForRepeatsArray)		)
			{
				assert	!this.isThereRepeats(checkForRepeatsArray)	: "Error: one of the rows has a repeated number." ;				
				
				return false;
			}
		}

		// Goes through every column

		checkForRepeatsArray = new int[this.values.length]; // initialize the array

		for( int col = 0 ;	col < this.values[ 0 ].length		; col++ )
		{
			//add the numbers in one column to one array.

			for( int row = 0 ;	row < this.values.length	; row++ )
			{
				checkForRepeatsArray[ row ] = this.values[ row ][ col ];
			}
			
			//check if there is repeats

			if(		this.isThereRepeats(checkForRepeatsArray)	)
			{
				assert	!this.isThereRepeats(checkForRepeatsArray)	: "Error: one of the columns has a repeated number." ;				
				
				return false;
			}
			
		}

		// treat the sudoku as a grid of blocks in handelling this part.
		
		int xOfBlock; //from 0 to 2
		int yOfBlock; //from 0 to 2
		int temp = 0; // used as an index when addin the numbers

		for( xOfBlock = 0 ;		xOfBlock < 3	; xOfBlock++ )
		{
			for( yOfBlock = 0 ;		yOfBlock < 3	; yOfBlock++ )
			{
				// traverse through the values of each block

				for( int row = 0 + (3 * yOfBlock) ;		row < 3 * (yOfBlock + 1)	; row ++ )
				{
					for( int col = 0 + (3 * xOfBlock) ;		col < 3 * (xOfBlock + 1)	; col++ )
					{
						checkForRepeatsArray[ temp++ ] = this.values[ row ][ col ];
					}	
				}

				// check for repeats in the values of a block

				if(		this.isThereRepeats(checkForRepeatsArray)	)
				{
					assert	!this.isThereRepeats(checkForRepeatsArray)	: "Error: one of the blocks has a repeated number.";
					
					return false;
				}

				temp = 0; // set the zero to assure proper index the next iteration.
			}
		}


		// All rows/cols/blocks are legal.
		return true;
	}

	/**
	 * checks if the grid is full.
	 * @return true : if the grid is full.
	 */
	public boolean isFull()
	{
		for( int row = 0 ;	row < this.values.length	; row++ )
		{
			for( int col = 0 ;	col < this.values[ row ].length		; col++ )
			{
				if(		this.values[ row ][ col ] == 0	)
				{
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Checks if two grid instances have the same numbers.
	 * @return true : if instances have the sam numbers.
	 */
	@Override
	public boolean equals(Object x)
	{
		Grid that = (Grid) x;

		for( int row = 0 ;	row < this.values.length	; row++ )
		{
			for( int col = 0 ;	col < this.values[ row ].length		; col++ )
			{
				if(		this.values[ row ][ col ] != that.values[ row ][ col ]	)
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Checks if an array has a repeted numbers
	 * @return true : if the array has repeated numbers.
	 */
	private boolean isThereRepeats( int[] arr )
	{
		int temp;

		for(int i = 0 ; i < arr.length ; i++ )
		{
			temp = arr[ i ];
			if(		temp != 0	)
			{
				for(int j = i + 1 ;		j < arr.length		; j ++)
				{
					if(		temp == arr[ j ]	)
					{	
						return true;
					}
				}
			}
		
		}

		return false;
	}
}
