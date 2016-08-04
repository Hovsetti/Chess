package Model;

import java.util.ArrayList;

public class Pawn extends Piece{

	public Pawn(String color, char symbol) {
		super(color, symbol);
	}
	
	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		
		int boardLocation = findPieceLocation(startingLocation, squares);
		
		int modBoardLocation = boardLocation%MOD_VALUE;
		
		int modifiedIndex;
		
		if(this.color.equals("white")){
			modifiedIndex = boardLocation - MOVE_EIGHT;
			if(modifiedIndex > BOARD_MIN){
				if(!squares[modifiedIndex].getIsOccupied()){
					possibleMoves.add(squares[modifiedIndex].getSpace());
					if(!this.hasMoved){
						modifiedIndex = boardLocation - (MOVE_EIGHT*2);
						if(!squares[modifiedIndex].getIsOccupied()){
							possibleMoves.add(squares[modifiedIndex].getSpace());
						}
					}
				}
				
				modifiedIndex = boardLocation - MOVE_SEVEN;
				if(squares[modifiedIndex].getIsOccupied() && squares[modifiedIndex].getPiece().getColor().equals("black") && modifiedIndex%MOD_VALUE > modBoardLocation){
					possibleMoves.add(squares[modifiedIndex].getSpace());
				}
				
				modifiedIndex = boardLocation - MOVE_NINE;
				if(squares[modifiedIndex].getIsOccupied() && squares[modifiedIndex].getPiece().getColor().equals("black") && modifiedIndex%MOD_VALUE < modBoardLocation){
					possibleMoves.add(squares[modifiedIndex].getSpace());
				}
			}
		}else{
			modifiedIndex = boardLocation + MOVE_EIGHT;
			if(modifiedIndex < BOARD_MAX){
				if(!squares[modifiedIndex].getIsOccupied()){
					possibleMoves.add(squares[modifiedIndex].getSpace());
					if(!this.hasMoved){
						modifiedIndex = boardLocation + (MOVE_EIGHT*2);
						if(!squares[modifiedIndex].getIsOccupied()){
							possibleMoves.add(squares[modifiedIndex].getSpace());
						}
					}
				}
				
				modifiedIndex = boardLocation + MOVE_SEVEN;
				if(squares[modifiedIndex].getIsOccupied() && squares[modifiedIndex].getPiece().getColor().equals("white") && modifiedIndex%MOD_VALUE < modBoardLocation){
					possibleMoves.add(squares[modifiedIndex].getSpace());
				}
				
				modifiedIndex = boardLocation + MOVE_NINE;
				if(squares[modifiedIndex].getIsOccupied() && squares[modifiedIndex].getPiece().getColor().equals("white") && modifiedIndex%MOD_VALUE > modBoardLocation){
					possibleMoves.add(squares[modifiedIndex].getSpace());
				}
			}
		}
		return possibleMoves;
	}

}
