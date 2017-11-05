import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/24/17.
 */
public class ServiceColumn {

    Debug debug = new Debug();

    public ColumnOfBlocks applyGravityToColumnOfBlocks(ColumnOfBlocks currColumn){
        int si = 1;
        System.out.println("preCRUNCH");
        for (Block block: currColumn.getContainingBlocks()){
            debug.printBlock(block);
        }
        for (int fi=0; fi < currColumn.getContainingBlocks().size()-1;fi++){
            Block curr = currColumn.getContainingBlocks().get(fi);
            Block next = currColumn.getContainingBlocks().get(si);
            // if they aren't one after the other then there is a gap
            if (!(next.getPositionInColumn()-curr.getPositionInColumn()==1)){
                int howMany = (next.getPositionInColumn()-curr.getPositionInColumn())-1;
                ArrayList<Block> blocksOnTop = getBlocksOnTopOfBlock(curr, currColumn);
                blocksOnTop.add(curr);
                ArrayList<Block> blocksOnBottom = getBlocksBottomOfBlock(next, currColumn);
                blocksOnBottom.add(next);
                ArrayList<Block> shiftedDownBlocksOnTop = shiftDownBlocks(blocksOnTop,howMany);
                ColumnOfBlocks adjustedColumn = new ColumnOfBlocks();
                adjustedColumn.addAllBlocks(shiftedDownBlocksOnTop);
                adjustedColumn.addAllBlocks(blocksOnBottom);
                currColumn = adjustedColumn;
                fi=0;
                si=1;
            }
            si++;
        }
        System.out.println("postCRUNCH:");
        for (Block block: currColumn.getContainingBlocks()){
            debug.printBlock(block);
        }

        Block lastBlock = currColumn.getContainingBlocks().get(currColumn.getContainingBlocks().size()-1);

        boolean isLastBlockAtFloor = lastBlock.getPositionInColumn()==4;
        if (!isLastBlockAtFloor){
            System.out.println("moving the whole thing to the floor");
            int howFarItIsFromTheFloor = lastBlock.getPositionInColumn()-4;
            ColumnOfBlocks adjustedColumn = new ColumnOfBlocks();
            ArrayList<Block> adjustedBlocks = shiftDownBlocks(currColumn.getContainingBlocks(),howFarItIsFromTheFloor);
            adjustedColumn.addAllBlocks(adjustedBlocks);
            currColumn = adjustedColumn;
        }
        System.out.println("postGRAVITY:");
        for (Block block: currColumn.getContainingBlocks()) {
            debug.printBlock(block);
        }

        return currColumn;
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
