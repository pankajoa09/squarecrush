import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pjoa09 on 10/24/17.
 */
public class ServiceColumn {

    Debug debug = new Debug();

    private static final int MAX_HEIGHT = 5;
    private static final int POOL_SIZE = 3;

    public ArrayList<Block> getFallingBlocks(ColumnOfBlocks columnOfBlocks){
        ColumnOfBlocks currColumn = columnOfBlocks;
        currColumn.setPositionInRowOfColumns(columnOfBlocks.getPositionInRowOfColumns());

        int si = 1;
        Set<Block> blocksThatWillFall = new HashSet<>();
        if (!currColumn.getContainingBlocks().isEmpty()) {
            Block lastBlock = currColumn.getContainingBlocks().get(currColumn.getContainingBlocks().size() - 1);

            int howFarItIsFromTheFloor = 4 - lastBlock.getPositionInColumn();

            if (currColumn.getContainingBlocks().size() == 1){
                Block last = currColumn.getContainingBlocks().get(0);
                last.incrementShiftDown(howFarItIsFromTheFloor);
                blocksThatWillFall.add(last);
            }
            for (int fi = 0; fi < currColumn.getContainingBlocks().size() - 1; fi++) {

                Block curr = currColumn.getContainingBlocks().get(fi);
                Block next = currColumn.getContainingBlocks().get(si);
                //System.out.println("----ITER");
                // if they aren't one after the other then there is a gap

                if (!(next.getPositionInColumn() - curr.getPositionInColumn() == 1) || next.equals(lastBlock)) {

                    int howMany = (next.getPositionInColumn() - curr.getPositionInColumn()) - 1;

                    ArrayList<Block> blocksOnTop = getBlocksOnTopOfBlock(curr, currColumn);
                    blocksOnTop.add(curr);
                    for (Block shift : blocksOnTop) {
                        shift.incrementShiftDown(howMany);

                    }

                    if (next.equals(lastBlock)) {
                        blocksOnTop.add(next);

                        for (Block shit : blocksOnTop) {
                            shit.incrementShiftDown(howFarItIsFromTheFloor);
                        }
                    }



                    blocksThatWillFall.addAll(blocksOnTop);
                }
                si++;
            }
        }
        ArrayList<Block> ans = new ArrayList<>();
        ans.addAll(blocksThatWillFall);
        return ans;
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

    public ColumnOfBlocks dropBlocksInToColumn(ArrayList<Block> allFaller, ColumnOfBlocks columnOfBlocks){
        ArrayList<Block> faller = new ArrayList<>();
        for (Block blk: allFaller){
            if (blk.getColumnNumber()==columnOfBlocks.getPositionInRowOfColumns()){
                faller.add(blk);
            }
        }
        ArrayList<Block> fallen = shiftDownBlocksOnOwn(faller);
        columnOfBlocks.removeAllBlocks(faller);
        columnOfBlocks.addAllBlocks(fallen);
        return columnOfBlocks;
    }



    public ArrayList<Block> shiftDownBlocksOnOwn(ArrayList<Block> blocks) {
        ArrayList<Block> shiftedDown = new ArrayList<Block>();
        if (!blocks.isEmpty()) {
            for (Block block : blocks) {
                BlockImage blockImage = block.getBlockImage();
                Block shifted = new Block();
                shifted.createBlock(block.getPositionInColumn() + block.getShiftDown(), block.getColumnNumber(), blockImage);
                shiftedDown.add(shifted);
            }
        }
        return shiftedDown;
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
