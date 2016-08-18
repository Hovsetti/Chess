package Controller;

import java.util.ArrayList;

public class CheckMove {

	private ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
	private ArrayList<Integer> checkingMoves = new ArrayList<Integer>();
	private ArrayList<String> finalMoves = new ArrayList<String>();
	private ArrayList<Integer> movesOnBoard = new ArrayList<Integer>();
	boolean keepRunning = true;
	private Model.Square[] squares;
	private final int MOD_VALUE = 8;
	private final int BOARD_MIN = -1;
	private final int BOARD_MAX = 64;
	private int checkOffset;

	public CheckMove(Model.Square[] squares){
		this.squares = squares;
	}

	public void checkPossibleMoves(Model.Piece piece){
		possibleMoves.clear();
		movesOnBoard.clear();
		ArrayList<Integer> moves = piece.getPossibleMoves();
		for(int j = 0; j < moves.size() && keepRunning; j++){
			int currentMove = moves.get(j);
			if(currentMove>BOARD_MIN && currentMove<BOARD_MAX){
				movesOnBoard.add(currentMove);
			}
		}
		removeImpossibleMoves(piece, possibleMoves);
		removeCheckViolation(piece);
	}

	public void checkSecondaryPieceMoves(Model.Piece piece){
		checkingMoves.clear();
		movesOnBoard.clear();
		ArrayList<Integer> moves = piece.getPossibleMoves();
		for(int j = 0; j < moves.size() && keepRunning; j++){
			int currentMove = moves.get(j);
			if(currentMove>BOARD_MIN && currentMove<BOARD_MAX){
				movesOnBoard.add(currentMove);
			}
		}
		removeImpossibleMoves(piece, checkingMoves);
	}

	public ArrayList<String> getFinalMoves(){
		return finalMoves;
	}

	public void clearFinalMoves(){
		finalMoves.clear();
	}

	private void removeImpossibleMoves(Model.Piece piece, ArrayList<Integer> arrayList){
		int pieceLocation = piece.getCurrentLocation();
		for(int j = 0; j<movesOnBoard.size(); j++){
			boolean spotAdded = false;
			if(!squares[movesOnBoard.get(j)].getPiece().getColor().equals(piece.getColor())){
				if(piece.getCanJump()){
					checkJumpMoves(pieceLocation, movesOnBoard.get(j), arrayList);
					spotAdded = true;
				}
				if(!spotAdded){
					if(piece.getHasSpecialAttackMove()){
						checkSpecialAttacks(piece, pieceLocation, movesOnBoard.get(j), arrayList);
					}else if(hasPathToPiece(pieceLocation, movesOnBoard.get(j), piece)){
						arrayList.add(movesOnBoard.get(j));
					}
				}
			}
		}
	}

	private void removeCheckViolation(Model.Piece piece){
		if(isCheck('k', piece.getColor())){

		}else{
			if(piece.getSymbol()=='k' || piece.getSymbol()=='K'){
				for(int j = 0; j < possibleMoves.size(); j++){
					finalMoves.add(squares[possibleMoves.get(j)].getSpace());
				}
			}
			squares[piece.getCurrentLocation()].setPiece(new Model.Piece("unoccupied space", '-'));
			squares[piece.getCurrentLocation()].setIsOccupied(false);
			if(isCheck('k', piece.getColor())){
				squares[piece.getCurrentLocation()].setPiece(piece);
				squares[piece.getCurrentLocation()].setIsOccupied(true);
				hasPathToPiece(findCheckPeice('k', piece.getColor()).getCurrentLocation(), piece.getCurrentLocation(), piece);
				for(int j = 0; j < MOD_VALUE; j++){
					for(int s = 0; s < possibleMoves.size(); s++){
						if(possibleMoves.get(s) == (piece.getCurrentLocation()+(checkOffset*j))){
							finalMoves.add(squares[possibleMoves.get(s)].getSpace());
						}
						if(possibleMoves.get(s) == (piece.getCurrentLocation()-(checkOffset*j))){
							finalMoves.add(squares[possibleMoves.get(s)].getSpace());
						}
					}
				}

			}else{
				squares[piece.getCurrentLocation()].setPiece(piece);
				squares[piece.getCurrentLocation()].setIsOccupied(true);
				for(int j = 0; j < possibleMoves.size(); j++){
					finalMoves.add(squares[possibleMoves.get(j)].getSpace());
				}
			}
		}
	}

