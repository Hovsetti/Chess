package View;

import java.io.File;
import java.io.InputStream;

public class Board {

	private InputStream inputStream;
	private File file;
	private final int BOARD_SIZE = 64;
	private Model.Square[] squares = new Model.Square[BOARD_SIZE];
	private boolean potentialMove;
	
	public Board(){
		populateSquareArray();
	}
	
	private void populateSquareArray() {
		char file;
		int rank = 8;
		int arrayIndex = 0; 
		for(int j = 0; j < BOARD_SIZE/8; j++){
			file = 'a';
			for(int w = 0; w < BOARD_SIZE/8; w++){
				squares[arrayIndex] = new Model.Square(""+file+rank);
				arrayIndex++;
				file++;
			}
			rank--;
		}
	}

	public File getFile(){
		return file;
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}
	
	public void drawBoard(){
		int arrayIndex = 0;
		for(int j = 0; j < BOARD_SIZE/8; j++){
			for(int w = 0; w < BOARD_SIZE/8; w++){
				System.out.print(squares[arrayIndex].getPiece().getSymbol() + "\t");
				arrayIndex++;
			}
			System.out.print("\n");
		}
	}
	
	public Model.Square[] getSquares(){
		return squares;
	}
	
	public boolean attemptMove(String startPosition, String endPosition){
		boolean successfulMove = false;
		for(int j = 0; j < squares.length && !successfulMove; j++){
			if(squares[j].getSpace().equals(startPosition)){
				if(squares[j].getPiece().checkMove(startPosition, endPosition, squares)){
					squares[j] = movePiece(endPosition, squares[j]);
					//TODO switch turn
					successfulMove = true;
				}
			}
		}
		return successfulMove;
	}
	
	private Model.Square movePiece(String endPosition, Model.Square startingSquare){
		for(int j = 0; j<squares.length; j++){
			if(squares[j].getSpace().equals(endPosition)){
				potentialMove = squares[j].getIsOccupied();
				squares[j].setPiece(startingSquare.getPiece());
				squares[j].getPiece().clearPossibleMoves();
				squares[j].setIsOccupied(true);
				squares[j].getPiece().setHasMoved(true);
				startingSquare.setPiece(new Model.Piece("empty space", '-'));
				startingSquare.setIsOccupied(false);
			}
		}
		return startingSquare;
	}
	
	public boolean getPotentialMove(){
		return potentialMove;
	}
}
