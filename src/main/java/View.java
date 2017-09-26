import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.rowset.internal.Row;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;



/**
 * Created by pjoa09 on 9/26/17.
 */
public class View {

    //private static GridPane columnOfBlocksPane = new GridPane();
    //private static GridPane rowOfColumnsPane = new GridPane();

    private final static int MAX_OBJECTS = 5;

    private final static int WIDTH = 10;
    private final static int HEIGHT = 10;

    public void initializeStage(){

        
    }


// this is a board being created gonna move it to the Controller soon.
    public RowOfColumns createRowOfColumns(){
        RowOfColumns rowOfColumns = new RowOfColumns();
        for (int i=0; i<WIDTH; i++){
            ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
            for (int j=0; j<HEIGHT; j++){
                Block block = new Block();
                block.createRandomBlock(i,j);
                columnOfBlocks.addBlock(block);
            }
            rowOfColumns.addColumnOfBlocks(columnOfBlocks);
        }
        return rowOfColumns;
    }


// A RowOfColumns contains ColumnOfBlocks' contains Blocks
// A RowOfColumns is displayed as a GridPane called rowOfColumnsPane
// A ColumnOfBlocks is displayed as a GridPane called columnOfBlocks
// A Block is displayed as a Rectangle called BlockRectangle

    public GridPane refreshRowOfColumnsPane(RowOfColumns rowOfColumns){
        GridPane rowOfColumnsPane = new GridPane();
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
            GridPane columnOfBlocksPane = refreshColumnOfBlocksPane(columnOfBlocks);
            rowOfColumnsPane.setRowIndex(columnOfBlocksPane,rowOfColumns.getContainingColumns().size());
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);

        }
        return rowOfColumnsPane;
    }

    public GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks){
        GridPane columnOfBlocksPane = new GridPane();
        for (Block block: columnOfBlocks.getContainingBlocks()){
            Rectangle blockRectangle = refreshBlockRectangle(block);
            columnOfBlocksPane.setColumnIndex(blockRectangle,columnOfBlocks.getContainingBlocks().size());
            columnOfBlocksPane.getChildren().addAll(blockRectangle);
        }
        return columnOfBlocksPane;
    }


    public Rectangle refreshBlockRectangle(Block block){
        Rectangle blockRectangle = new Rectangle();
        blockRectangle.setHeight(80);
        blockRectangle.setWidth(80);
        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));
        return blockRectangle;
    }







}
