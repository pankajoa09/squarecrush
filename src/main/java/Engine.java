

import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/1/17.
 */
public class Engine {

    public ArrayList<Block> getBlocksToRemove(Block block, RowOfColumns rowOfColumns){
        ArrayList<Block> blocksToRemove = new ArrayList<Block>();
        ArrayList<Block> surroundingBlocks = new ArrayList<Block>();
        surroundingBlocks.add(rowOfColumns.getNorthBlock(block));
        surroundingBlocks.add(rowOfColumns.getEastBlock(block));
        surroundingBlocks.add(rowOfColumns.getSouthBlock(block));
        surroundingBlocks.add(rowOfColumns.getWestBlock(block));
        for (Block surroundingBlock : surroundingBlocks){
            if (surroundingBlock.getBlockImage().equals(block.getBlockImage())){
                blocksToRemove.add(surroundingBlock);
            }
        }
        return blocksToRemove;
    }



}
