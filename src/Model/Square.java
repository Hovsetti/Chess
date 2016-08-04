package Model;

public class Square {
	
	private String space;
	private Piece piece;
	private boolean isOccupied = false;
	
	public Square(String space){
		this.space = space;
		piece = new Piece("unoccupied space", '-');
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
}
