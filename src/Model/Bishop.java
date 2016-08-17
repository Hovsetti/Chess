package Model;

public class Bishop extends Piece{

	public Bishop(String color, char symbol) {
		super(color, symbol);
		canMoveDiagonalEveryTurn = true;
	}

	public void setPossibleMoves(){
		for(int j = 1; j<ROW_SIZE; j++){
			possibleMoves.add(currentArrayLocation+(MOVE_SEVEN*j));
			possibleMoves.add(currentArrayLocation-(MOVE_SEVEN*j));
			possibleMoves.add(currentArrayLocation+(MOVE_NINE*j));
			possibleMoves.add(currentArrayLocation-(MOVE_NINE*j));
		}
	}


}
