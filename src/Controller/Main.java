package Controller;

import java.util.ArrayList;
import java.util.Iterator;

import Model.Piece;
import Model.Queen;

public class Main {

	public static void main(String[] args) {
		Controller.FileIo fileIo = new Controller.FileIo(args[0]);
		fileIo.readFile();
		Iterator<String> iterator = fileIo.getActionArray().iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		fileIo.getBoard().drawBoard();
		Model.Square[] squares = fileIo.getBoard().getSquares();
		Piece testRook = (Queen) squares[63].getPiece();
		ArrayList<String> places = testRook.getPossibleMoves(squares[63].getSpace(), squares);
		for(int j = 0; j<places.size(); j++){
			System.out.println(j + places.get(j));
		}
	}
}
