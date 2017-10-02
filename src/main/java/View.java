import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.rowset.internal.Row;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
    private static StackPane stackPane = new StackPane();
    private static GridPane mainGridPane = new GridPane();
    private final static int MAX_OBJECTS = 3;
    private final static int WIDTH = 5;
    private final static int HEIGHT = 5;
    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private final static int BLOCK_IMAGE_POOL_SIZE = 2;

    Debug debug = new Debug();

    public void initializeStage(Stage primaryStage){


        GridPane rowOfColumnsPane = refreshRowOfColumnsPane(createRowOfColumns());
        mainGridPane.add(rowOfColumnsPane,1,1);
        stackPane.getChildren().addAll(mainGridPane);
        Scene scene = new Scene(stackPane, WIDTH*BLOCK_RECTANGLE_SIZE, HEIGHT*BLOCK_RECTANGLE_SIZE);
        primaryStage.setTitle("CRUSHMEH");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


// this is a board being created gonna move it to the Controller soon.
    public RowOfColumns createRowOfColumns(){
        RowOfColumns rowOfColumns = new RowOfColumns();
        for (int i=0; i<WIDTH; i++){
            ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
            for (int j=0; j<HEIGHT; j++){
                Block block = new Block();
                block.createRandomBlock(i,j,BLOCK_IMAGE_POOL_SIZE);
                columnOfBlocks.addBlock(block);
            }
            rowOfColumns.addColumnOfBlocks(columnOfBlocks);
        }
        debug.printRowOfColumns(rowOfColumns);
        return rowOfColumns;
    }


// A RowOfColumns contains ColumnOfBlocks' contains Blocks
// A RowOfColumns is displayed as a GridPane called rowOfColumnsPane
// A ColumnOfBlocks is displayed as a GridPane called columnOfBlocks
// A Block is displayed as a Rectangle called BlockRectangle

    public GridPane refreshRowOfColumnsPane(RowOfColumns rowOfColumns){
        GridPane rowOfColumnsPane = new GridPane();
        int index=0;
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
            GridPane columnOfBlocksPane = refreshColumnOfBlocksPane(columnOfBlocks, rowOfColumns);
            rowOfColumnsPane.setRowIndex(columnOfBlocksPane,index);
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);
            index++;

        }
        return rowOfColumnsPane;
    }

    public GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks, RowOfColumns rowOfColumns){
        GridPane columnOfBlocksPane = new GridPane();
        int index = 0;
        for (Block block: columnOfBlocks.getContainingBlocks()){
            Rectangle blockRectangle = refreshBlockRectangle(block,rowOfColumns);
            columnOfBlocksPane.setColumnIndex(blockRectangle,index);
            columnOfBlocksPane.getChildren().addAll(blockRectangle);
            index++;
        }
        return columnOfBlocksPane;
    }


    public Rectangle refreshBlockRectangle(Block block, final RowOfColumns rowOfColumns){
        final int columnNumber = block.getColumnNumber();
        final int positionInColumn = block.getPositionInColumn();
        Rectangle blockRectangle = new Rectangle();
        blockRectangle.setHeight(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setWidth(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));
        blockRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Controller controller = new Controller();
                controller.rectangleBlockClickHandler(columnNumber,positionInColumn,rowOfColumns);

            }
        });
        return blockRectangle;
    }







}
