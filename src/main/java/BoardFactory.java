

/**
 * Created by pjoa09 on 10/18/17.
 */
public class BoardFactory implements Cloneable {






    Debug debug = new Debug();

    public RowOfColumns createRowOfColumns(int boardSize,int poolSize){

        RowOfColumns rowOfColumns = new RowOfColumns();
        rowOfColumns.setPoolSize(poolSize);
        //populate rowofcolumns with columns
        for (int i=0; i<boardSize;i++){
            ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
            columnOfBlocks.setPositionInRowOfColumns(i);
            rowOfColumns.addColumnOfBlocks(columnOfBlocks);
        }

        for (ColumnOfBlocks col : rowOfColumns.getContainingColumns()){
            for (int i =0; i<boardSize;i++){
                Block block = new Block();
                block.createRandomBlock(i,col.getPositionInRowOfColumns(),poolSize);
                //debug.printBlock(block);
                col.addBlock(block);
            }
        }


        return rowOfColumns;
    }

    public RowOfColumns createCleanRowOfColumns(int boardSize,int poolSize){
        System.out.println("creating clean rowOfColumns");
        Service service = new Service();
        RowOfColumns rowOfColumns = createRowOfColumns(boardSize,poolSize);
        while (true) {
            if (service.getBlocksToDestroy(rowOfColumns).isEmpty()) {
                System.out.println("done");
                return rowOfColumns;
            } else {
                System.out.println("...");
                service.destroyAndReplaceBlocks(rowOfColumns);
            }
        }
    }



    public RowOfColumns createRowOfColumnsClone(RowOfColumns rowOfColumns){

        RowOfColumns rowOfColumnsClone = new RowOfColumns();
        rowOfColumnsClone.setPoolSize(rowOfColumns.getPoolSize());
        for (ColumnOfBlocks column : rowOfColumns.getContainingColumns()){
            ColumnOfBlocks cloneColumn = new ColumnOfBlocks();
            cloneColumn.setPositionInRowOfColumns(column.getPositionInRowOfColumns());
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
