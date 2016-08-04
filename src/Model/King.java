package Model;

import java.util.ArrayList;

public class King extends Piece{

	public King(String color, char symbol) {
		super(color, symbol);
	}

	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		
		int boardLocation = findPieceLocation(startingLocation, squares);
		
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
