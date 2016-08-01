package Model;

public class Piece {
	private String color;
	private char symbol;
	
	public Piece(String color, char symbol){
		this.color = color;
		this.symbol = symbol;
	}
	
	public String move(String startingPosition){
		String move = startingPosition;
		return move;
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

}
