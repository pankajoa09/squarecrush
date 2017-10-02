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

    public Block getBlock(int columnNumber, int positionInColumn){
        return getColumnOfBlocks(columnNumber).getBlock(positionInColumn);
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



    public Block getNorthBlock(Block block){
        return getBlock(block.getColumnNumber()-1,block.getPositionInColumn());
    }

    public Block getEastBlock(Block block){
        return getBlock(block.getColumnNumber(),block.getPositionInColumn()+1);
    }

    public Block getSouthBlock(Block block){
        return getBlock(block.getColumnNumber()+1,block.getPositionInColumn());
    }

    public Block getWestBlock(Block block){
        return getBlock(block.getColumnNumber(),block.getPositionInColumn()-1);
    }


}
