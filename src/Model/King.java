package Model;

public class King extends Piece{

	public King(String color, char symbol) {
		super(color, symbol);
	}

	public void setPossibleMoves(){
		possibleMoves.add(currentArrayLocation+MOVE_ONE);
		possibleMoves.add(currentArrayLocation-MOVE_ONE);
		possibleMoves.add(currentArrayLocation+MOVE_EIGHT);
		possibleMoves.add(currentArrayLocation-MOVE_EIGHT);
		possibleMoves.add(currentArrayLocation+MOVE_NINE);
		possibleMoves.add(currentArrayLocation-MOVE_NINE);
		possibleMoves.add(currentArrayLocation+MOVE_SEVEN);
		possibleMoves.add(currentArrayLocation-MOVE_SEVEN);
	}

}
