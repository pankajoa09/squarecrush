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

    Service service = new Service();
    private final static Clicks clicks = new Clicks();



    public Animated swapHandler(int columnNumber, int positionInColumn, Animated oldAnimated){
        RowOfColumns rowOfColumns = oldAnimated.getRowOfColumns();
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

            Animated animated = new Animated();
            animated.setScore(oldAnimated.getScore());
            Block firstClick = clicks.getFirstClick();
            Block secondClick = clicks.getSecondClick();
            Clicks click = new Clicks();
            click.setFirstClick(firstClick);
            click.setSecondClick(secondClick);
            animated.setClicks(click);
            animated.setRowOfColumns(rowOfColumns);
            animated = service.swap(animated);

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

    public Animated generalHandler(Animated oldAnimated){
        Animated animated = new Animated();
        animated.setScore(oldAnimated.getScore());
        animated.setRowOfColumns(oldAnimated.getRowOfColumns());
        return service.update(animated);
    }

    public void newGame(Stage primaryStage, int boardSize, int poolSize){
        Controller controller = new Controller(primaryStage,boardSize,poolSize);
        controller.newGame(boardSize,poolSize);

    }








}
