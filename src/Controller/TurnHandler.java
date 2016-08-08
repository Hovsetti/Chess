package Controller;

public class TurnHandler {
	
	private String player1 = "white";
	private String player2 = "black";
	private String currentPlayer = "white";
	
	public String changTurn(){
		if(currentPlayer.equals(player1)){
			currentPlayer = player2;
		}else{
			currentPlayer = player1;
		}
		return currentPlayer;
	}
	
	public String getCurrentPlayer(){
		return currentPlayer;
	}

}
