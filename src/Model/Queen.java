package Model;

import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(String color, char symbol) {
		super(color, symbol);
	}
	
	public ArrayList<String> getPossibleMoves(String startingLocation, Square[] squares){
		ArrayList<String> possibleMoves = new ArrayList<String>();
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

		for(int j = boardLocation-7; j%8 > boardLocation%8 && j>-1; j-=7){//up and left for 7
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation+7; j%8 < boardLocation%8 && j<64; j+=7){//check down and right for +7
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation-9; j%8 < boardLocation%8 && j>-1; j-=9){
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation+9; j%8 > boardLocation%8 && j<64; j+=9){
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}
		
		for(int j = boardLocation-1; j%8 < boardLocation%8 && j>-1; j--){//check all values less than starting location horizontally
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation+1; j%8 > boardLocation%8 && j<64; j++){//check all values greater than starting location horizontally
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation-8; j >= 0 && j>-1; j-=8){//check all values less than starting location vertically
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		for(int j = boardLocation+8; j <= 63 && j<64; j+=8){//check all values greater than starting location vertically
			if(!squares[j].getIsOccupied()){
				possibleMoves.add(squares[j].getSpace());
			}else{
				if(squares[j].getPiece().getColor().equals(this.color)){
					break;
				}else{
					possibleMoves.add(squares[j].getSpace());
					break;
				}
			}
		}

		return possibleMoves;
	}

}
