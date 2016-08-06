package Model;

public class Knight extends Piece{
	
	private final int MOVE_SIX = 6;
	private final int MOVE_TEN = 10;
	private final int MOVE_FIFTEEN = 15;
	private final int MOVE_SEVENTEEN = 17;

	public Knight(String color, char symbol) {
		super(color, symbol);
		canJump = true;
	}
	
	public void setPossibleMoves(){
		possibleMoves.add(currentArrayLocation+MOVE_SIX);
		possibleMoves.add(currentArrayLocation-MOVE_SIX);
		possibleMoves.add(currentArrayLocation+MOVE_TEN);
		possibleMoves.add(currentArrayLocation-MOVE_TEN);
		possibleMoves.add(currentArrayLocation+MOVE_FIFTEEN);
		possibleMoves.add(currentArrayLocation-MOVE_FIFTEEN);
		possibleMoves.add(currentArrayLocation+MOVE_SEVENTEEN);
		possibleMoves.add(currentArrayLocation-MOVE_SEVENTEEN);
	}
}
