import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;



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
    private final static int HEIGHT = 10;
    private final static int TRUE_HEIGHT = 5;
    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private Timeline timeline;


    public View(Stage primaryStage){
        this.primaryStage = primaryStage;

    }


    Debug debug = new Debug();







    public void initializeStage(RowOfColumns rowOfColumns){

        primaryStage.setHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMaxHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        primaryStage.setMinHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        primaryStage.setWidth(WIDTH*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMaxWidth(WIDTH*BLOCK_RECTANGLE_SIZE);
        primaryStage.setMinWidth(WIDTH*BLOCK_RECTANGLE_SIZE);

        GridPane rowOfColumnsPane = refreshRowOfColumnsPane(rowOfColumns);
        mainGridPane.add(rowOfColumnsPane,2,2);
        mainGridPane.setTranslateY(-200);
        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(mainGridPane);
        Scene scene = new Scene(stackPane, WIDTH*BLOCK_RECTANGLE_SIZE, TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE+10);
        primaryStage.setTitle("CRUSHMEH");
        primaryStage.setScene(scene);
        primaryStage.show();
    }






// A RowOfColumns contains ColumnOfBlocks' contains Blocks
// A RowOfColumns is displayed as a GridPane called rowOfColumnsPane
// A ColumnOfBlocks is displayed as a GridPane called columnOfBlocks
// A Block is displayed as a Rectangle called BlockRectangle

    public void refreshMainGrid(Animated animated){
        mainGridPane.getChildren().clear();
        mainGridPane.getChildren().removeAll();
        mainGridPane.add(refreshRowOfColumnsPane(animated.getRowOfColumns()),2,2);
        mainGridPane.setTranslateY(-200);
    }

    public GridPane refreshRowOfColumnsPane(RowOfColumns rowOfColumns){
        GridPane rowOfColumnsPane = new GridPane();


        for (int i = 0; i < rowOfColumns.getContainingColumns().size();i++){
            ColumnOfBlocks columnOfBlocks = rowOfColumns.getColumnOfBlocks(i);
            GridPane columnOfBlocksPane = refreshColumnOfBlocksPane(columnOfBlocks,rowOfColumns);
            GridPane.setColumnIndex(columnOfBlocksPane,i);
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);
        }
        return rowOfColumnsPane;
    }

    private GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks, RowOfColumns rowOfColumns){
        GridPane columnOfBlocksPane = new GridPane();
        int index = 0;
        System.out.println("");

        for (int i = 0; i < columnOfBlocks.getContainingBlocks().size();i++){
            Block block = columnOfBlocks.getBlock(i);
            StackPane blockRectangle = refreshBlockRectangle(block,rowOfColumns);
            GridPane.setRowIndex(blockRectangle,i);
            columnOfBlocksPane.getChildren().addAll(blockRectangle);
        }


        return columnOfBlocksPane;
    }


    private StackPane refreshBlockRectangle(final Block block, final RowOfColumns rowOfColumns){

        final int columnNumber = block.getColumnNumber();
        final int positionInColumn = block.getPositionInColumn();
        final Rectangle blockRectangle = new Rectangle();
        blockRectangle.setHeight(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setWidth(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));
        //blockRectangle.setFill(new Color(1,1,1,1));
        StackPane rectanglePane = new StackPane();
        //Label text = new Label((block.getPositionInColumn()-5)+" "+block.getColumnNumber());
        Label text = new Label("");
        Font font = new Font("Impact",30);
        text.fontProperty().set(font);
        rectanglePane.getChildren().addAll(blockRectangle,text);
        final GridPane finalMainGridPane = this.mainGridPane;
        final Stage finalPrimaryStage = this.primaryStage;
        rectanglePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Event event = new Event();
                System.out.println("clickk");
                Animated animated = event.rectangleBlockClickHandler(columnNumber,positionInColumn,rowOfColumns);
//                Animated animated = event.rectangleBlockClickHandler(columnNumber,positionInColumn,rowOfColumns);
//                animator(animated);

            }
        });
        return rectanglePane;
    }

    ///all animations below. I seriously cant split this class no matter how much I try.

    private void fallDown(StackPane rectangle, int howFar, int ind, int smth, Animated animated){
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
        final int Ind = ind;
        final int Smth = smth;
        final Animated finalAnimated = animated;
        timeline.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (Ind==Smth) {
                    refreshMainGrid(finalAnimated);

                    //stackPane.getChildren().clear();
                    //stackPane.getChildren().removeAll();
                    //stackPane.getChildren().addAll(mainGridPane);
                }

            }
        });
        timeline.play();
        //timer.start();
    }

    private void move(StackPane rectangle){

    }





    private void fade(StackPane rectangle){
        System.out.println("does it work");

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
        //this is some weird shit I am just trying to get it to react on the last animation.
        int ind = 0;
        for (Block fall : fallDowns){
            int howMuch = howManyFadedBelow(fadeOuts,fall);
            int pos = fall.getPositionInColumn();
            int col = fall.getColumnNumber();
            fallDown(getRectangle(pos,col),howMuch,ind,fallDowns.size()-1,animated);
            ind++;
        }


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




    public StackPane getRectangle(int positionInColumn,int column){
        StackPane returnedRectangle = new StackPane();

        GridPane rowOfColumnsPane = new GridPane();
        try {
            rowOfColumnsPane = (GridPane)this.mainGridPane.getChildren().get(0);
        }
        catch (IndexOutOfBoundsException iobe){
            System.out.println("LOOOK HERE");
            System.out.println(this.mainGridPane.getChildren());
        }

        Node gridPane = rowOfColumnsPane.getChildren().get(column);
        if (gridPane instanceof GridPane){
            //System.out.println(((GridPane)nodeOut).getChildren());
            Node rectangle = ((GridPane)gridPane).getChildren().get(positionInColumn);
            if (rectangle instanceof StackPane) {
                returnedRectangle = (StackPane)rectangle;
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
