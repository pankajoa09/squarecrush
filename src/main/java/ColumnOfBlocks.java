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
        if (!this.containingBlocks.contains(block) && block.isActive()) {
            this.containingBlocks.add(block);
        }
    }


    public void addAllBlocks(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addBlock(block);
        }
    }




    public void removeBlock(Block block){
        this.containingBlocks.remove(block);
        Block nullBlock = new Block();
        nullBlock.createNullBlock(block.getPositionInColumn(),block.getColumnNumber());
    }

    public Block getBlock(int positionInColumn){
        Block block = null;
        for (Block blk : this.containingBlocks){
            if (blk.getPositionInColumn() == positionInColumn){
                block = blk;
                break;
            }
        }
        return block;
    }




}
