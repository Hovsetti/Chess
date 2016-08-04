package Model;

import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(String color, char symbol) {
		super(color, symbol);
	}

	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		
		int boardLocation = findPieceLocation(startingLocation, squares);
		
		int modBoardLocation = boardLocation%MOD_VALUE;

		for(int j = boardLocation-MOVE_SEVEN; j%MOD_VALUE > modBoardLocation && j>BOARD_MIN && keepRunning; j-=MOVE_SEVEN){
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		for(int j = boardLocation+MOVE_SEVEN; j%MOD_VALUE < modBoardLocation && j<BOARD_MAX && keepRunning; j+=MOVE_SEVEN){
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		for(int j = boardLocation-MOVE_NINE; j%MOD_VALUE < modBoardLocation && j>BOARD_MIN && keepRunning; j-=MOVE_NINE){
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		for(int j = boardLocation+MOVE_NINE; j%MOD_VALUE > modBoardLocation && j<BOARD_MAX && keepRunning; j+=MOVE_NINE){
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		return possibleMoves;
	}


}
