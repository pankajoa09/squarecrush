import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.rowset.internal.Row;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;



/**
 * Created by pjoa09 on 9/26/17.
 */
public class View {

    //private static GridPane columnOfBlocksPane = new GridPane();
    //private static GridPane rowOfColumnsPane = new GridPane();
    private final Stage primaryStage;

    private final StackPane stackPane = new StackPane();
    private final GridPane mainGridPane = new GridPane();
    private final static int MAX_OBJECTS = 3;
    private final static int WIDTH = 5;
    private final static int HEIGHT = 5;
    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private final static int BLOCK_IMAGE_POOL_SIZE = 2;
    private Timeline timeline;
    private AnimationTimer timer;



    Debug debug = new Debug();



    public View(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initializeStage(RowOfColumns rowOfColumns){

        GridPane rowOfColumnsPane = refreshRowOfColumnsPane(rowOfColumns);
        mainGridPane.add(rowOfColumnsPane,1,1);
        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(mainGridPane);
        Scene scene = new Scene(stackPane, WIDTH*BLOCK_RECTANGLE_SIZE, HEIGHT*BLOCK_RECTANGLE_SIZE);
        primaryStage.setTitle("CRUSHMEH");
        primaryStage.setScene(scene);
        primaryStage.show();
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
            rowOfColumnsPane.setColumnIndex(columnOfBlocksPane,index);
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);
            index++;

        }
        return rowOfColumnsPane;
    }

    private GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks, RowOfColumns rowOfColumns){
        GridPane columnOfBlocksPane = new GridPane();
        int index = 0;
        for (Block block: columnOfBlocks.getContainingBlocks()){
            Rectangle blockRectangle = refreshBlockRectangle(block,rowOfColumns);
            columnOfBlocksPane.setRowIndex(blockRectangle,index);
            columnOfBlocksPane.getChildren().addAll(blockRectangle);
            index++;
        }

        return columnOfBlocksPane;
    }


    private Rectangle refreshBlockRectangle(final Block block, final RowOfColumns rowOfColumns){
        final int columnNumber = block.getColumnNumber();
        final int positionInColumn = block.getPositionInColumn();
        final Rectangle blockRectangle = new Rectangle();

        blockRectangle.setHeight(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setWidth(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));
        final Stage stage = this.primaryStage;
        blockRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Event event = new Event();
                Animated animated = event.rectangleBlockClickHandler(columnNumber,positionInColumn,rowOfColumns);
                animator(animated);

                //

            }
        });
        return blockRectangle;
    }

    private void fallDown(Rectangle rectangle,int howFar){
        //rectangle.setEffect(new Lighting());
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        KeyValue keyValueX = new KeyValue(rectangle.translateXProperty(), 0);
        KeyValue keyValueY = new KeyValue(rectangle.translateYProperty(), BLOCK_RECTANGLE_SIZE*howFar);
        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(2000);
        //one can add a specific action when the keyframe is reached
        KeyFrame keyFrame = new KeyFrame(duration , keyValueX, keyValueY);
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        //timer.start();
    }

    private void fade(Rectangle rectangle){
        FadeTransition ft = new FadeTransition(Duration.millis(500), rectangle);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
    }


    public void animator(Animated animated){
        System.out.println("ANIMATOR CALLED");
        ArrayList<Block> fadeOuts = animated.getToFade();
        ArrayList<Block> fallDowns = animated.getToMoveDown();
        ArrayList<Block> toPlaceOnTops = animated.getToPlaceOnTop();
        fallDowns.addAll(toPlaceOnTops);

        for (Block fade: fadeOuts){

            int pos = fade.getPositionInColumn();
            int col = fade.getColumnNumber();
            fade(getRectangle(pos,col));
        }
        
        for (Block fall : fallDowns){
            int howMuch = howManyFadedBelow(fadeOuts,fall);
            int pos = fall.getPositionInColumn();
            int col = fall.getColumnNumber();
            fallDown(getRectangle(pos,col),howMuch);
        }
        /*
        for (Block top: toPlaceOnTops){
            int howMuch = howManyFadedBelow(fadeOuts,top);
            int pos = top.getPositionInColumn();
            int col = top.getColumnNumber();
            fallDown(refreshBlockRectangle(top,animated.getRowOfColumns()),howMuch);
        }
        */

        //mainGridPane.getChildren().clear();
        //mainGridPane.add(refreshRowOfColumnsPane(animated.getRowOfColumns()),1,1);


    }

    public int howManyFadedBelow(ArrayList<Block> fadeOuts, Block targetBlock){
        int count = 0;
        for (Block block: fadeOuts){
            if (block.getColumnNumber()==targetBlock.getColumnNumber()){
                if(block.getPositionInColumn()>targetBlock.getPositionInColumn()){
                    count++;
                }
            }
        }

        //System.out.println("how many faded: "+count);
        return count;
    }




    public Rectangle getRectangle(int positionInColumn,int column){
        Rectangle returnedRectangle = new Rectangle();
        GridPane rowOfColumnsPane = (GridPane)this.mainGridPane.getChildren().get(0);
        Node gridPane = rowOfColumnsPane.getChildren().get(column);
        if (gridPane instanceof GridPane){
            //System.out.println(((GridPane)nodeOut).getChildren());
            Node rectangle = ((GridPane)gridPane).getChildren().get(positionInColumn);
            if (rectangle instanceof Rectangle) {
                returnedRectangle = (Rectangle) rectangle;
            }
            else{
                System.out.println("GO HOME");
            }
        }
        else{
            System.out.println("WHHHDFSHDFJ");
        }
        return returnedRectangle;

    }









}
