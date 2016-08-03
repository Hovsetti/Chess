package Model;

public class Square {
	
	private String space;
	private Piece piece;
	private boolean isOccupied;
	private char value = '-';
	
	public Square(String space){
		this.space = space;
		piece = null;
		isOccupied = false;
	}
	
	public String getSpace() {
		return space;
	}
	
	public void setSpace(String space){
		this.space = space;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public char getValue(){
		return value;
	}
}