	private void checkJumpMoves(int pieceLocation, int squareLocation, ArrayList<Integer> arrayList){
		if(squareLocation-6==pieceLocation || squareLocation-15==pieceLocation || squareLocation+10==pieceLocation || squareLocation+17==pieceLocation){
			if(pieceLocation%MOD_VALUE > squareLocation%MOD_VALUE){
				arrayList.add(squareLocation);
			}
		}else if(squareLocation+6==pieceLocation || squareLocation+15==pieceLocation || squareLocation-10==pieceLocation || squareLocation-17==pieceLocation){
			if(pieceLocation%MOD_VALUE < squareLocation%MOD_VALUE){
				arrayList.add(squareLocation);;
			}
		}
	}

	private boolean hasPathToPiece(int pieceLocation, int squareLocation, Model.Piece piece){
		boolean hasPath = false;
		if(piece.getCanMoveStraightEveryTurn() && isStraightPath(pieceLocation, squareLocation)){
			hasPath = true;
		}else if(piece.getCanMoveDiagonalEveryTurn() && isDiagonalPath(pieceLocation, squareLocation) && !hasPath){
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
			offset = 9;
			checkOffset = offset;
			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isDiagonalPath){
				offset = -9;
				checkOffset = offset;
				isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}else if(checkSeven%1==0){
			offset = -7;
			checkOffset = offset;
			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isDiagonalPath){
				offset = 7;
				checkOffset = offset;
				isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}
		return isDiagonalPath;
	}

	private boolean isStraightPath(int pieceLocation, int squareLocation){
		boolean isStraightPath = false;
		int offset;
		if(squareLocation%MOD_VALUE == pieceLocation%MOD_VALUE){
			offset = -8;
			checkOffset = offset;
			isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isStraightPath){
				offset = 8;
				checkOffset = offset;
				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}else if(Math.floor(pieceLocation/8) == Math.floor(squareLocation/8)){
			if(squareLocation > pieceLocation){
				offset = -1;
				checkOffset = offset;
				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);

			}else{
				offset = 1;
				checkOffset = offset;
				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}
		return isStraightPath;
	}

	private boolean checkTruePath(int peiceLocation, int squareLocation, int offset){
		boolean isTruePath = true;
		if(squareLocation == 9 && offset == 7){
			System.out.println(squareLocation%MOD_VALUE + " < " +  peiceLocation%MOD_VALUE);
		}
		if(offset==-9 && squareLocation%MOD_VALUE < peiceLocation%MOD_VALUE){
			isTruePath = false;
		}else if(offset==9 && squareLocation%MOD_VALUE>peiceLocation%MOD_VALUE){
			isTruePath = false;
		}else if(offset==-7 && squareLocation%MOD_VALUE>peiceLocation%MOD_VALUE){
			isTruePath = false;
		}else if(offset==7 && squareLocation%MOD_VALUE < peiceLocation%MOD_VALUE){
			isTruePath = false;
		}else if(offset==-7 && Math.floor(squareLocation/MOD_VALUE) == Math.floor(peiceLocation/MOD_VALUE)){
			isTruePath = false;
		}else if(offset==7 && Math.floor(squareLocation/MOD_VALUE) == Math.floor(peiceLocation/MOD_VALUE)){
			isTruePath = false;
		}else if(offset==1 && Math.floor(squareLocation/MOD_VALUE) != Math.floor(peiceLocation/MOD_VALUE)){
			isTruePath = false;
		}else if(offset==-1 && Math.floor(squareLocation/MOD_VALUE) != Math.floor(peiceLocation/MOD_VALUE)){
			isTruePath = false;
		}else if(squares[peiceLocation].getPiece().getSymbol() == 'k' || squares[peiceLocation].getPiece().getSymbol() == 'K'){
			if((peiceLocation%MOD_VALUE)+(squareLocation%MOD_VALUE)==7){
				isTruePath = false;
			}
		}
		int checkSquare = squareLocation+offset;
		while(checkSquare != peiceLocation && isTruePath){
			if(checkSquare < BOARD_MAX && checkSquare > BOARD_MIN){
				if(!squares[checkSquare].getIsOccupied()){
					checkSquare += offset;
				}else{
					isTruePath = false;
				}
			}else{
				isTruePath = false;
			}
		}
		return isTruePath;
	}

