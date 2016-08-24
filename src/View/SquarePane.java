package View;

import java.awt.Color;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SquarePane extends StackPane{

	private Model.Square square;
	private Controller.TurnHandler turnHandler = new Controller.TurnHandler();
	private Controller.CheckMove checkMove;
	private SquarePane[] panes;
	private String location;
	private String color;
	private Grid gridPane;
	private SquarePane thisPane = this;

	public SquarePane(Model.Square square, Controller.CheckMove checkMove, SquarePane[] panes, String color, Grid gridPane){
		this.square = square;
		this.checkMove = checkMove;
		this.panes = panes;
		location = square.getSpace();
		this.color = color;
		this.gridPane = gridPane;
		this.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent t) {
				if(!gridPane.pieceSelected()){
					peiceMouseEvent();
					gridPane.setSavePane(thisPane);
					gridPane.setSaveSquare(square);
					gridPane.setPieceSelected(true);
				}else{
					for(int j = 0; j < checkMove.getFinalMoves().size(); j++){
						if(location.equals(checkMove.getFinalMoves().get(j))){
							setStyle("-fx-background-color: "+color+";");
							square.setPiece(gridPane.getSaveSquare().getPiece());
							square.getPiece().setImage(gridPane.getSaveSquare().getPiece().getImage());
							removeChild();
							setChild();
							square.getPiece().clearPossibleMoves();
							square.getPiece().setCurrentLocation(square.getLocation());
							square.getPiece().setHasMoved(true);
							square.setIsOccupied(true);
							gridPane.getSaveSquare().setPiece(new Model.Piece("unoccupied space", '-'));
							gridPane.getSaveSquare().getPiece().setImage(null);
							gridPane.getSavePane().removeChild();
							gridPane.getSavePane().setChild();
							gridPane.getSaveSquare().setIsOccupied(false);
							square.setPiece(checkMove.promotePawn(square.getPiece()));
							removeChild();
							setChild();
						}else{
							for(int i = 0; i < panes.length; i++){
								panes[i].square.getPiece().clearPossibleMoves();
								if(panes[i].location.equals(checkMove.getFinalMoves().get(j))){
									panes[i].setStyle("-fx-background-color: "+panes[i].color+";");
								}
							}
						}
					}
					checkMove.clearFinalMoves();
					gridPane.setPieceSelected(false);
					if(checkMove.isCheck('k', turnHandler.getCurrentPlayer())){
						if(checkMove.isCheckmate(turnHandler.getCurrentPlayer())){
							System.out.println(turnHandler.getCurrentPlayer()+ " player's King is in check mate");
							
						}else{
							System.out.println(turnHandler.getCurrentPlayer() + " player's King is in check");
						}
					}else{
						System.out.println(turnHandler.getCurrentPlayer() + " player's King is safe");
					}
				}
			}
		});
	}

	public void setSquare(Model.Square square){
		this.square = square;
	}

	public void setChild(){
		ImageView imageView = new ImageView(square.getPiece().getImage());
		this.getChildren().add(imageView);
	}

	public void play(){
		System.out.println(getParent());
	}

	private void peiceMouseEvent(){
		square.getPiece().setPossibleMoves();
		checkMove.checkPossibleMoves(square.getPiece());
		highlightMoves();
	}

	private void highlightMoves(){
		for(int j = 0; j < checkMove.getFinalMoves().size(); j++){
			for(int i = 0; i < panes.length; i++){
				if(panes[i].location.equals(checkMove.getFinalMoves().get(j))){
					String color = "blue";
					panes[i].setStyle("-fx-background-color: "+color+";");
				}
			}
		}
		if (checkMove.getFinalMoves().size() < 1){
			System.out.println("No moves");
		}
	}

	private void movePiece(){
		String color = "green";
		this.setStyle("-fx-background-color: "+color+";");
	}

	public void removeChild(){
		this.getChildren().removeAll(this.getChildren());
	}

	public void getMoves(){
		square.getPiece().getPossibleMoves();
	}

}
