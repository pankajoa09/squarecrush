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
        Controller controller = new Controller(primaryStage);

    }



}