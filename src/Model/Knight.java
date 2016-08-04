package Model;

import java.util.ArrayList;

public class Knight extends Piece{
	
	private final int MOVE_SIX = 6;
	private final int MOVE_TEN = 10;
	private final int MOVE_FIFTEEN = 15;
	private final int MOVE_SEVENTEEN = 17;

	public Knight(String color, char symbol) {
		super(color, symbol);
	}
	
	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){

		int boardLocation = findPieceLocation(startingLocation, squares);
		
		int modBoardLocation = boardLocation%MOD_VALUE;
		
		int modifiedIndex = boardLocation-MOVE_SIX;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_SIX;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_FIFTEEN;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_FIFTEEN;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_SEVENTEEN;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_SEVENTEEN;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation-MOVE_TEN;
		
		if(modifiedIndex%MOD_VALUE < modBoardLocation && modifiedIndex>BOARD_MIN){
			addPossibleMoves(modifiedIndex, squares);
		}
		
		modifiedIndex = boardLocation+MOVE_TEN;
		
		if(modifiedIndex%MOD_VALUE > modBoardLocation && modifiedIndex<BOARD_MAX){
			addPossibleMoves(modifiedIndex, squares);
		}

		return possibleMoves;
	}

}
