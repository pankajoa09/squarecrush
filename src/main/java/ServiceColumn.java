import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/24/17.
 */
public class ServiceColumn {

    public ColumnOfBlocks applyGravityToColumnOfBlocks(ColumnOfBlocks currColumn){
        ColumnOfBlocks adjustedColumn = new ColumnOfBlocks();
        for (int i=0; i <= currColumn.getContainingBlocks().size();i++){
            Block currBlock = currColumn.getBlock(i);
            if (currBlock.getBlockImage().getName().equals("null")){
                adjustedColumn = moveBlockInColumnToTop(currBlock,currColumn);
            }
        }
        return adjustedColumn;
    }

    public ColumnOfBlocks moveBlockInColumnToTop(Block block, ColumnOfBlocks columnOfBlocks){
        ColumnOfBlocks newColumn = new ColumnOfBlocks();
        ArrayList<Block> blocksOnTop = getBlocksOnTopOfBlock(block, columnOfBlocks);
        ArrayList<Block> blocksOnBottom = getBlocksBottomOfBlock(block, columnOfBlocks);
        ArrayList<Block> shiftBlocksOnTop = shiftDownBlocks(blocksOnTop,1);
        newColumn.addAllBlocks(shiftBlocksOnTop);
        newColumn.addAllBlocks(blocksOnBottom);
        Block newBlock = new Block();
        newBlock.createBlock(0,columnOfBlocks.getPositionInRowOfColumns(),block.getBlockImage());
        newColumn.addBlock(newBlock);
        return newColumn;
    }

    public ArrayList<Block> getBlocksOnTopOfBlock(Block block, ColumnOfBlocks columnOfBlocks) {
        //System.out.println("");
        //System.out.println("GET BLOCKS ON TOP:");
        ArrayList<Block> blocksOnTopOfBlock = new ArrayList<Block>();
        for (Block blk : columnOfBlocks.getContainingBlocks()) {
            if (blk.getPositionInColumn() < block.getPositionInColumn()) {
                //debug.printBlock(blk);
                blocksOnTopOfBlock.add(blk);
            }
        }
        return blocksOnTopOfBlock;
    }

    public ArrayList<Block> shiftDownBlocks(ArrayList<Block> blocks, int shiftDownBy) {
        ArrayList<Block> shiftedDown = new ArrayList<Block>();
        //System.out.println("");
        //System.out.println("GET SHIFTED BLOCKS:");
        for (Block block : blocks) {
            BlockImage blockImage = block.getBlockImage();
            Block shifted = new Block();
            shifted.createBlock(block.getPositionInColumn() + shiftDownBy, block.getColumnNumber(), blockImage);
            shifted.setActiveTrue();
            //debug.printBlock(shifted);
            shiftedDown.add(shifted);
        }
        return shiftedDown;
    }

    public ArrayList<Block> getBlocksBottomOfBlock(Block block, ColumnOfBlocks columnOfBlocks) {
        //System.out.println("");
        //System.out.println("GET BOTTOM BLOCKS");
        ArrayList<Block> blocksBottomOfBlock = new ArrayList<Block>();
        for (Block blk : columnOfBlocks.getContainingBlocks()) {
            if (blk.getPositionInColumn() > block.getPositionInColumn()) {
                //debug.printBlock(blk);
                blocksBottomOfBlock.add(blk);

            }
        }
        return blocksBottomOfBlock;
    }




}
