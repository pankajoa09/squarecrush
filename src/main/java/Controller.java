import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class Controller {

    private final Stage primaryStage;
    //View view = new View(primaryStage);
    Engine engine = new Engine();
    Debug debug= new Debug();

    public Controller(Stage primaryStage) {
        System.out.println("controller initiated");
        this.primaryStage = primaryStage;
        View view = new View(primaryStage);
        view.initializeStage(newGame());

    }




    public RowOfColumns newGame(){
        System.out.println("new game called");
        RowOfColumns rowOfColumns = engine.createRowOfColumns();
        //debug.showBlockOnGrid(rowOfColumns.getColumnOfBlocks(1).getContainingBlocks(), rowOfColumns);
        return rowOfColumns;
    }
}
