package Model;

import java.util.ArrayList;

public class Piece {
	protected String color;
	protected char symbol;
	protected boolean hasMoved = false;
	protected ArrayList<String> possibleMoves = new ArrayList<String>();
	protected boolean keepRunning = true;
	protected final int MOD_VALUE = 8;
	protected final int BOARD_MIN = -1;
	protected final int BOARD_MAX = 64;
	protected final int MOVE_SEVEN = 7;
	protected final int MOVE_NINE = 9;
	protected final int MOVE_EIGHT = 8;
	protected final int MOVE_ONE = 1;

	public Piece(String color, char symbol){
		this.color = color;
		this.symbol = symbol;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		ArrayList<String> possibleMoves = new ArrayList<String>();

		return possibleMoves;
	}

	protected void addPossibleMoves(int index, Square[] squares){
		if(!squares[index].getIsOccupied()){
			possibleMoves.add(squares[index].getSpace());
		}else{
			if(squares[index].getPiece().getColor().equals(this.color)){
				keepRunning = false;
			}else{
				possibleMoves.add(squares[index].getSpace());
				keepRunning = false;
			}
		}
	}

	public boolean checkMove(String startPosition, String endPosition, Square[] squares){
		boolean validMove = false;
		getPossibleMoves(startPosition, squares);
		for(int p = 0; p < possibleMoves.size() && !validMove; p++){
			if(possibleMoves.get(p).equals(endPosition)){
				validMove = true;
			}
		}
		return validMove;
	}
	
	public void clearPossibleMoves(){
		possibleMoves.clear();
	}
	
	public void setHasMoved(boolean moved){
		hasMoved = moved;
	}
}
