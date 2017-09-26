import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * Created by pjoa09 on 9/26/17.
 */
public class View {

    public void initializeStage(){

        gridPane = refreshGrid(grid);
        borderPane = refreshBorder(stats);
        mainGridPane.add(gridPane,1,1);
        mainGridPane.add(borderPane,1,2);
        stackPane.getChildren().addAll(mainGridPane);
        Scene scene = new Scene(stackPane, 640, 700);
        primaryStage.setTitle("Reversi");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void refreshGrid(){
        ArrayList<Integer> column = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>>  grid = new ArrayList<ArrayList<Integer>>();
        for 
    }





}
