import com.sun.rowset.internal.Row;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class RowOfColumns {



    private ArrayList<ColumnOfBlocks> containingColumns = new ArrayList<ColumnOfBlocks>();

    public void addColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        this.containingColumns.add(columnOfBlocks);
    }

    public void removeColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        this.containingColumns.remove(columnOfBlocks);
    }

    public ArrayList<ColumnOfBlocks> getContainingColumns() {
        return containingColumns;
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
            System.out.println("couldn't remove block because it doesn't exist");
        }
    }




    public Block getBlockNew(int columnNumber, int positionInColumn){
        Block ans = new Block();
        if (blockLocationIsValid(columnNumber,positionInColumn)){
            Block block = getColumnOfBlocks(columnNumber).getBlock(positionInColumn);
            ans = block;
        }
        else{
            Block block = new Block();
            block.createRandomBlock(-100,-100,3);
            ans = block;
        }
        return ans;
    }

    private Boolean blockLocationIsValid(int columnNumber, int positionInColumn){
        int rowSize = this.getContainingColumns().size();
        Boolean matchesColumnNumberConstraint = (columnNumber >= 0) && (columnNumber < rowSize);
        int columnSize = this.getColumnOfBlocks(columnNumber).getContainingBlocks().size();
        Boolean matchesPositionInColumnConstraint = (positionInColumn >= 0) && (positionInColumn < columnSize);
        return (matchesColumnNumberConstraint && matchesPositionInColumnConstraint);
    }

    public Block getBlock(int columnNumber, int positionInColumn){
        Debug debug = new Debug();

        Block block = new Block();
        int rowSize = this.getContainingColumns().size();
        Boolean matchesColumnNumberConstraint = (columnNumber >= 0) && (columnNumber < rowSize);
        if (matchesColumnNumberConstraint) {
            int columnSize = this.getColumnOfBlocks(columnNumber).getContainingBlocks().size();
            Boolean matchesPositionInColumnConstraint = (positionInColumn >= 0) && (positionInColumn < columnSize);
            if (matchesPositionInColumnConstraint) {
                block = getColumnOfBlocks(columnNumber).getBlock(positionInColumn);
            } else {
                block.createRandomBlock(-100, -100, 3);
            }
        }
        else{
            block.createRandomBlock(-100,-100,3);
        }
        return block;
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



    public Block getNorthBlock(Block block) {
        //System.out.println("north block");
        return getBlock(block.getColumnNumber(),block.getPositionInColumn()-1);
    }

    public Block getEastBlock(Block block) {
        //System.out.println("east block");
        return getBlock(block.getColumnNumber()-1,block.getPositionInColumn());
    }

    public Block getSouthBlock(Block block) {
        //System.out.println("south block");
        return getBlock(block.getColumnNumber(),block.getPositionInColumn()+1);
    }

    public Block getWestBlock(Block block) {
        //System.out.println("west block");
        return getBlock(block.getColumnNumber()+1,block.getPositionInColumn());
    }


}
