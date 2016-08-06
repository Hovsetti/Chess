package Model;

public class Rook extends Piece{

	public Rook(String color, char symbol) {
		super(color, symbol);
	}

	public void setPossibleMoves(){
		for(int j = 1; j<ROW_SIZE; j++){
			possibleMoves.add(currentArrayLocation+(MOVE_EIGHT*j));
			possibleMoves.add(currentArrayLocation-(MOVE_EIGHT*j));
			possibleMoves.add(currentArrayLocation+(MOVE_ONE*j));
			possibleMoves.add(currentArrayLocation-(MOVE_ONE*j));
		}
	}

}
