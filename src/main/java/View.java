import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.jmx.snmp.SnmpAckPdu;
import com.sun.org.apache.regexp.internal.RE;


import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.java2d.pipe.hw.AccelDeviceEventNotifier;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


/**
 * Created by pjoa09 on 9/26/17.
 */
public class View {

    //private static GridPane columnOfBlocksPane = new GridPane();
    //private static GridPane rowOfColumnsPane = new GridPane();
    private final Stage primaryStage;


    private final StackPane stackPane = new StackPane();
    private final GridPane mainGridPane = new GridPane();

    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private Timeline timeline;


    public View(Stage primaryStage){
        this.primaryStage = primaryStage;

    }


    Debug debug = new Debug();



    public void initializeStage(RowOfColumns rowOfColumns){
        primaryStage.close();
        //primaryStage.setHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMaxHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMinHeight(TRUE_HEIGHT*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setWidth(WIDTH*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMaxWidth(WIDTH*BLOCK_RECTANGLE_SIZE);
        //primaryStage.setMinWidth(WIDTH*BLOCK_RECTANGLE_SIZE);

        Animated animated = new Animated();
        animated.setRowOfColumns(rowOfColumns);
        GridPane rowOfColumnsPane = refreshRowOfColumnsPane(rowOfColumns,animated);


        mainGridPane.getChildren().clear();
        mainGridPane.add(rowOfColumnsPane,2,2);
        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(mainGridPane);
        int width = rowOfColumns.size();
        int height = rowOfColumns.getColumnOfBlocks(0).size();
        Scene scene = new Scene(stackPane, width*BLOCK_RECTANGLE_SIZE+100, height*BLOCK_RECTANGLE_SIZE+30);
        Button btn1 = new Button();
        btn1.setText("New Game");
        stackPane.getChildren().add(btn1);

        btn1.setTranslateX((width*BLOCK_RECTANGLE_SIZE)/2);


        primaryStage.setTitle("SquareCrush");
        primaryStage.setScene(scene);
        primaryStage.show();

        btn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {


                TextField boardField = new TextField();
                TextField difficultyField = new TextField();

                HBox size = new HBox();
                size.getChildren().addAll(boardField);
                size.getChildren().addAll(difficultyField);
                size.setSpacing(10);


                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                Button anotherbtn = new Button();
                anotherbtn.setText("New Game");
                VBox dialogVbox = new VBox(10);
                dialogVbox.getChildren().add(new Text("  Size         Difficulty"));
                dialogVbox.getChildren().add(size);
                dialogVbox.getChildren().add(anotherbtn);
                Scene dialogScene = new Scene(dialogVbox, 150, 100);
                dialog.setScene(dialogScene);
                dialog.show();

                anotherbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        String input = boardField.getText();
                        String input2 = difficultyField.getText();
                        try{
                            int size = Integer.parseInt(input);
                            int diff = Integer.parseInt(input2);
                            Event notevent = new Event();
                            notevent.newGame(primaryStage,size,diff);
                        }
                        catch (NumberFormatException nfe){
                            System.out.println("Give ME A NUMBER!");
                            System.out.println("NumberFormatException: " + nfe.getMessage());
                        }
                    }
                });

                //Event event = new Event();
                //event.newGame(primaryStage);
            }
    });

}


// A RowOfColumns contains ColumnOfBlocks' contains Blocks
// A RowOfColumns is displayed as a GridPane called rowOfColumnsPane
// A ColumnOfBlocks is displayed as a GridPane called columnOfBlocks
// A Block is displayed as a Rectangle called BlockRectangle

    public void refreshMainGrid(Animated animated){
        mainGridPane.getChildren().clear();
        mainGridPane.getChildren().removeAll();
        //System.out.println("THIS IS WHAT REFRESHMAINGRIDANIMATOR GETS:");
        //debug.printRowOfColumns(animated.getRowOfColumns());
        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        mainGridPane.add(refreshRowOfColumnsPane(rowOfColumns,animated),2,2);
        Event event = new Event();
        animated = event.generalHandler(animated);

        StackPane smthelse = new StackPane();
        Label score = new Label();
        //score.setText("LIONS");
        score.setText(Integer.toString(animated.getScore()));
        smthelse.getChildren().addAll(score);
        mainGridPane.add(smthelse,2,4);
        System.out.println(Integer.toString(animated.getScore()));

        if (animated.isGameOver()) {
            StackPane smth = new StackPane();
            Label gameOver = new Label();
            gameOver.setText("GAME OVER");
            smth.getChildren().setAll(gameOver);
            mainGridPane.add(smth,2,3);
            System.out.println(animated.isGameOver());
        }
        animateFadeOutBlocks(animated);

        //mainGridPane.setTranslateY(-200);
    }

    public GridPane refreshRowOfColumnsPane(RowOfColumns rowOfColumns, Animated animated){
        GridPane rowOfColumnsPane = new GridPane();

        for (int i = 0; i < rowOfColumns.size();i++){
            ColumnOfBlocks columnOfBlocks = rowOfColumns.getColumnOfBlocks(i);
            GridPane columnOfBlocksPane = refreshColumnOfBlocksPane(columnOfBlocks, animated);
            rowOfColumnsPane.setColumnIndex(columnOfBlocksPane,i);
            rowOfColumnsPane.getChildren().addAll(columnOfBlocksPane);
        }
        return rowOfColumnsPane;
    }

    private GridPane refreshColumnOfBlocksPane(ColumnOfBlocks columnOfBlocks, Animated animated){
        GridPane columnOfBlocksPane = new GridPane();

        for (int i = 0; i < columnOfBlocks.size();i++){
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
        /*
        if (animated.getToPlaceOnTop().contains(block)){
            blockRectangle.setFill(new Color(1,1,1,1));
            blockRectangle.setStroke(Color.WHITE);

        }
        else{
            blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));

        }
        */
        blockRectangle.setFill(new ImagePattern(block.getBlockImage().getImage()));
        //blockRectangle.setFill(new Color(1,1,1,1));

        StackPane rectanglePane = new StackPane();
        //Label text = new Label((block.getPositionInColumn())+" "+block.getColumnNumber());
        Label text = new Label("");
        Font font = new Font("Impact",30);
        text.fontProperty().set(font);
        rectanglePane.getChildren().addAll(blockRectangle,text);
        final GridPane finalMainGridPane = this.mainGridPane;
        final Stage finalPrimaryStage = this.primaryStage;
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
//                System.out.println("Swap ended");
                refreshMainGrid(finalAnimated);

                //animateFadeBlocks(finalAnimated);

            }
        });

        timeline.play();
        //timer.start();
    }



    private void animateFadeInBlocks(Animated animated){

        for (Block create: animated.getToPlaceOnTop()){
            StackPane rectangle = refreshBlockRectangle(create,animated);
            fadeIn(rectangle,animated);
            //fadeIn(getRectangleFromBlock(create),animated);
        }
    }




    private void fallDown(StackPane rectangle, int howFar, int ind, Animated animated){
        //rectangle.setEffect(new Lighting());
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

                    //System.out.println("Fall Down ended");

                    //animateFadeInBlocks(finalAnimated);
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
                //System.out.println("Fade Out ended");
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

    private void fadeIn(StackPane rectangle, Animated animated){
        FadeTransition ft = new FadeTransition(Duration.millis(500), rectangle);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //System.out.println("Fade In ended");
                refreshMainGrid(animated);
            }
        });
        ft.play();
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
