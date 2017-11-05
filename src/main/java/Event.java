import com.sun.rowset.internal.Row;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Event {

    Debug debug = new Debug();
    Engine engine = new Engine();
    Service service = new Service();
    private static Clicks clicks = new Clicks();



    public Animated rectangleBlockClickHandler(int columnNumber, int positionInColumn, RowOfColumns rowOfColumns){
        Animated ans = new Animated();
        Block clicked = rowOfColumns.getBlock(columnNumber,positionInColumn);
        if (isSecondClick(clicked)){

            System.out.print("PRESWAP ");
            debug.printBlock(clicked);
            ArrayList<Block> clicklist = new ArrayList<Block>();
            clicks.setSecondClick(clicked);
            clicklist.add(clicks.getFirstClick());
            clicklist.add(clicks.getSecondClick());
            debug.printArrayInRowOfColumns(clicklist,rowOfColumns);

            Block firstClick = clicks.getFirstClick();
            Block secondClick = clicks.getSecondClick();
            Animated animated = service.updateAnimated(firstClick,secondClick,rowOfColumns);
            clicks.setFirstClick(null);
            clicks.setSecondClick(null);
            ans = animated;



        }
        else{
            System.out.println("is first click");
            clicks.setFirstClick(clicked);
        }
        return ans;
    }

    private boolean isSecondClick(Block block){
        boolean answer = false;
        Block firstClick = clicks.getFirstClick();
        if (firstClick != null){
            answer = true;
        }
        return answer;
    }







}