	public void checkSpecialAttacks(Model.Piece piece, int pieceLocation, int squareLocation, ArrayList<Integer> arrayList){
		if(piece.getColor().equals("white")){
			if(squareLocation+16 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation+8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation+7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black")){
				arrayList.add(squareLocation);
			}else if(squareLocation+9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black")){
				arrayList.add(squareLocation);
			}
		}else if(piece.getColor().equals("black")){
			if(squareLocation-16 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation-8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation-7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")){
				arrayList.add(squareLocation);
			}else if(squareLocation-9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")){
				arrayList.add(squareLocation);
			}
		}
	}

	public boolean isCheck(char character, String color){
		Model.Piece piece = findCheckPeice(character, color);
		int startPosition = piece.getCurrentLocation();
		return checkForCheck(startPosition);
	}

	private Model.Piece findCheckPeice(char character, String color){
		char peiceCharacter = character;
		Model.Piece piece = new Model.Piece("unoccupied space", '-');
		if(color.equals("black")){
			peiceCharacter = Character.toLowerCase(peiceCharacter);
		}else{
			peiceCharacter = Character.toUpperCase(peiceCharacter);
		}
		for(int j = 0; j<squares.length; j++){
			if(squares[j].getPiece().getSymbol() == peiceCharacter){
				piece = squares[j].getPiece();
			}
		}
		return piece;
	}

	private boolean checkForCheck(int startPosition){
		boolean isCheck = false;
		if(checkHorizontalCheck(startPosition)){
			isCheck = true;
		}else if(checkVerticalCheck(startPosition) && !isCheck){
			isCheck = true;
		}else if(checkDiagonalRightCheck(startPosition) && !isCheck){
			isCheck = true;
		}else if(checkDiagonalLeftCheck(startPosition) && !isCheck){
			isCheck = true;
		}else if(checkCanJumpCheck(startPosition) && !isCheck){
			isCheck = true;
		}
		checkingMoves.clear();
		return isCheck;
	}

	private boolean checkHorizontalCheck(int startPosition){
		boolean isCheck = false;
		int offset = 1;
		checkOffset = offset;
		isCheck = checkHorizontalForOffset(startPosition, offset);
		if(!isCheck){
			offset = -1; 
			checkOffset = offset;
			isCheck = checkHorizontalForOffset(startPosition, offset);
		}
		return isCheck;
	}

	private boolean checkHorizontalForOffset(int startPosition, int offset){
		checkingMoves.clear();
		boolean isCheck = false;
		boolean keepRunning = true;
		double rowCheck = Math.floor(startPosition/8);
		int checkLocation = startPosition + offset;
		while(Math.floor(checkLocation/8) == rowCheck && keepRunning && checkLocation > BOARD_MIN && checkLocation < BOARD_MAX){
			if(!squares[checkLocation].getIsOccupied()){
				checkLocation += offset;
			}else{
				Model.Piece piece = squares[checkLocation].getPiece();
				if(piece.getColor().equals(squares[startPosition].getPiece().getColor())){
					keepRunning = false;
				}else{
					piece.setPossibleMoves();
					checkSecondaryPieceMoves(piece);
					for(int j = 0; j<checkingMoves.size(); j++){
						if(startPosition == (checkingMoves.get(j))){
							isCheck = true;
						}
					}
					keepRunning = false;
				}
			}
		}
		return isCheck;
	}


	private boolean checkVerticalCheck(int startPosition){
		boolean isCheck = false;
		int offset = 8;
		checkOffset = offset;
		isCheck = checkVerticalForOffset(startPosition, offset);
		if(!isCheck){
			offset = -8; 
			checkOffset = offset;
			isCheck = checkVerticalForOffset(startPosition, offset);
		}
		return isCheck;
	}

	private boolean checkVerticalForOffset(int startPosition, int offset){
		checkingMoves.clear();
		boolean isCheck = false;
		boolean keepRunning = true;
		int checkLocation = startPosition + offset;
		while(checkLocation < BOARD_MAX && checkLocation > BOARD_MIN && keepRunning){
			if(!squares[checkLocation].getIsOccupied()){
				checkLocation += offset;
			}else{
				Model.Piece piece = squares[checkLocation].getPiece();
				if(piece.getColor().equals(squares[startPosition].getPiece().getColor())){
					keepRunning = false;
				}else{
					piece.setPossibleMoves();
					checkSecondaryPieceMoves(piece);
					for(int j = 0; j<checkingMoves.size(); j++){
						if(startPosition == (checkingMoves.get(j))){
							isCheck = true;
						}
					}
					keepRunning = false;
				}
			}
		}
		return isCheck;
	}

