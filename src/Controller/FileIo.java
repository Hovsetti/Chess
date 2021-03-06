package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;

public class FileIo {

	private InputStream in;
	private ArrayList<String> actions;
	private View.Board board;
	private static final int SCREEN_WIDTH = 512;
	private static final int SCREEN_HEIGHT = 512;

	public FileIo(String fileName){
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		board = new View.Board();
	}
	
	public ArrayList<String> getActionArray(){
		return actions;
	}

	public void readFile(){
		actions = new ArrayList<String>();
		String line = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String placePattern = new String("([A-Z][a-z][a-z][0-9]).*");
		String movePattern = new String("([a-z][0-9]\\s[a-z][0-9][\\*]*).*");
		String moveTwoPattern = new String("([a-z][0-9]\\s[a-z][0-9]\\s[a-z][0-9]\\s[a-z][0-9]).*");
		try {
			while(null != (line = reader.readLine())){
				line = line.trim();
				boolean matched = false;
				Pattern pattern = Pattern.compile(placePattern);
				Matcher match = pattern.matcher(line);
				if(match.matches()  && !matched){
					line = match.group(1);
					actions.add(placePiece(line));
					matched = true;
				}
				
				pattern = Pattern.compile(moveTwoPattern);
				match = pattern.matcher(line);
				if(match.matches()  && !matched){
					line = match.group(1);
					actions.add(moveTwoPieces(line));
					matched = true;
				}
				
				pattern = Pattern.compile(movePattern);
				match = pattern.matcher(line);
				if(match.matches() && !matched){
					board.drawBoard();
					line = match.group(1);
					matched = true;
					//actions.add(movePiece(line));
					System.out.println(movePiece(line));
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		board.drawBoard();
	}

		private String placePiece(String command){
			String output = "[" + command + "] = ";
			String placement = completePlacement(command.charAt(2), command.charAt(3));
			switch(command.charAt(1)){
			case 'l':
				output += "The white ";
				break;
			case 'd':
				output += "The black ";
				break;
			default:
				output += "The INVALID COLOR ";
			}
			switch(command.charAt(0)){
			case 'Q':
				output += "Queen was placed at " + placement;
				break;
			case 'K':
				output += "King was placed at " + placement;
				break;
			case 'P':
				output += "Pawn was placed at " + placement;
				break;
			case 'R':
				output += "Rook was placed at " + placement;
				break;
			case 'N':
				output += "Knight was placed at " + placement;
				break;
			case 'B':
				output += "Bishop was placed at " + placement;
				break;
			default:
				output += "INVALID PIECE was moved to " + placement;
			}
			assignSquareValues(placement, command.charAt(1), command.charAt(0));
			return output;
		}
	
		private String movePiece(String command){
			String output = "[" + command + "] = ";
			String startingPlacement = completePlacement(command.charAt(0), command.charAt(1));
			String finalPlacement = completePlacement(command.charAt(3), command.charAt(4));
			if(!board.attemptMove(startingPlacement, finalPlacement)){
				output = "INVALID MOVE FROM " + startingPlacement + " TO "+ finalPlacement;
			}else{
				if(board.getPotentialMove()){
					output += "Piece from " + startingPlacement + " was moved to " + finalPlacement + " and took a piece";
				}else{
					output += "Piece from " + startingPlacement + " was moved to "+ finalPlacement;
				}
			}
			return output;
		}
	
		private String moveTwoPieces(String command){
			String kingStartSpace = completePlacement(command.charAt(0), command.charAt(1));
			String kingFinalSpace = completePlacement(command.charAt(3), command.charAt(4));
			String rookStartSpace = completePlacement(command.charAt(6), command.charAt(7));
			String rookFinalSpace = completePlacement(command.charAt(9), command.charAt(10));
			return "[" + command + "] = King was moved from " + kingStartSpace + " to " + kingFinalSpace + " and the rook was moved from " + 
					rookStartSpace + " to " + rookFinalSpace;
		}
		
		private String checkRank(char number){
			String rank = "";
			switch(number){
			case '1':
				rank = "1";
				break;
			case '2':
				rank = "2";
				break;
			case '3':
				rank = "3";
				break;
			case '4':
				rank = "4";
				break;
			case '5':
				rank = "5";
				break;
			case '6':
				rank = "6";
				break;
			case '7':
				rank = "7";
				break;
			case '8':
				rank = "8";
				break;
			default:
				rank = " INVALID RANK";
			}
			return rank;
		}
		
		private String checkFile(char letter){
			String file = "";
			switch(letter){
			case 'a':
				file = "a";
				break;
			case 'b':
				file = "b";
				break;
			case 'c':
				file ="c";
				break;
			case 'd':
				file = "d";
				break;
			case 'e':
				file = "e";
				break;
			case 'f':
				file = "f";
				break;
			case 'g':
				file = "g";
				break;
			case 'h':
				file = "h";
				break;
			default:
				file = "INVALID FILE ";
			}
			return file;
		}
		
		private String completePlacement(char file, char rank){
			return checkFile(file)+checkRank(rank);
		}

		public View.Board getBoard(){
			return board;
		}
		
		private void assignSquareValues(String space, char color, char symbol){
			boolean keepRunning = true;
			Model.Square[] squares = board.getSquares();
			Model.Piece piece = setStartingPiece(symbol, color);
			for(int j = 0; j < squares.length && keepRunning; j++){
				if(squares[j].getSpace().equals(space)){
					piece.setCurrentLocation(j);
					squares[j].setPiece(piece);
					squares[j].setIsOccupied(true);
					keepRunning = false;
				}
			}
			board.setSquares(squares);
		}
		
		private Model.Piece setStartingPiece(char symbol, char color){
			Model.Piece piece = new Model.Piece("unocupied space", '-');
			switch(symbol){
			case 'Q':
				piece = new Model.Queen("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackQueen.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhiteQueen.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			case 'K':
				piece = new Model.King("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackKing.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhiteKing.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			case 'P':
				piece = new Model.Pawn("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackPawn.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhitePawn.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			case 'R':
				piece = new Model.Rook("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackRook.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhiteRook.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			case 'N':
				piece = new Model.Knight("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackKnight.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhiteKnight.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			case 'B':
				piece = new Model.Bishop("unocupied space", '-');
				if(color == 'd'){
					piece.setColor("black");
					piece.setSymbol(Character.toLowerCase(symbol));
					piece.setImage(new Image("View/Images/BlackBishop.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}else{
					piece.setColor("white");
					piece.setSymbol(Character.toUpperCase(symbol));
					piece.setImage(new Image("View/Images/WhiteBishop.png", SCREEN_WIDTH/16, SCREEN_HEIGHT/16, false, false));
				}
				break;
			}
			return piece;
		}
}
