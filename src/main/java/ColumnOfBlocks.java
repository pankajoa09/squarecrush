import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class ColumnOfBlocks {

    int positionInRowOfColumns;

    private ArrayList<Block> containingBlocks = new ArrayList<Block>();

    public int getPositionInRowOfColumns() {
        return positionInRowOfColumns;
    }

    public void setPositionInRowOfColumns(int positionInRowOfColumns) {
        this.positionInRowOfColumns = positionInRowOfColumns;
    }

    public ArrayList<Block> getContainingBlocks() {
        return containingBlocks;
    }

    public void setContainingBlocks(ArrayList<Block> containingBlocks) {
        this.containingBlocks = containingBlocks;
    }



    public void addBlock(Block block){
        this.containingBlocks.add(block);
    }

    public void removeBlock(Block block){
        this.containingBlocks.remove(block);
    }




}
