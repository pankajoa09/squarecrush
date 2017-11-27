import javafx.stage.Stage;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class Controller {

    private final Stage primaryStage;
    BoardFactory boardFactory = new BoardFactory();

    public Controller(Stage primaryStage) {
        System.out.println("controller initiated");
        this.primaryStage = primaryStage;
        View view = new View(primaryStage);
        view.initializeStage(newGame());

    }


    public RowOfColumns newGame(){
        System.out.println("new game called");
        RowOfColumns rowOfColumns = boardFactory.createCleanRowOfColumns();
        return rowOfColumns;
    }
}
