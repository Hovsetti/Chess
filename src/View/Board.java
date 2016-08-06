package View;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class Board {

	private InputStream inputStream;
	private File file;
	private final int BOARD_SIZE = 64;
	private Model.Square[] squares = new Model.Square[BOARD_SIZE];
	private boolean potentialMove;
	private Controller.CheckMove checkMove = new Controller.CheckMove(squares);

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
			if(squares[j].getIsOccupied()){
				if(squares[j].getSpace().equals(startPosition)){
					if(checkMoves(squares[j].getPiece(), endPosition)){
						squares[j] = movePiece(endPosition, squares[j]);
						//TODO switch turn
						successfulMove = true;
					}
				}
			}
			squares[j].getPiece().clearPossibleMoves();
		}
		return successfulMove;
	}

	private Model.Square movePiece(String endPosition, Model.Square startingSquare){
		for(int j = 0; j<squares.length; j++){
			if(squares[j].getSpace().equals(endPosition)){
				potentialMove = squares[j].getIsOccupied();
				squares[j].setPiece(startingSquare.getPiece());
				squares[j].getPiece().clearPossibleMoves();
				squares[j].getPiece().setCurrentLocation(j);
				squares[j].setIsOccupied(true);
				squares[j].getPiece().setHasMoved(true);
				checkMove.clearPossibleMoves();
				startingSquare.setPiece(new Model.Piece("empty space", '-'));
				startingSquare.setIsOccupied(false);
			}
		}
		return startingSquare;
	}

	public boolean checkMoves(Model.Piece piece, String endPosition){
		boolean validMove = false;
		piece.setPossibleMoves();
		checkMove.checkPossibleMoves(piece);
		ArrayList<String> movesToCheck = checkMove.getPossibleMoves();
		for(int p = 0; p < movesToCheck.size() && !validMove; p++){
			if(movesToCheck.get(p).equals(endPosition)){
				validMove = true;
			}
		}
		return validMove;
	}

	public boolean getPotentialMove(){
		return potentialMove;
	}
}
