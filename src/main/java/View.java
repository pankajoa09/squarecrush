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


/**
 * Created by pjoa09 on 9/26/17.
 */
public class View {

    private final Stage primaryStage;
    private final StackPane stackPane = new StackPane();
    private final GridPane mainGridPane = new GridPane();

    private final static int WIDTH = 5;
    private final static int HEIGHT = 5;
    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private Timeline timeline;


    public View(Stage primaryStage){
        this.primaryStage = primaryStage;

    }

    Debug debug = new Debug();

    public void initializeStage(RowOfColumns rowOfColumns){
        Animated animated = new Animated();
        animated.setRowOfColumns(rowOfColumns);
        GridPane rowOfColumnsPane = refreshRowOfColumnsPane(rowOfColumns,animated);

        mainGridPane.add(rowOfColumnsPane,2,2);
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

    public void refreshMainGrid(Animated animated){
        mainGridPane.getChildren().clear();
        mainGridPane.getChildren().removeAll();
        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        mainGridPane.add(refreshRowOfColumnsPane(rowOfColumns,animated),2,2);
        Event event = new Event();
        animated = event.generalHandler(animated);
        animateFadeOutBlocks(animated);
    }

    public GridPane refreshRowOfColumnsPane(RowOfColumns rowOfColumns, Animated animated){
        GridPane rowOfColumnsPane = new GridPane();

        for (int i = 0; i < rowOfColumns.getContainingColumns().size();i++){
            ColumnOfBlocks columnOfBlocks = rowOfColumns.getColumnOfBlocks(i);
            GridPane columnOfBlocksPane = refreshColumnOfBlocksPane(columnOfBlocks, animated);
            rowOfColumnsPane.setColumnIndex(columnOfBlocksPane,i);
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);
        }
        return rowOfColumnsPane;
    }

