package Model;

public class Queen extends Piece{

	public Queen(String color, char symbol) {
		super(color, symbol);
		canMoveDiagonalEveryTurn = true;
		canMoveStraightEveryTurn = true;
	}
	
	public void setPossibleMoves(){
		for(int j = 1; j<ROW_SIZE; j++){
			possibleMoves.add(currentArrayLocation+(MOVE_EIGHT*j));
			possibleMoves.add(currentArrayLocation-(MOVE_EIGHT*j));
			possibleMoves.add(currentArrayLocation+(MOVE_ONE*j));
			possibleMoves.add(currentArrayLocation-(MOVE_ONE*j));
			possibleMoves.add(currentArrayLocation+(MOVE_SEVEN*j));
			possibleMoves.add(currentArrayLocation-(MOVE_SEVEN*j));
			possibleMoves.add(currentArrayLocation+(MOVE_NINE*j));
			possibleMoves.add(currentArrayLocation-(MOVE_NINE*j));
		}
	}

}
