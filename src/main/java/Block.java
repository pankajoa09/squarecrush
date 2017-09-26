/**
 * Created by pjoa09 on 9/26/17.
 */

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Block {




    private int positionNumberInColumn; //or row
    private int columnNumber;
    private BlockImage blockImage;

    public int getPositionNumberInColumn() {
        return positionNumberInColumn;
    }

    public void setPositionNumberInColumn(int positionNumberInColumn) {
        this.positionNumberInColumn = positionNumberInColumn;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public BlockImage getBlockImage() {
        return blockImage;
    }

    public void setBlockImage(BlockImage blockImage) {
        this.blockImage = blockImage;
    }


    public void createBlock(int columnNumber, int positionNumberInColumn, BlockImage blockImage) {
        this.columnNumber = columnNumber;
        this.positionNumberInColumn = positionNumberInColumn;
        this.blockImage = blockImage;
    }

    public void createRandomBlock(int columnNumber,int positionNumberInColumn){
        this.columnNumber = columnNumber;
        this.positionNumberInColumn = positionNumberInColumn;
        BlockImage blockImage = new BlockImage();
        blockImage.createRandom();
        this.blockImage = blockImage;
    }








}
