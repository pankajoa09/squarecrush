

/**
 * Created by pjoa09 on 10/18/17.
 */
public class BoardFactory {

    private final static int MAX_OBJECTS = 3;
    private final static int WIDTH = 5;
    private final static int TRUE_HEIGHT = 10;
    private final static int HEIGHT = 5;
    private final static int BLOCK_RECTANGLE_SIZE = 80;
    private final static int BLOCK_IMAGE_POOL_SIZE = 8;


    public RowOfColumns createRowOfColumns(){
        RowOfColumns rowOfColumns = new RowOfColumns();
        //populate rowofcolumns with columns
        for (int i=0; i<WIDTH;i++){
            ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
            columnOfBlocks.setPositionInRowOfColumns(i);
            rowOfColumns.addColumnOfBlocks(columnOfBlocks);
        }

        for (ColumnOfBlocks col : rowOfColumns.getContainingColumns()){
            for (int i =0; i<TRUE_HEIGHT;i++){
                Block block = new Block();
                block.createRandomBlock(i,col.getPositionInRowOfColumns(),BLOCK_IMAGE_POOL_SIZE);
                if (i >= HEIGHT){
                    block.setActiveTrue();
                }
                col.addBlock(block);
            }
        }
        //debug.printRowOfColumns(rowOfColumns);
        //debug.printArrayInRowOfColumns(rowOfColumns.getColumnOfBlocks(1).getContainingBlocks(),rowOfColumns);
        return rowOfColumns;
    }

    public RowOfColumns createRowOfColumnsClone(RowOfColumns rowOfColumns){

        RowOfColumns rowOfColumnsClone = new RowOfColumns();

        for (int i=0; i<WIDTH;i++){
            ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
            columnOfBlocks.setPositionInRowOfColumns(i);
            rowOfColumnsClone.addColumnOfBlocks(columnOfBlocks);
        }

        for (ColumnOfBlocks col : rowOfColumns.getContainingColumns()){
            for (int i =0; i<TRUE_HEIGHT;i++){
                Block block = new Block();
                BlockImage oldBlockImage = rowOfColumns.getBlock(i,col.getPositionInRowOfColumns()).getBlockImage();
                BlockImage blockImage = new BlockImage();
                blockImage.setImage(oldBlockImage.getImage());
                blockImage.setName(oldBlockImage.getName());
                block.createBlock(i,col.getPositionInRowOfColumns(),blockImage);
                if (i >= HEIGHT){
                    block.setActiveTrue();
                }
                col.addBlock(block);
            }
        }

        return rowOfColumns;
    }
}
