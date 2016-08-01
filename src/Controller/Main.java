package Controller;

import java.util.Iterator;

import View.Board;

public class Main {

	public static void main(String[] args) {
		Controller.FileIo fileIo = new Controller.FileIo(args[0]);
		fileIo.readFile();
		Iterator iterator = fileIo.getActionArray().iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		fileIo.getBoard().drawBoard();
	}
}