	private boolean checkDiagonalRightCheck(int startPosition){
		boolean isCheck = false;
		int offset = 9;
		checkOffset = offset;
		isCheck = checkDiagonalRightForOffset(startPosition, offset);
		if(!isCheck){
			offset = -7; 
			checkOffset = offset;
			isCheck = checkDiagonalRightForOffset(startPosition, offset);
		}
		return isCheck;
	}

	private boolean checkDiagonalRightForOffset(int startPosition, int offset){
		checkingMoves.clear();
		boolean isCheck = false;
		boolean keepRunning = true;
		int checkLocation = startPosition + offset;
		while(checkLocation%MOD_VALUE > startPosition%MOD_VALUE && keepRunning && checkLocation > BOARD_MIN && checkLocation < BOARD_MAX){
			if(!squares[checkLocation].getIsOccupied()){
				checkLocation += offset;
			}else{
				Model.Piece piece = squares[checkLocation].getPiece();
				if(piece.getColor().equals(squares[startPosition].getPiece().getColor())){
					keepRunning = false;
				}else{
					piece.setPossibleMoves();
					checkSecondaryPieceMoves(piece);
					for(int j = 0; j<checkingMoves.size(); j++){
						if(startPosition == (checkingMoves.get(j))){
							isCheck = true;
						}
					}
					keepRunning = false;
				}
			}
		}
		return isCheck;
	}


	private boolean checkDiagonalLeftCheck(int startPosition){
		boolean isCheck = false;
		int offset = 7;
		checkOffset = offset;
		isCheck = checkDiagonalLeftForOffset(startPosition, offset);
		if(!isCheck){
			offset = -9; 
			checkOffset = offset;
			isCheck = checkDiagonalLeftForOffset(startPosition, offset);
		}
		return isCheck;
	}

	private boolean checkDiagonalLeftForOffset(int startPosition, int offset){
		checkingMoves.clear();
		boolean isCheck = false;
		boolean keepRunning = true;
		int checkLocation = startPosition + offset;
		while(checkLocation%MOD_VALUE < startPosition%MOD_VALUE && keepRunning && checkLocation > BOARD_MIN && checkLocation < BOARD_MAX){
			if(!squares[checkLocation].getIsOccupied()){
				checkLocation += offset;
			}else{
				Model.Piece piece = squares[checkLocation].getPiece();
				if(piece.getColor().equals(squares[startPosition].getPiece().getColor())){
					keepRunning = false;
				}else{
					piece.setPossibleMoves();
					checkSecondaryPieceMoves(piece);
					for(int j = 0; j<checkingMoves.size(); j++){
						if(startPosition == (checkingMoves.get(j))){
							isCheck = true;
						}
					}
					keepRunning = false;
				}
			}
		}
		return isCheck;
	}


	private boolean checkCanJumpCheck(int startPosition){
		boolean isCheck = false;
		int offset = 6;
		checkOffset = offset;
		isCheck = checkCanJumpForOffset(startPosition, offset);
		if(!isCheck){
			offset = -6; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 10; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -10; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 15; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -15; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 17; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -17; 
			checkOffset = offset;
			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		return isCheck;
	}

	private boolean checkCanJumpForOffset(int startPosition, int offset){
		checkingMoves.clear();
		boolean isCheck = false;
		int checkLocation = startPosition + offset;
		if(checkLocation > BOARD_MIN && checkLocation < BOARD_MAX){
			if(squares[checkLocation].getIsOccupied()){
				Model.Piece piece = squares[checkLocation].getPiece();
				if(!piece.getColor().equals(squares[startPosition].getPiece().getColor())){
					piece.setPossibleMoves();
					checkSecondaryPieceMoves(piece);
					for(int j = 0; j<checkingMoves.size(); j++){
						if(startPosition == (checkingMoves.get(j))){
							isCheck = true;
						}
					}
				}
			}
		}
		return isCheck;
	}
}
