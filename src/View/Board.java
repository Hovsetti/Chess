package View;

import java.io.File;
import java.io.InputStream;

public class Board {

	private InputStream inputStream;
	private File file;
	private final int BOARD_SIZE = 64;
	private final int NUMBER_OF_PIECES = 32;
	private Model.Square[] squares = new Model.Square[BOARD_SIZE];
	private Model.Piece[] pieces = new Model.Piece[NUMBER_OF_PIECES];
	
	public Board(){
		populateSquareArray();
		populatePieceArray();
	}
	
	private void populateSquareArray() {
		char file;
		int rank = 1;
		int arrayIndex = 0; 
		for(int j = 0; j < BOARD_SIZE/8; j++){
			file = 'a';
			for(int w = 0; w < BOARD_SIZE/8; w++){
				squares[arrayIndex] = new Model.Square(""+file+rank);
				arrayIndex++;
				file++;
			}
			rank++;
		}
	}
	
	private void populatePieceArray() {
		for(int j = 0; j < NUMBER_OF_PIECES; j++){
			pieces[j] = new Model.Piece("unocupied space", '-');
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
				Model.Piece piece = squares[arrayIndex].getPiece();
				if(piece == null){
					System.out.print(squares[arrayIndex].getValue() + "\t"); 
				}else{
					System.out.print(squares[arrayIndex].getPiece().getSymbol() + "\t");
				}
				arrayIndex++;
			}
			System.out.print("\n");
		}
	}
	
	public Model.Square[] getSquares(){
		return squares;
	}
	
	public Model.Piece[] getPieces(){
		return pieces;
	}
}
