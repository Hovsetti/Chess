package Model;

import java.util.ArrayList;

public class King extends Piece{

	public King(String color, char symbol) {
		super(color, symbol);
	}

	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		
		int boardLocation = -1;
		
		for(int j = 0; j < squares.length; j++){
			if(squares[j].getSpace().equals(startingLocation)){
				boardLocation = j;
			}
		}

		if(boardLocation == -1){
			System.out.println("bad boardLocation in getPossibleMoves for the piece in " + startingLocation);
			System.exit(1);
		}

		int modBoardLocation = boardLocation%MOD_VALUE;
		
		int modifiedIndex = boardLocation-MOVE_ONE;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_ONE;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_EIGHT;
		
		if(modifiedIndex>BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_EIGHT;
		
		if(modifiedIndex<BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_SEVEN;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_SEVEN;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_NINE;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_NINE;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex+9<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}

		return possibleMoves;
	}

}
