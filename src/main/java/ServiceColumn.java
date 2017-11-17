import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/24/17.
 */
public class ServiceColumn {

    Debug debug = new Debug();

    private static final int MAX_HEIGHT = 5;
    private static final int POOL_SIZE = 3;

    public ColumnOfBlocks applyGravityToColumnOfBlocks(ColumnOfBlocks currColumn){
        //System.out.println("old: "+currColumn.getPositionInRowOfColumns());
        currColumn = applyCrunchToColumnOfBlocks(currColumn);
        //System.out.println("new1: "+currColumn.getPositionInRowOfColumns());
        currColumn = applyDropToColumnOfBlocks(currColumn);
        //System.out.println("new2: "+currColumn.getPositionInRowOfColumns());
        return currColumn;
    }

    public ColumnOfBlocks applyCrunchToColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        ColumnOfBlocks currColumn = columnOfBlocks;
        currColumn.setPositionInRowOfColumns(columnOfBlocks.getPositionInRowOfColumns());
        int si = 1;
        for (int fi=0; fi < currColumn.getContainingBlocks().size()-1;fi++){
            Block curr = currColumn.getContainingBlocks().get(fi);
            Block next = currColumn.getContainingBlocks().get(si);
            //System.out.println("----ITER");
            // if they aren't one after the other then there is a gap
            ColumnOfBlocks adjustedColumn = new ColumnOfBlocks();
            adjustedColumn.setPositionInRowOfColumns(columnOfBlocks.getPositionInRowOfColumns());
            if (!(next.getPositionInColumn()-curr.getPositionInColumn()==1)){
                int howMany = (next.getPositionInColumn()-curr.getPositionInColumn())-1;
                ArrayList<Block> blocksOnTop = getBlocksOnTopOfBlock(curr, currColumn);
                blocksOnTop.add(curr);
                ArrayList<Block> blocksOnBottom = getBlocksBottomOfBlock(next, currColumn);
                blocksOnBottom.add(next);
                ArrayList<Block> shiftedDownBlocksOnTop = shiftDownBlocks(blocksOnTop,howMany);
                adjustedColumn.addAllBlocks(shiftedDownBlocksOnTop);
                adjustedColumn.addAllBlocks(blocksOnBottom);
                currColumn = adjustedColumn;
                fi=0;
                si=1;
            }
            si++;
        }


        return currColumn;
    }

    public ColumnOfBlocks applyDropToColumnOfBlocks(ColumnOfBlocks currColumn){
        if (!(currColumn.getContainingBlocks().isEmpty())) {
            Block lastBlock = currColumn.getContainingBlocks().get(currColumn.getContainingBlocks().size() - 1);
            boolean isLastBlockAtFloor = lastBlock.getPositionInColumn() == 4;
            if (!isLastBlockAtFloor) {
                int howFarItIsFromTheFloor = 4 - lastBlock.getPositionInColumn();
                ColumnOfBlocks adjustedColumn = new ColumnOfBlocks();
                adjustedColumn.setPositionInRowOfColumns(currColumn.getPositionInRowOfColumns());
                ArrayList<Block> adjustedBlocks = shiftDownBlocks(currColumn.getContainingBlocks(), howFarItIsFromTheFloor);
                adjustedColumn.addAllBlocks(adjustedBlocks);
                currColumn = adjustedColumn;
            }
        }
        return currColumn;
    }

    public ColumnOfBlocks moveBlockInColumnToTop(Block block, ColumnOfBlocks columnOfBlocks){
        ColumnOfBlocks newColumn = new ColumnOfBlocks();
        newColumn.setPositionInRowOfColumns(columnOfBlocks.getPositionInRowOfColumns());
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
        if (!blocks.isEmpty()) {
            for (Block block : blocks) {
                BlockImage blockImage = block.getBlockImage();
                Block shifted = new Block();
                shifted.createBlock(block.getPositionInColumn() + shiftDownBy, block.getColumnNumber(), blockImage);
                //debug.printBlock(shifted);
                shiftedDown.add(shifted);
            }
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

    public ArrayList<Block> createReplacementBlocks(ColumnOfBlocks columnOfBlocks){
        ArrayList<Block> replacement = new ArrayList<Block>();
        for (int i =0; i< MAX_HEIGHT; i++){
            try{
                Block block = columnOfBlocks.getBlock(i);
                boolean smth = block.getBlockImage().getImage()==null;
            }
            catch (NullPointerException npe){
                Block block = new Block();
                block.createRandomBlock(i,columnOfBlocks.getPositionInRowOfColumns(),POOL_SIZE);
                replacement.add(block);
            }
        }
        return replacement;
    }



}
