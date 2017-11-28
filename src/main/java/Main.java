import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by pjoa09 on 9/26/17.
 */

public class Main extends Application {


    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int board = 5;
        int size = 3;
        Controller controller = new Controller(primaryStage,board,size);
        //Testing testing = new Testing();



        //testing.testThrees();
        //testing.testGetBlocksToDestroy();
        //testing.testDestroyBlocks();
        //testing.testGetBlock();
        //testing.testClone();
        //testing.testGetToFallNew();
        //testing.testFall();
        //testing.testWhatTheFuck();
        //testing.testGameOver();



    }



}