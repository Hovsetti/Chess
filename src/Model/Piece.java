package Model;

import java.util.ArrayList;

public class Piece {
	protected String color;
	protected char symbol;
	protected boolean hasMoved;
	
	public Piece(String color, char symbol){
		this.color = color;
		this.symbol = symbol;
		hasMoved = false;
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
}
