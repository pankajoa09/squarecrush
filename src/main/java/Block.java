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


    private int positionInColumn; // j
    private int columnNumber; // i
    private BlockImage blockImage;


    public int getPositionInColumn() {
        return positionInColumn;
    }

    public void setPositionInColumn(int positionInColumn) {
        this.positionInColumn = positionInColumn;
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


    public void createBlock(int positionInColumn, int columnNumber, BlockImage blockImage) {
        this.columnNumber = columnNumber;
        this.positionInColumn = positionInColumn;
        this.blockImage = blockImage;
    }

    public void createRandomBlock(int positionInColumn,int columnNumber,int blockImagePoolSize){
        this.columnNumber = columnNumber;
        this.positionInColumn = positionInColumn;
        BlockImage blockImage = new BlockImage();
        blockImage.createRandom(blockImagePoolSize);
        this.blockImage = blockImage;
    }

    public void createNullBlock(int positionInColumn,int columnNumber){
        this.columnNumber = columnNumber;
        this.positionInColumn = positionInColumn;
        BlockImage nullBlockImage = new BlockImage();
        nullBlockImage.createNull();
        this.blockImage = nullBlockImage;
    }











}
