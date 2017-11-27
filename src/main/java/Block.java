/**
 * Created by pjoa09 on 9/26/17.
 */

public class Block {


    private int positionInColumn; // j
    private int columnNumber; // i
    private BlockImage blockImage;
    private int shiftDown;

    public int getShiftDown(){
        return shiftDown;
    }

    public void incrementShiftDown(int howMuch){
        this.shiftDown = this.shiftDown+howMuch;
    }


    public int getPositionInColumn() {
        return positionInColumn;
    }

    public int getColumnNumber() {
        return columnNumber;
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
}
