package Model;

public class Pawn extends Piece{

	public Pawn(String color, char symbol) {
		super(color, symbol);
		hasSpecialAttackMove = true;
		canMoveDiagonalEveryTurn = true;
		canMoveStraightEveryTurn = true;
	}
	
	public void setPossibleMoves(){
		if(this.color.equals("white")){
			if(!hasMoved){
				possibleMoves.add(currentArrayLocation-16);
			}
			possibleMoves.add(currentArrayLocation-8);
			possibleMoves.add(currentArrayLocation-7);
			possibleMoves.add(currentArrayLocation-9);
		}else{
			if(!hasMoved){
				possibleMoves.add(currentArrayLocation+16);
			}
			possibleMoves.add(currentArrayLocation+8);
			possibleMoves.add(currentArrayLocation+7);
			possibleMoves.add(currentArrayLocation+9);
		}
	}

}
