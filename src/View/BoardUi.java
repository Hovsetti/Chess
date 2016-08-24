package View;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class BoardUi extends Application{
	private static final int SCREEN_WIDTH = 512;
	private static final int SCREEN_HEIGHT = 512;
	private SquarePane[] stackPanes = new SquarePane[64];
	private Model.Square[] squares;
	private Controller.CheckMove checkMove;

	@Override
	public void start(Stage primaryStage){
		Grid gridpane = new Grid();
		Scene scene = new Scene(gridpane, SCREEN_WIDTH, SCREEN_HEIGHT);
		Controller.FileIo fileIo = new Controller.FileIo("C:\\Users\\Gavin\\workspace\\Chess\\PlacePieces.txt");
		fileIo.readFile();
		squares = fileIo.getBoard().getSquares();
		checkMove = new Controller.CheckMove(squares);
		initializeBoard(gridpane);
		primaryStage.setTitle("Gavin Hovseth Chess");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private void initializeBoard(Grid gridpane){
		final int size = 8 ;
		int location = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col ++) {
				String color ;
				if ((row + col) % 2 == 0) {
					color = "white";
				} else {
					color = "grey";
				}
				SquarePane square = new SquarePane(squares[location], checkMove, stackPanes, color, gridpane);
				square.setChild();
				square.setStyle("-fx-background-color: "+color+";");
				square.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				gridpane.add(square, col, row);
				stackPanes[location] = square;
				location++;
			}
		}

		for (int i = 0; i < size; i++) {
			gridpane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
			gridpane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
		}
	}
}
