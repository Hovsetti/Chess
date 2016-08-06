package Model;

import java.util.ArrayList;

public class Piece {
	protected final int ROW_SIZE = 8;
	protected String color;
	protected char symbol;
	protected boolean canJump = false;
	protected boolean hasSpecialAttackMove = false;
	protected boolean hasMoved = false;
	protected ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
	protected int currentArrayLocation;
	protected boolean keepRunning = true;
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

	public ArrayList<Integer> getPossibleMoves(){
		return possibleMoves;
	}
	
	public void clearPossibleMoves(){
		possibleMoves.clear();
	}
	
	public void setHasMoved(boolean moved){
		hasMoved = moved;
	}
	
	public int getCurrentLocation() {
		return currentArrayLocation;
	}

	public void setCurrentLocation(int currentLocation) {
		this.currentArrayLocation = currentLocation;
	}
	
	public void setPossibleMoves(){
	}
	
	public boolean getCanJump(){
		return canJump;
	}
	
	public boolean getHasSpecialAttackMove(){
		return hasSpecialAttackMove;
	}
}
