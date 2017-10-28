package sudoku;

import java.util.*;

/**
 * This class provide the functionality to solve a sudoku grid.
 */
public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	/**
	 * A cetor that take the grid needed to be solved as an input.
	 */
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	/**
	 * This methods solves the grid and puts the solutions in the class array.
	 */
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	/**
	 * This method is used in the solve method to find the solution of
	 * the grid using backtracking.
	 */
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			// Abandon evaluation of this illegal board.
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Add it to solutions.
			this.solutions.add(grid);
			return;
		}
		else
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. Recursively 
			// call solveRecurse() on those grids.
			ArrayList<Grid> possibleSolutions = grid.next9Grids();
			for(Grid g: possibleSolutions)
			{
				this.solveRecurse(g);
			}
		}
	}
	
	/**
	 * This method find s the status of the sudoku grid.
	 * @return Evaluation enum type vriables.
	 */
	public Evaluation evaluate(Grid grid)
	{
		// check if its legal first.

		if( grid.isLegal() )
		{
			// check if its full.

			if( grid.isFull() )
			{
				return Evaluation.ACCEPT;
			}
			else
			{
				return Evaluation.CONTINUE;
			}
		}
		
		return Evaluation.ABANDON;
	}

	/**
	 * Returns the solutions arraylist.
	 */
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	public static void main(String[] args)
	{

		ArrayList<Grid> examples = new ArrayList<>();
		examples.add( TestGridSupplier.getPuzzle1() );
		examples.add( TestGridSupplier.getPuzzle2() );
		examples.add( TestGridSupplier.getPuzzle3() );
		examples.add( TestGridSupplier.getAccept() );
		examples.add( TestGridSupplier.getReject1() );
		examples.add( TestGridSupplier.getReject2() );
		examples.add( TestGridSupplier.getReject3() );
		examples.add( TestGridSupplier.getReject4() );

		ArrayList<Grid> givenSolutions = new ArrayList<>();
		givenSolutions.add( TestGridSupplier.getSolution1() );
		givenSolutions.add( TestGridSupplier.getSolution2() );
		givenSolutions.add( TestGridSupplier.getSolution3() );

		Solver solver;

		for( int i = 0 ; i < examples.size() ; i++ )
		{
			System.out.println( "We will solve:" + "\n" + examples.get( i ) );
			solver = new Solver( examples.get( i ) );
			solver.solve();

			if( !solver.getSolutions().isEmpty() )
			{
				System.out.println( "The solutions are: " + "\n" );

				// because only the first three example have given solutions.
				if( i < 3 )
				{
					for( Grid calculatedSolution : solver.getSolutions() )
					{
						System.out.println( calculatedSolution );
						if( calculatedSolution.equals( givenSolutions.get(i) ) )
						{
							System.out.println( "This solution matchs." + "\n" );
						}
					}
				}
			}
			else
			{
				System.out.println( "The soltuins list is empty." );
			}

			System.out.println( "=========================================" );

		}
	}
}
