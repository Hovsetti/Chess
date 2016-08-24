package Controller;

import java.util.ArrayList;

import javafx.scene.image.Image;

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
	private static final int SCREEN_WIDTH = 512;
	private static final int SCREEN_HEIGHT = 512;

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
		for(int j = 0; j < possibleMoves.size(); j++){
			Model.Piece startPiece = piece;
			int startLocation = piece.getCurrentLocation();
			Model.Piece secondPiece = squares[possibleMoves.get(j)].getPiece();
			squares[piece.getCurrentLocation()].setPiece(new Model.Piece("unoccupied space", '-'));
			squares[piece.getCurrentLocation()].setIsOccupied(false);
			squares[possibleMoves.get(j)].setPiece(startPiece);
			squares[possibleMoves.get(j)].setIsOccupied(true);
			piece.setCurrentLocation(possibleMoves.get(j));
			if(!isCheck('k', piece.getColor())){
				if(!finalMoves.contains(squares[possibleMoves.get(j)].getSpace())){
					finalMoves.add(squares[possibleMoves.get(j)].getSpace());
				}
			}
			squares[startLocation].setPiece(startPiece);
			squares[startLocation].setIsOccupied(true);
			piece.setCurrentLocation(startLocation);
			squares[possibleMoves.get(j)].setPiece(secondPiece);
			if(secondPiece.getColor().equals("unoccupied space")){
				squares[possibleMoves.get(j)].setIsOccupied(false);
			}else{
				squares[possibleMoves.get(j)].setIsOccupied(true);
			}
		} 
	}

	public boolean isCheckmate(String color){
		boolean isCheckmate = true;
		for(int j = 0; j < squares.length && isCheckmate; j++){
			if(squares[j].getPiece().getColor().equals(color)){
				Model.Piece piece = squares[j].getPiece();
				piece.setPossibleMoves();
				checkSecondaryPieceMoves(piece);
				ArrayList<Integer> checkingMoves = (ArrayList<Integer>) this.checkingMoves.clone();
				for(int i = 0; i < checkingMoves.size() && isCheckmate; i++){
					Model.Piece startPiece = piece;
					int startLocation = piece.getCurrentLocation();
					int moveLocation = checkingMoves.get(i);
					Model.Piece secondPiece = squares[moveLocation].getPiece();
					squares[piece.getCurrentLocation()].setPiece(new Model.Piece("unoccupied space", '-'));
					squares[piece.getCurrentLocation()].setIsOccupied(false);
					squares[moveLocation].setPiece(startPiece);
					squares[moveLocation].setIsOccupied(true);
					piece.setCurrentLocation(moveLocation);
					if(!isCheck('k', color)){
						isCheckmate = false;
					}
					squares[startLocation].setPiece(startPiece);
					squares[startLocation].setIsOccupied(true);
					piece.setCurrentLocation(startLocation);
					squares[moveLocation].setPiece(secondPiece);
					if(secondPiece.getColor().equals("unoccupied space")){
						squares[moveLocation].setIsOccupied(false);
					}else{
						squares[moveLocation].setIsOccupied(true);
					}
				}
			}
		}
		return isCheckmate;
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

			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isDiagonalPath){
				offset = -9;

				isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}else if(checkSeven%1==0){
			offset = -7;

			isDiagonalPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isDiagonalPath){
				offset = 7;

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

			isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			if(!isStraightPath){
				offset = 8;

				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}else if(Math.floor(pieceLocation/8) == Math.floor(squareLocation/8)){
			if(squareLocation > pieceLocation){
				offset = -1;

				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);

			}else{
				offset = 1;

				isStraightPath = checkTruePath(pieceLocation, squareLocation, offset);
			}
		}
		return isStraightPath;
	}

	private boolean checkTruePath(int peiceLocation, int squareLocation, int offset){
		boolean isTruePath = true;
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
			if(peiceLocation%MOD_VALUE == 0 && squareLocation%MOD_VALUE == 7){
				isTruePath = false;
			}
			if(peiceLocation%MOD_VALUE == 7 && squareLocation%MOD_VALUE == 0){
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
			if(squareLocation+16 == pieceLocation && !squares[squareLocation].getIsOccupied() && !squares[squareLocation+8].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation+8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation+7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black") && squareLocation%MOD_VALUE>pieceLocation%MOD_VALUE){
				arrayList.add(squareLocation);
			}else if(squareLocation+9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("black") && squareLocation%MOD_VALUE<pieceLocation%MOD_VALUE){
				arrayList.add(squareLocation);
			}
		}else if(piece.getColor().equals("black")){
			if(squareLocation-16 == pieceLocation && !squares[squareLocation].getIsOccupied() && !squares[squareLocation-8].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation-8 == pieceLocation && !squares[squareLocation].getIsOccupied()){
				arrayList.add(squareLocation);
			}else if(squareLocation-7 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")  && squareLocation%MOD_VALUE<pieceLocation%MOD_VALUE){
				arrayList.add(squareLocation);
			}else if(squareLocation-9 == pieceLocation && squares[squareLocation].getPiece().getColor().equals("white")  && squareLocation%MOD_VALUE>pieceLocation%MOD_VALUE){
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

		isCheck = checkHorizontalForOffset(startPosition, offset);
		if(!isCheck){
			offset = -1; 

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

		isCheck = checkVerticalForOffset(startPosition, offset);
		if(!isCheck){
			offset = -8; 

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

		isCheck = checkDiagonalRightForOffset(startPosition, offset);
		if(!isCheck){
			offset = -7; 

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

		isCheck = checkDiagonalLeftForOffset(startPosition, offset);
		if(!isCheck){
			offset = -9; 

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

		isCheck = checkCanJumpForOffset(startPosition, offset);
		if(!isCheck){
			offset = -6; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 10; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -10; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 15; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -15; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = 17; 

			isCheck = checkCanJumpForOffset(startPosition, offset);
		}
		if(!isCheck){
			offset = -17; 

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

	public Model.Piece promotePawn(Model.Piece piece){
		Model.Piece tempPiece = piece;
		if(piece.getSymbol() == 'p' || piece.getSymbol() == 'P'){
			if(piece.getColor().equals("white")){
				if(piece.getCurrentLocation() < 8){
					tempPiece = new Model.Queen("white", 'Q');
					tempPiece.setImage(new Image("View/Images/WhiteQueen.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
			}else if(piece.getColor().equals("black")){
				if(piece.getCurrentLocation() > 55){
					tempPiece = new Model.Queen("black", 'q');
					tempPiece.setImage(new Image("View/Images/BlackQueen.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
			}
		}
		return tempPiece;
	}
}
