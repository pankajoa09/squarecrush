import com.sun.rowset.internal.Row;

import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Event {

    Debug debug = new Debug();
    Engine engine = new Engine();


    public Animated rectangleBlockClickHandler(int columnNumber, int positionInColumn, RowOfColumns rowOfColumns){
        System.out.println("clicked: "+columnNumber+" "+positionInColumn);
        Block clickedBlock = rowOfColumns.getBlock(columnNumber,positionInColumn);
        Animated animated = engine.processClicked(clickedBlock,rowOfColumns);
        return animated;
    }






}
