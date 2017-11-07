







import java.lang.Math;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pjoa09 on 10/23/17.
 */
public class Service{


    Debug debug = new Debug();




    public Animated updateAnimated(Block firstClick, Block secondClick, RowOfColumns rowOfColumns){

        Animated returned = new Animated();
        RowOfColumns hypoRowOfColumns = rowOfColumns.clone();
        hypoRowOfColumns = applySwap(firstClick,secondClick, rowOfColumns);
        ArrayList<Block> arbit = new ArrayList<Block>();

        System.out.println("POST-SWAP:");
        arbit.add(firstClick);
        arbit.add(secondClick);
        debug.printArrayInRowOfColumns(arbit,rowOfColumns);

        Boolean swapPossible = isSwapPossible(hypoRowOfColumns, rowOfColumns);
        if (swapPossible){
            rowOfColumns = applySwap(firstClick,secondClick,rowOfColumns);

            ArrayList<Block> toDestroy = getBlocksToDestroy(rowOfColumns);
            rowOfColumns.removeAllBlocks(toDestroy);
            ArrayList<Block> replacementBlocks = createReplacementBlocks(toDestroy,rowOfColumns);
            rowOfColumns = applyGravityToRowOfColumns(rowOfColumns);
            rowOfColumns = addReplacementBlocksToRowOfColumns(replacementBlocks,rowOfColumns);
            ArrayList<Block> blocksToFall = getBlocksThatWillFall(toDestroy,rowOfColumns);
            Animated animated = new Animated();
            animated.getClicks().setFirstClick(firstClick);
            animated.getClicks().setSecondClick(secondClick);
            animated.addAllToDestroy(toDestroy);
            animated.setRowOfColumns(rowOfColumns);
            animated.addAllToPlaceOnTop(replacementBlocks);
            animated.addAllToFall(blocksToFall);
            returned = animated;

        }
        else{
            System.out.println("Swap not possible");
            Animated animated = new Animated();
            returned = animated;
            //I dont know really. G
        }
        return returned;
    }

