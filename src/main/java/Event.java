import com.sun.rowset.internal.Row;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Event {

    Debug debug = new Debug();
    Engine engine = new Engine();
    Service service = new Service();

    private static Block firstClick;


    public void rectangleBlockClickHandler(int columnNumber, int positionInColumn, RowOfColumns rowOfColumns){
        Block clicked = rowOfColumns.getBlock(columnNumber,positionInColumn);
        if (isSecondClick(clicked)){
            Block firstClick = getFirstClick();
            Block secondClick = clicked;
            Animated animated = service.updateAnimated(firstClick,secondClick);
        }
        else{
           storeFirstClick(clicked);
        }
    }

    private Boolean isSecondClick(Block block){
        Boolean answer = false;
        if (!firstClick.equals(null)){
            answer = true;
        }
        return answer;
    }

    private void storeFirstClick(Block clicked){
        firstClick = clicked;
    }

    private Block getFirstClick(){
        return firstClick;
    }






}