    private GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks, Animated animated){
        GridPane columnOfBlocksPane = new GridPane();

        for (int i = 0; i < columnOfBlocks.getContainingBlocks().size();i++){
            Block block = columnOfBlocks.getBlock(i);
            StackPane blockRectangle = refreshBlockRectangle(block,animated);
            columnOfBlocksPane.setRowIndex(blockRectangle,i);
            columnOfBlocksPane.getChildren().addAll(blockRectangle);
        }


        return columnOfBlocksPane;
    }


    private StackPane refreshBlockRectangle(final Block block, Animated animated){


        final int columnNumber = block.getColumnNumber();
        final int positionInColumn = block.getPositionInColumn();
        final Rectangle blockRectangle = new Rectangle();
        blockRectangle.setHeight(BLOCK_RECTANGLE_SIZE);
        blockRectangle.setWidth(BLOCK_RECTANGLE_SIZE);

        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));

        StackPane rectanglePane = new StackPane();

        Label text = new Label("");
        Font font = new Font("Impact",30);
        text.fontProperty().set(font);
        rectanglePane.getChildren().addAll(blockRectangle,text);

        final Animated finalAnimated = animated;
        rectanglePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Event event = new Event();
                Animated animated = event.swapHandler(columnNumber,positionInColumn,finalAnimated);
                if (animated.getClicks().getSecondClick() != null) {
                    System.out.println("second click");
                    animateSwapBlocks(animated);
                }
            }
        });
        return rectanglePane;
    }

    ///all animations below. I seriously cant split this class no matter how much I try.

    private void animateSwapBlocks(Animated animated){


        Block block1 = animated.getClicks().getFirstClick();
        Block block2 = animated.getClicks().getSecondClick();
        StackPane click1 = getRectangleFromBlock(block1);
        StackPane click2 = getRectangleFromBlock(block2);


        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        Duration duration = Duration.millis(400);

        if (animated.getClicks().areClicksNextToEachOther()){

            KeyValue keyValueX1 = new KeyValue(click1.translateXProperty(), (block2.getColumnNumber()-block1.getColumnNumber())*BLOCK_RECTANGLE_SIZE);
            KeyValue keyValueY1 = new KeyValue(click1.translateYProperty(), 0);
            KeyFrame keyFrame1 = new KeyFrame(duration , keyValueX1, keyValueY1);
            //add the keyframe to the timeline
            timeline.getKeyFrames().add(keyFrame1);

            KeyValue keyValueX2 = new KeyValue(click2.translateXProperty(), (block1.getColumnNumber()-block2.getColumnNumber())*BLOCK_RECTANGLE_SIZE);
            KeyValue keyValueY2 = new KeyValue(click2.translateYProperty(), 0);
            KeyFrame keyFrame2 = new KeyFrame(duration , keyValueX2, keyValueY2);
            //add the keyframe to the timeline
            timeline.getKeyFrames().add(keyFrame2);
        }
        else if (animated.getClicks().areClicksOnTopOfOneAnother()){
            KeyValue keyValueX1 = new KeyValue(click1.translateXProperty(), 0);
            KeyValue keyValueY1 = new KeyValue(click1.translateYProperty(), (block2.getPositionInColumn()-block1.getPositionInColumn())*BLOCK_RECTANGLE_SIZE);
            KeyFrame keyFrame1 = new KeyFrame(duration , keyValueX1, keyValueY1);
            //add the keyframe to the timeline
            timeline.getKeyFrames().add(keyFrame1);

            KeyValue keyValueX2 = new KeyValue(click2.translateXProperty(), 0);
            KeyValue keyValueY2 = new KeyValue(click2.translateYProperty(), (block1.getPositionInColumn()-block2.getPositionInColumn())*BLOCK_RECTANGLE_SIZE);
            KeyFrame keyFrame2 = new KeyFrame(duration , keyValueX2, keyValueY2);
            //add the keyframe to the timeline
            timeline.getKeyFrames().add(keyFrame2);

        }
        else{
            System.out.println("Somethings wrong with the click animation");
        }

        final Animated finalAnimated = animated;
        timeline.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                refreshMainGrid(finalAnimated);
            }
        });
        timeline.play();
    }

    private void fallDown(StackPane rectangle, int howFar, int ind, Animated animated){
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        KeyValue keyValueX = new KeyValue(rectangle.translateXProperty(), 0);
        KeyValue keyValueY = new KeyValue(rectangle.translateYProperty(), BLOCK_RECTANGLE_SIZE*howFar);
        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(750);
        //one can add a specific action when the keyframe is reached
        KeyFrame keyFrame = new KeyFrame(duration , keyValueX, keyValueY);
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        final Animated finalAnimated = animated;
        final int Ind = ind;
        timeline.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                if (Ind==1) {
                    //animateFadeInBlocks(finalAnimated);
                    refreshMainGrid(finalAnimated);
                }

            }
        });
        timeline.play();
    }

    private void animateFallDownBlocks(Animated animated){
        int ind = animated.getToFall().size();
        if (animated.getToFall().size()==1){
            System.out.println("my nibbb");
            debug.printBlock(animated.getToFall().get(0));
        }
        if (!animated.getToFall().isEmpty()) {
            for (Block fall : animated.getToFall()) {
                fallDown(getRectangleFromBlock(fall), fall.getShiftDown(), ind, animated);
                ind--;
            }
        }
    }

    private void fadeOut(StackPane rectangle,int ind, Animated animated){
        FadeTransition ft = new FadeTransition(Duration.millis(500), rectangle);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        final Animated finalAnimated = animated;
        final int Ind = ind;
        ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (Ind==1) {
                    animateFallDownBlocks(finalAnimated);
                }
            }
        });
        ft.play();
    }

    private void animateFadeOutBlocks(Animated animated){
        int ind = animated.getToDestroy().size();
        for (Block destroy: animated.getToDestroy()){
            fadeOut(getRectangleFromBlock(destroy),ind,animated);
            ind--;
        }

    }


    public StackPane getRectangleFromBlock(Block block){

        return getRectangle(block.getPositionInColumn(),block.getColumnNumber());
    }

    public StackPane getRectangle(int positionInColumn,int column){
        StackPane returnedRectangle = new StackPane();

        GridPane rowOfColumnsPane = new GridPane();
        try {
            rowOfColumnsPane = (GridPane)this.mainGridPane.getChildren().get(0);
        }
        catch (IndexOutOfBoundsException iobe){

            System.out.println(this.mainGridPane.getChildren());
        }

        Node gridPane = rowOfColumnsPane.getChildren().get(column);
        if (gridPane instanceof GridPane){
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
