

import com.sun.rowset.internal.Row;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pjoa09 on 10/1/17.
 */
public class Engine {

    Debug debug = new Debug();






    private ArrayList<Block> getBlocksToRemove(Block block, RowOfColumns rowOfColumns, ArrayList<Block> clusterOfBlocks){
        // because we cannot let it look backwards we need to tell it where it has been, which is in the parameter as visited.
        ArrayList<Block> blocksToRemove = new ArrayList<Block>();
        ArrayList<Block> surroundingBlocks = new ArrayList<Block>();
        //System.out.println("---------");

        //if it isn't a retarded block that has a position of -100 take it.
        if(!(rowOfColumns.getNorthBlock(block).getPositionInColumn()==-100)){
            //System.out.println("ADDED NORTH");
            //debug.printBlock(rowOfColumns.getNorthBlock(block));
            surroundingBlocks.add(rowOfColumns.getNorthBlock(block));}

        if(!(rowOfColumns.getEastBlock(block).getPositionInColumn()==-100)){
            //System.out.println("ADDED EAST");
            //debug.printBlock(rowOfColumns.getEastBlock(block));
            surroundingBlocks.add(rowOfColumns.getEastBlock(block));}

        if(!(rowOfColumns.getSouthBlock(block).getPositionInColumn()==-100)){
            //System.out.println("ADDED SOUTH");
            //debug.printBlock(rowOfColumns.getSouthBlock(block));
            surroundingBlocks.add(rowOfColumns.getSouthBlock(block));}

        if(!(rowOfColumns.getWestBlock(block).getPositionInColumn()==-100)){
            //System.out.println("ADDED WEST");
            //debug.printBlock(rowOfColumns.getWestBlock(block));
            surroundingBlocks.add(rowOfColumns.getWestBlock(block));}

        //System.out.println("surrounding blocks");
        //for (Block blockk : surroundingBlocks){
//            System.out.println(blockk.getColumnNumber()+" "+blockk.getPositionInColumn());
//        }

        for (Block surroundingBlock : surroundingBlocks){
            //if they are of the same type and are not in visited
            if (surroundingBlock.getBlockImage().getName().equals(block.getBlockImage().getName()) && (!clusterOfBlocks.contains(surroundingBlock))){
                blocksToRemove.add(surroundingBlock);
            }
        }

        return blocksToRemove;
    }



    public Animated getClusterOfBlocks(Block block, Animated animated){

        ArrayList<Block> clusterOfBlocks = new ArrayList<Block>();
        Queue<Block> visited = new LinkedList<Block>();
        //visited[a]
        //just getting around the non active blocks
        while (!visited.isEmpty()) {
            //visited.pop() = a -> surround(a) = [b]
            Block current = visited.remove();
            animated.addToDestroy(current);
            ArrayList<Block> surround = getBlocksToRemove(current, animated.getRowOfColumns(), animated.getToDestroy());
            //visited.append([b])
            if (!surround.isEmpty()) {
                for (Block blk : surround) {
                    visited.add(blk);
                }
            }

            //visited[b]
        }
        return animated;
    }

    public Animated processClicked(Block clicked, RowOfColumns rowOfColumns){
        Animated animated = new Animated();
        animated.setRowOfColumns(rowOfColumns);
        animated = getClusterOfBlocks(clicked,animated);
        animated = destroyCluster(animated);
        return animated;
    }

    public Animated destroyCluster(Animated animated){
        ArrayList<Block> fade = animated.getToDestroy();
        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        debug.printArrayInRowOfColumns(fade,rowOfColumns);
        for (Block block : fade){
            animated = destroyBlock(block,animated);

        }
        debug.printArrayInRowOfColumns(fade,rowOfColumns);

        return animated;
    }

    private Animated destroyBlock(Block block, Animated animated){
        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        ColumnOfBlocks column = rowOfColumns.getColumnOfBlocks(block.getColumnNumber());
        // remove the block
        column.removeBlock(block);
        //System.out.println("OLD BLOCK");
        //debug.printBlock(block);
        // get the top blocks shift them down
        ArrayList<Block> topBlocks = getBlocksOnTopOfBlock(block,column);
        ArrayList<Block> topBlocksShifted = shiftDownBlocks(topBlocks,1);
        // get the bottom blocks
        ArrayList<Block> bottomBlocks = getBlocksBottomOfBlock(block,column);
        //create a block to replace that lost block and place it on top
        Block replacer = new Block();
        replacer.createRandomBlock(0,column.getPositionInRowOfColumns(),3);
        //add the replacement to the top blocks
        topBlocksShifted.add(replacer);
        //add the bottom blocks
        topBlocksShifted.addAll(bottomBlocks);
        //set the columns blocks as such
        column.setContainingBlocks(topBlocksShifted);
        //remove the old column
        rowOfColumns.removeColumnOfBlocks(rowOfColumns.getColumnOfBlocks(block.getColumnNumber()));
        //add the new column
        rowOfColumns.addColumnOfBlocks(column);
        //needed for animation
        animated.addToPlaceOnTop(replacer);
        animated.addAllToFall(topBlocks);
        //set them back into animate
        animated.setRowOfColumns(rowOfColumns);
        return animated;
    }

    private ArrayList<Block> getBlocksOnTopOfBlock(Block block,ColumnOfBlocks columnOfBlocks){
        //System.out.println("");
        //System.out.println("GET BLOCKS ON TOP:");
        ArrayList<Block> blocksOnTopOfBlock = new ArrayList<Block>();
        for (Block blk: columnOfBlocks.getContainingBlocks()){
            if (blk.getPositionInColumn()<block.getPositionInColumn()){
                //debug.printBlock(blk);
                blocksOnTopOfBlock.add(blk);
            }
        }
        return blocksOnTopOfBlock;
    }

    private ArrayList<Block> shiftDownBlocks(ArrayList<Block> blocks,int shiftDownBy){
        ArrayList<Block> shiftedDown = new ArrayList<Block>();
        //System.out.println("");
        //System.out.println("GET SHIFTED BLOCKS:");
        for (Block block : blocks){
            BlockImage blockImage = block.getBlockImage();
            Block shifted = new Block();
            shifted.createBlock(block.getPositionInColumn()+shiftDownBy,block.getColumnNumber(),blockImage);

            //debug.printBlock(shifted);
            shiftedDown.add(shifted);
        }
        return shiftedDown;
    }

    private ArrayList<Block> getBlocksBottomOfBlock(Block block,ColumnOfBlocks columnOfBlocks){
        //System.out.println("");
        //System.out.println("GET BOTTOM BLOCKS");
        ArrayList<Block> blocksBottomOfBlock = new ArrayList<Block>();
        for (Block blk: columnOfBlocks.getContainingBlocks()){
            if (blk.getPositionInColumn()>block.getPositionInColumn()){
                //debug.printBlock(blk);
                blocksBottomOfBlock.add(blk);

            }
        }
        return blocksBottomOfBlock;
    }
















}
