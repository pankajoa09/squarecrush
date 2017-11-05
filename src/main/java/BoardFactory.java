

/**
 * Created by pjoa09 on 10/18/17.
 */
public class BoardFactory {

    private final static int MAX_OBJECTS = 3;
    private final static int WIDTH = 5;
    private final static int HEIGHT = 5;
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
            for (int i =0; i<HEIGHT;i++){
                Block block = new Block();
                block.createRandomBlock(i,col.getPositionInRowOfColumns(),BLOCK_IMAGE_POOL_SIZE);
                col.addBlock(block);
            }
        }

        //debug.printArrayInRowOfColumns(rowOfColumns.getColumnOfBlocks(1).getContainingBlocks(),rowOfColumns);
        return rowOfColumns;
    }

    public RowOfColumns createRowOfColumnsClone(RowOfColumns rowOfColumns){

        RowOfColumns rowOfColumnsClone = new RowOfColumns();

        for (ColumnOfBlocks column : rowOfColumns.getContainingColumns()){
            ColumnOfBlocks cloneColumn = new ColumnOfBlocks();
            for (Block block : column.getContainingBlocks()){
                Block cloneBlock = new Block();
                BlockImage cloneBlockImage = new BlockImage();
                cloneBlockImage.create(block.getBlockImage().getName(),block.getBlockImage().getImage());
                cloneBlock.createBlock(block.getPositionInColumn(),block.getColumnNumber(),cloneBlockImage);
                cloneColumn.addBlock(cloneBlock);
            }
            rowOfColumnsClone.addColumnOfBlocks(cloneColumn);
        }

        return rowOfColumnsClone;
    }
}
