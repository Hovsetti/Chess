package Controller;

import java.util.ArrayList;

public class CheckMove {

	private ArrayList<String> possibleMoves = new ArrayList<String>();
	private ArrayList<Integer> movesOnBoard = new ArrayList<Integer>();
	private boolean keepRunning = true;
	private Model.Square[] squares;
	private final int MOD_VALUE = 8;
	private final int BOARD_MIN = -1;
	private final int BOARD_MAX = 64;

	public CheckMove(Model.Square[] squares){
		this.squares = squares;
	}

	private void addPossibleMove(String position){
		possibleMoves.add(position);
	}

	public void checkPossibleMoves(Model.Piece piece){
		this.possibleMoves.clear();
		ArrayList<Integer> possibleMoves = piece.getPossibleMoves();
		int modBoardLocation = piece.getCurrentLocation()%MOD_VALUE;
		for(int j = 0; j < possibleMoves.size() && keepRunning; j++){
			int currentMove = possibleMoves.get(j);
			if(currentMove>BOARD_MIN && currentMove<BOARD_MAX){
				checkRight(piece, currentMove, modBoardLocation);
				checkLeft(piece, currentMove, modBoardLocation);
				checkUp(piece, currentMove, modBoardLocation);
				checkDown(piece, currentMove, modBoardLocation);
			}
		}
		removeImpossibleMoves(piece);
		movesOnBoard.clear();
	}

	private void checkRight(Model.Piece piece, int currentMove, int modBoardLocation){
		if(currentMove%MOD_VALUE > modBoardLocation){
			movesOnBoard.add(currentMove);
		}
	}

	private void checkLeft(Model.Piece piece, int currentMove, int modBoardLocation){
		if(currentMove%MOD_VALUE < modBoardLocation && currentMove>BOARD_MIN && currentMove<BOARD_MAX){
			movesOnBoard.add(currentMove);
		}
	}

	private void checkDown(Model.Piece piece, int currentMove, int modBoardLocation){
		if(currentMove%MOD_VALUE == modBoardLocation){
			movesOnBoard.add(currentMove);
		}
	}

	private void checkUp(Model.Piece piece, int currentMove, int modBoardLocation){
		if(currentMove%MOD_VALUE == modBoardLocation){
			movesOnBoard.add(currentMove);
		}
	}
	
	public void clearPossibleMoves(){
		possibleMoves.clear();
	}

	public ArrayList<String> getPossibleMoves(){
		return possibleMoves;
	}
	
	private void removeImpossibleMoves(Model.Piece piece){
		int pieceLocation = piece.getCurrentLocation();
		for(int j = 0; j<movesOnBoard.size(); j++){
			boolean spotAdded = false;
			if(!squares[movesOnBoard.get(j)].getPiece().getColor().equals(piece.getColor())){
				if(piece.getCanJump()){
					addPossibleMove(squares[movesOnBoard.get(j)].getSpace());
					spotAdded = true;
				}
				if(!spotAdded && hasPathToPiece(pieceLocation, movesOnBoard.get(j))){
					if(piece.getHasSpecialAttackMove()){
						checkSpecialAttacks(piece, pieceLocation, movesOnBoard.get(j));
					}else{
						addPossibleMove(squares[movesOnBoard.get(j)].getSpace());
					}
				}
			}
		}
	}
	
	private boolean hasPathToPiece(int pieceLocation, int squareLocation){
		boolean hasPath = false;
		if(isStraightPath(pieceLocation, squareLocation)){
			hasPath = true;
		}else if(isDiagonalPath(pieceLocation, squareLocation) && !hasPath){
			hasPath = true;
		}
		return hasPath;
	}
	
	private boolean isDiagonalPath(int pieceLocation, int squareLocation){
		boolean isDiagonalPath = false;
		int offset;
		float checkNine = ((pieceLocation - squareLocation)/(float)9);
		float checkSeven = ((pieceLocation - squareLocation)/(float)7);
		if(checkNine%1==0){
			if(squareLocation%MOD_VALUE<pieceLocation%MOD_VALUE){
				offset = 9;
			}else{
				offset = -9;
			}
			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
		}else if(checkSeven%1==0){
			if(squareLocation%MOD_VALUE<pieceLocation%MOD_VALUE){
				offset = -7;
			}else{
				offset = 7;
			}
			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
		}
		return isDiagonalPath;
	}
	
	private boolean isStraightPath(int pieceLocation, int squareLocation){
		boolean isStraightPath = false;
		int offset;
		if(squareLocation%MOD_VALUE == pieceLocation%MOD_VALUE){
			if(squareLocation > pieceLocation){
				offset = -8;
			}else{
				offset = 8;
			}
			isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
		}else if(Math.floor(pieceLocation/8) == Math.floor(squareLocation/8)){
			if(squareLocation > pieceLocation){
				offset = -1;
			}else{
				offset = 1;
			}
			isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
		}
		return isStraightPath;
	}
	
	private boolean checkTruePath(int peiceLocation, int squareLocation, int offset){
		boolean isTruePath = true;
		int checkSquare = squareLocation+offset;
		while(checkSquare != peiceLocation){
			if(checkSquare < BOARD_MAX && checkSquare > BOARD_MIN){
				if(!squares[checkSquare].getIsOccupied()){
					checkSquare += offset;
				}else{
					isTruePath = false;
					break;
				}
			}else{
				break;
			}
		}
		return isTruePath;
	}
	
	public void checkSpecialAttacks(Model.Piece piece, int pieceLocation, int squareLocation){
		if(piece.getColor().equals("white")){
			if(squareLocation+16 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation+8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation+7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black")){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation+9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black")){
				addPossibleMove(squares[squareLocation].getSpace());
			}
		}else if(piece.getColor().equals("black")){
			if(squareLocation-16 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation-8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation-7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")){
				addPossibleMove(squares[squareLocation].getSpace());
			}else if(squareLocation-9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")){
				addPossibleMove(squares[squareLocation].getSpace());
			}
		}
	}
}
