package nQueens;

/**
 * <p>
 * Title: GameBoard Class - A component of the NQueens application
 * </p>
 *
 * <p>
 * Description: An entity object class that represents the game board and 
 * performs all of the critical problem solving actions on that board
 * 
 * 	This class has been simplified by the removal of the more advanced methods so the interns can
 *  practice writing those methods without needing to write everything
 * 
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2015-2018
 * </p>
 *
 * @author Lynn Robert Carter based on a version written by Hatem Alismail
 * @version 2.00	Update to JavaFX
 * @version 2.01	Refactor to make the user interface easier to understand 
 */
public class GameBoard {
	// This enumeration is used to define the game board three different states
	protected enum BoardState {
		SAFE, 		// With no queens on the board, all positions are safe
		NOT_SAFE, 	// Any cell a queen can attack is not safe
		QUEEN		// A cell containing a queen
	};

	// These attributes capture the essence of the game board
	protected BoardState[][] theBoard;	// This is the board of states 
	private int boardSize;				// The number of rows and columns 4 - 12

	/**
	 * Constructs an empty board size X size
	 * 
	 * Complains if you attempt to construct a board that is too small or large.
	 * The GUI should never allow this to happen.
	 */
	public GameBoard(int size) {
		
		// Do not support boards smaller than 4x4 or larger than 12x12
		if (size < 4 || size > 12) {
			System.out.println("*** ERROR *** The board size must be in the range 4 - 12.");
			return;
		}

		// Set up the key attributes
		boardSize = size;
		theBoard = new BoardState[boardSize][boardSize];
		
		// Initialize the board to completely empty (e.g. safe)
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				theBoard[i][j] = BoardState.SAFE;
	}

	/**********
	 * This is a copy constructor that makes a deep copy of the game board that 
	 * is required for recursion.
	 * 
	 * @param b - the board object that should be copied.
	 */
	public GameBoard(GameBoard b) {
		// Create a new board
		theBoard = new BoardState[b.boardSize][b.boardSize];

		// Copy the board by making an actual copy of the contents
		for (int i = 0; i < b.boardSize; i++)
			for (int j = 0; j < b.boardSize; j++)
				theBoard[i][j]=b.theBoard[i][j];
		boardSize = b.boardSize;
	}

	/**********
	 * Determines if a position can be reached in one or zero moves by any of
	 * the queens currently on the board
	 * 
	 * @param row - the row for the position
	 * @param col - the column for the position
	 *
	 * @return true if the specified square is not safe (it is being not safe 
	 * by at least one queen)
	 */
	public boolean isNotSafe(int row, int col) {
		return theBoard[row][col] != BoardState.SAFE;
	}

	/**********
	 * Places a queen on the board at a specified row and column if that is an 
	 * SAFE place.  If it is not a safe place, the method returns false. If it 
	 * is a safe place, it places a queen there and indicates all of the squares
	 * that are now not safe because of this placement.
	 * 
	 * @param row - the row for the position
	 * @param col - the column for the position
	 * 
	 * @return true if placement is successful
	 */
	public boolean place(int row, int col) {
	
		if(theBoard[row][col] != BoardState.SAFE) {
			return false;
		}
		else 
		{
			
		
		for(int r=row+1,c=col;r<boardSize;r++) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		for(int r=row-1,c=col;r>=0;r--) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		for(int r=row,c=col+1;c<boardSize;c++) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		for(int r=row,c=col-1;c>=0;c--) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		
		for(int r=row+1, c=col+1; r<boardSize && c<boardSize; r++,c++ ) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		
		for(int r=row-1, c=col-1; r>=0 && c>=0; r--,c-- ) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		
		for(int r=row+1, c=col-1; r<boardSize && c>=0; r++,c-- ) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		
		for(int r=row-1, c=col+1; r>=0 && c<boardSize; r--,c++ ) {
			theBoard[r][c] = BoardState.NOT_SAFE;
		}
		
		theBoard[row][col] = BoardState.QUEEN;
		
		return false;		
		}
	}

	/**********
	 * Getter for the size of the board
	 */
	public int getSize() {
		return boardSize;
	}
	
	/**********
	 * This method finds one solution of the current board using for loops and leaves this solution
	 * in the double dimensioned array this.theBoard
	 * @return 
	 */
	
	
	/* Iterative Solution
	public void findOneSolution(){

		for (int r = 0; r < boardSize; r++)
		    for (int c = 0; c < boardSize; c++)
		        if(place(r, c)) {
		        	break;
		        }
		
	}
	*/
	
	public GameBoard findOneSolution(GameBoard b, int row){
	    if (row >= b.getSize()) {
	    	return b;       
	    }
	    for (int r = 0; r < boardSize; r++)
		    for (int c = 0; c < boardSize; c++)
		        if(place(r, c)) {
		        	break;
		        }
	    return findOneSolutionInThisRow(b, row, 0); 
	}
	
	public GameBoard findOneSolutionInThisRow(GameBoard b, int row , int col){
	    if (col >= b.getSize()) {
	    	return null;        
	    }
	    
	    GameBoard tempBoard = new GameBoard(b);     
	    if (tempBoard.place(row, col)) {
	        tempBoard = findOneSolution(tempBoard, row+1);
	        if (tempBoard != null) return tempBoard;
	    }
	    return findOneSolutionInThisRow(b, row, col+1); 

	}




	/**********
	 * This toString returns a string representation of the board for debugging purposes
	 */
	public String toString() {
		String toReturn = "";	
		
		// Work from the top row to the bottom
		for (int r = 0; r < boardSize; r++) {	
			toReturn += "\n";
			// Within a row, work from the left to the right
			for (int c = 0; c < boardSize; c++)		

				// Process each of the the three possible state for each square
				switch (theBoard[r][c]) {
				// Safe (not not safe and empty)
				case SAFE: toReturn += " S "; break;

				// Not safe due to a Queen in a position to take it
				case NOT_SAFE: toReturn += " X "; break;

				// Contains a queen
				case QUEEN: toReturn += " Q "; break;
				
				// The code should never get here
				default:					
					toReturn += "\n*** Error ***\n";
				}
			// Upon completing a row, output a new line before starting to work on the next one
			toReturn += "\n\n"; 
		}
		return toReturn;
	}
}
