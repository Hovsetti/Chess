package View;

import javafx.scene.layout.GridPane;

public class Grid extends GridPane{
	
	private boolean pieceSelected = false;
	private Model.Square saveSquare = null;
	private SquarePane savePane = null;
	private Controller.TurnHandler turnHandler = new Controller.TurnHandler();
	private String currentPlayer = turnHandler.getCurrentPlayer();

	public SquarePane getSavePane() {
		return savePane;
	}

	public void setSavePane(SquarePane savePane) {
		this.savePane = savePane;
	}

	public boolean pieceSelected() {
		return pieceSelected;
	}

	public void setPieceSelected(boolean pieceSelected) {
		this.pieceSelected = pieceSelected;
	}
	
	public Model.Square getSaveSquare(){
		return saveSquare;
	}
	
	public void setSaveSquare(Model.Square saveSquare){
		this.saveSquare = saveSquare;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void changeTurn() {
		this.currentPlayer = turnHandler.changTurn();
	}
}
