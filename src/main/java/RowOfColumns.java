import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class RowOfColumns {

    Debug debug = new Debug();

    private static int MAX_HEIGHT = 5;

    private ArrayList<ColumnOfBlocks> containingColumns = new ArrayList<ColumnOfBlocks>();

    public void addColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        this.containingColumns.add(columnOfBlocks);
    }


    public ArrayList<ColumnOfBlocks> getContainingColumns() {
        Collections.sort(this.containingColumns, (ColumnOfBlocks s1, ColumnOfBlocks s2) ->
                (s1.getPositionInRowOfColumns() - s2.getPositionInRowOfColumns()));
        return this.containingColumns;
    }

    public void setContainingColumns(ArrayList<ColumnOfBlocks> containingColumns) {
        this.containingColumns = containingColumns;
    }

    public void addBlock(Block block){
        this.getColumnOfBlocks(block.getColumnNumber()).addBlock(block);
    }

    public void removeBlock(Block block){
        if (blockLocationIsValid(block.getColumnNumber(),block.getPositionInColumn())){
            this.getColumnOfBlocks(block.getColumnNumber()).removeBlock(block);
        }
        else{
            System.out.print("invalid position for a block:");
            debug.printBlock(block);
        }
    }

    public void removeAllBlocks(ArrayList<Block> toBeRemoved){
        for (Block block: toBeRemoved){
            this.removeBlock(block);
        }
    }




    public Block getBlock(int columnNumber, int positionInColumn){
        Block ans = new Block();
        if (blockLocationIsValid(columnNumber,positionInColumn)){
            Block block = getColumnOfBlocks(columnNumber).getBlock(positionInColumn);
            ans = block;
        }
        return ans;
    }

    private boolean blockLocationIsValid(int columnNumber, int positionInColumn){
        int rowSize = this.getContainingColumns().size();
        //System.out.println("rowSize "+rowSize);
        boolean matchesColumnNumberConstraint = (columnNumber >= 0) && (columnNumber < rowSize);
        //int columnSize = this.getColumnOfBlocks(columnNumber).getContainingBlocks().size();
        int columnSize = MAX_HEIGHT;
        //System.out.println("columnSize "+columnSize);
        boolean matchesPositionInColumnConstraint = (positionInColumn >= 0) && (positionInColumn < columnSize);
        return (matchesColumnNumberConstraint && matchesPositionInColumnConstraint);
    }



    public ColumnOfBlocks getColumnOfBlocks(int columnNumber){
        ColumnOfBlocks columnOfBlocks = new ColumnOfBlocks();
        for (ColumnOfBlocks column : this.getContainingColumns()){
            if (column.getPositionInRowOfColumns() == columnNumber){
                columnOfBlocks = column;
            }
        }
        return columnOfBlocks;
    }

    @Override
    public RowOfColumns clone(){
        BoardFactory boardFactory = new BoardFactory();
        return boardFactory.createRowOfColumnsClone(this);
    }


//
//    public Block getNorthBlock(Block block) {
//        //System.out.println("north block");
//        return getBlock(block.getColumnNumber(),block.getPositionInColumn()-1);
//    }
//
//    public Block getEastBlock(Block block) {
//        //System.out.println("east block");
//        return getBlock(block.getColumnNumber()-1,block.getPositionInColumn());
//    }
//
//    public Block getSouthBlock(Block block) {
//        //System.out.println("south block");
//        return getBlock(block.getColumnNumber(),block.getPositionInColumn()+1);
//    }
//
//    public Block getWestBlock(Block block) {
//        //System.out.println("west block");
//        return getBlock(block.getColumnNumber()+1,block.getPositionInColumn());
//    }


}