    public ArrayList<Block> getBlocksThatWillFall(ArrayList<Block> destroyed, RowOfColumns rowOfColumns){
        ServiceColumn serviceColumn = new ServiceColumn();
        ArrayList<Block> blocksThatFall = new ArrayList<Block>();
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
             int column = columnOfBlocks.getPositionInRowOfColumns();
             for (int i = 0; i <= columnOfBlocks.getContainingBlocks().size();i++){
                 if (destroyed.contains(columnOfBlocks.getBlock(i))){
                     Block maxBlock = rowOfColumns.getBlock(column,i);
                     blocksThatFall.addAll(serviceColumn.getBlocksOnTopOfBlock(maxBlock,columnOfBlocks));
                     break;
                 }
             }
        }
        return blocksThatFall;
    }





    private RowOfColumns applyGravityToRowOfColumns(RowOfColumns rowOfColumns){
        ServiceColumn serviceColumn = new ServiceColumn();
        RowOfColumns appliedROC = new RowOfColumns();
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
            appliedROC.addColumnOfBlocks(serviceColumn.applyGravityToColumnOfBlocks(columnOfBlocks));
        }
        return appliedROC;
    }



    private RowOfColumns addReplacementBlocksToRowOfColumns(ArrayList<Block> replacement,RowOfColumns rowOfColumns){
        for (Block block: replacement){
            rowOfColumns.addBlock(block);
        }
        return rowOfColumns;
    }

    private RowOfColumns applySwap(Block firstClick, Block secondClick, RowOfColumns rowOfColumns){

        boolean inSameRow = firstClick.getPositionInColumn()==secondClick.getPositionInColumn();
        boolean inSameColumn = firstClick.getColumnNumber()==secondClick.getColumnNumber();
        //they are in the same row and one apart
        boolean horizontalSwap = inSameRow && Math.abs(firstClick.getColumnNumber()-secondClick.getColumnNumber())==1;
        //they are in the same column and one apart
        boolean verticalSwap = inSameColumn && Math.abs(firstClick.getPositionInColumn()-secondClick.getPositionInColumn())==1;

        if (horizontalSwap || verticalSwap) {
            Block first = rowOfColumns.getBlock(firstClick.getColumnNumber(),firstClick.getPositionInColumn());
            Block second = rowOfColumns.getBlock(secondClick.getColumnNumber(),secondClick.getPositionInColumn());
            BlockImage firstImage = first.getBlockImage();
            BlockImage secondImage = second.getBlockImage();
            first.setBlockImage(secondImage);
            second.setBlockImage(firstImage);

        }/*
        else if (verticalSwap){
            ColumnOfBlocks column = rowOfColumns.getColumnOfBlocks(firstClick.getColumnNumber());
            column.removeBlock(firstClick);
            column.removeBlock(secondClick);
            int oldPosForFirst = firstClick.getPositionInColumn();
            int oldPosForSecond = secondClick.getPositionInColumn();
            firstClick.setPositionInColumn(oldPosForSecond);
            secondClick.setPositionInColumn(oldPosForFirst);
            column.addBlock(firstClick);
            column.addBlock(secondClick);
        }
        */
        else{
            System.out.println("All moves were illegal");
        }

        return rowOfColumns;
    }

    private RowOfColumns destroyAndReplaceBlocks(RowOfColumns rowOfColumns){
        ArrayList<Block> toDestroy = getBlocksToDestroy(rowOfColumns);
        rowOfColumns.removeAllBlocks(toDestroy);
        ArrayList<Block> replacementBlocks = createReplacementBlocks(toDestroy,rowOfColumns);
        rowOfColumns = applyGravityToRowOfColumns(rowOfColumns);
        rowOfColumns = addReplacementBlocksToRowOfColumns(replacementBlocks,rowOfColumns);
        return rowOfColumns;
    }

    private Boolean isSwapPossible(RowOfColumns hypoRowOfColumns, RowOfColumns rowOfColumns){
        System.out.println("is hypo legit");
        debug.printRowOfColumns(rowOfColumns);
        hypoRowOfColumns = destroyAndReplaceBlocks(hypoRowOfColumns);
        System.out.println("is hypo legit now?");
        debug.printRowOfColumns(rowOfColumns);

        return didItChange(hypoRowOfColumns, rowOfColumns);
    }

    private Boolean didItChange(RowOfColumns hypoRowOfColumns, RowOfColumns rowOfColumns){
        Boolean didItChange = false;

        for (int pos=0;pos<rowOfColumns.getColumnOfBlocks(1).getContainingBlocks().size();pos++) {
            for (int col = 0; col < rowOfColumns.getContainingColumns().size(); col++) {
                Block hypo = hypoRowOfColumns.getBlock(col,pos);
                Block real = rowOfColumns.getBlock(col,pos);
                System.out.println("---");
                debug.printBlock(hypo);
                debug.printBlock(real);
                System.out.println("---");
                }
            }
        return didItChange;
    }



    private ArrayList<Block> getBlocksToDestroy(RowOfColumns rowOfColumns){
        ArrayList<Block> blocksToDestroy = new ArrayList<Block>();
        blocksToDestroy.addAll(getBlocksToDestroyVertical(rowOfColumns));
        blocksToDestroy.addAll(getBlocksToDestroyHorizontal(rowOfColumns));
        System.out.println("ALL TO DESTROY:");
        debug.printArrayInRowOfColumns(blocksToDestroy,rowOfColumns);
        return blocksToDestroy;
    }









    public ArrayList<Block> threeOrMoreInAColumn(ColumnOfBlocks columnOfBlocks){
        int theNumber = 3;
        ArrayList<Block> ans = new ArrayList<Block>();
        //dont want duplication
        Set<Block> blockList = new HashSet<Block>();
        for (int i=0; i< columnOfBlocks.getContainingBlocks().size()-1;i++) {
            Block currBlock = columnOfBlocks.getBlock(i);
            Block nextBlock = columnOfBlocks.getBlock(i+1);

            //the one next is the same
            if (!(currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName()))){
                if (blockList.size() >= theNumber){
                    ans.addAll(blockList);
                }
                blockList.clear();
            }
            else if (currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName())){
                blockList.add(currBlock);
                blockList.add(nextBlock);
            }


        }

        if (blockList.size() >= theNumber){
            ans.addAll(blockList);
        }



        return ans;
    }

    public ArrayList<Block> threeOrMore(ArrayList<Block> vectorOfBlocks){
        int theNumber = 3;
        ArrayList<Block> ans = new ArrayList<Block>();
        //dont want duplication
        Set<Block> blockList = new HashSet<Block>();
        for (int i=0; i< vectorOfBlocks.size()-1;i++) {
            Block currBlock = vectorOfBlocks.get(i);
            Block nextBlock = vectorOfBlocks.get(i+1);

            //the one next is the same
            if (!(currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName()))){
                System.out.print("");
                if (blockList.size() >= theNumber){
                    ans.addAll(blockList);
                }
                blockList.clear();
            }
            else if (currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName())){
                blockList.add(currBlock);
                blockList.add(nextBlock);
            }


        }

        if (blockList.size() >= theNumber){
            ans.addAll(blockList);
        }



        return ans;
    }


    private ArrayList<Block> threeOrMoreInARow(RowOfBlocks columnOfBlocks){
        int theNumber = 4;
        ArrayList<Block> ans = new ArrayList<Block>();
        //dont want duplication
        Set<Block> blockList = new HashSet<Block>();
        for (int i=0; i< columnOfBlocks.getContainingBlocks().size()-1;i++) {
            Block currBlock = columnOfBlocks.getBlock(i);
            Block nextBlock = columnOfBlocks.getBlock(i+1);
            //the one next is the same
            if (currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName())){
                blockList.add(currBlock);
                blockList.add(nextBlock);
            }
            //the next one is not the same
            else if (!(currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName()))){
                if (blockList.size() >= theNumber){
                    ans.addAll(blockList);
                }
                blockList.clear();
            }
        }

        ans.addAll(blockList);
        return ans;
    }




    private ArrayList<Block> createReplacementBlocks(ArrayList<Block> destroyed, RowOfColumns rowOfColumns){
        ArrayList<Block> replacement = new ArrayList<Block>();
        for (Block oldBlock : destroyed){
            BlockImage oldBlockImage = oldBlock.getBlockImage();
            Block newBlock = new Block();
            newBlock.createBlock(0,oldBlock.getPositionInColumn(),oldBlockImage);
            replacement.add(newBlock);
        }
        return replacement;
    }


    private ArrayList<Block> getBlocksToDestroyVertical(RowOfColumns rowOfColumns){
        ArrayList<Block> ans = new ArrayList<Block>();
        for (int i = 0; i <= rowOfColumns.getContainingColumns().size(); i++){
            ColumnOfBlocks currColumn = rowOfColumns.getColumnOfBlocks(i);
            ArrayList<Block> threeOrMores = threeOrMoreInAColumn(currColumn);
            ans.addAll(threeOrMores);
        }
        System.out.println("GET BLOCKS TO DESTROY VERTICAL");
        debug.printArrayInRowOfColumns(ans,rowOfColumns);
        return ans;
    }

    private ArrayList<Block> getBlocksToDestroyHorizontal(RowOfColumns rowOfColumns){
        ArrayList<Block> ans = new ArrayList<Block>();
        int height = rowOfColumns.getColumnOfBlocks(0).getContainingBlocks().size();
        for (int row = 0; row< height;row++){
            RowOfBlocks reallyARow = new RowOfBlocks();
            for (int col=0; col< rowOfColumns.getContainingColumns().size();col++){
                reallyARow.addBlock(rowOfColumns.getBlock(col,row));
            }
            ArrayList<Block> threeOrMores = threeOrMoreInARow(reallyARow);
            ans.addAll(threeOrMores);
        }
        return ans;
    }







}
