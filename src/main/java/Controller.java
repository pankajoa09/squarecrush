import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class Controller {

    private final Stage primaryStage;
    //View view = new View(primaryStage);

    BoardFactory boardFactory = new BoardFactory();
    Debug debug= new Debug();

    public Controller(Stage primaryStage,int board, int pool) {
        primaryStage.close();
        System.out.println("controller initiated");
        this.primaryStage = primaryStage;
        View view = new View(primaryStage);
        view.initializeStage(newGame(board,pool));

    }


    public RowOfColumns newGame(int board, int pool){
        System.out.println("new game called");

        RowOfColumns rowOfColumns = boardFactory.createCleanRowOfColumns(board,pool);
        //debug.showBlockOnGrid(rowOfColumns.getColumnOfBlocks(1).getContainingBlocks(), rowOfColumns);
        return rowOfColumns;
    }
}
