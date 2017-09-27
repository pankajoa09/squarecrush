import javafx.stage.Stage;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class Controller {

    View view = new View();

    public void startApplication(Stage primaryStage){
        view.initializeStage(primaryStage);
    }
}
