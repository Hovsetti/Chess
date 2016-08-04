package Model;

import java.util.ArrayList;

public class Rook extends Piece{

	public Rook(String color, char symbol) {
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

		for(int j = boardLocation-MOVE_ONE; j%MOD_VALUE < modBoardLocation && j>BOARD_MIN && keepRunning; j--){
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		for(int j = boardLocation+MOVE_ONE; j%MOD_VALUE > modBoardLocation && j<BOARD_MAX && keepRunning; j++){
			addPossibleMoves(j, squares);
		}

		keepRunning = true;
		
		for(int j = boardLocation-MOVE_EIGHT; j>BOARD_MIN && keepRunning; j-=MOVE_EIGHT){
			addPossibleMoves(j, squares);
		}

		keepRunning = true;
		
		for(int j = boardLocation+MOVE_EIGHT; j<BOARD_MAX && keepRunning; j+=MOVE_EIGHT){//check all values greater than starting location vertically
			addPossibleMoves(j, squares);
		}
		
		keepRunning = true;

		return possibleMoves;
	}

}
