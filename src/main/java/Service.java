









import java.lang.Math;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pjoa09 on 10/23/17.
 */
public class Service{


    Debug debug = new Debug();
    ServiceColumn serviceColumn = new ServiceColumn();



    public Animated update(Animated animated){
        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        ArrayList<Block> toDestroy = getBlocksToDestroy(rowOfColumns);
        ArrayList<Block> blocksThatWillFall = getBlocksThatWillFall(toDestroy,rowOfColumns);
        rowOfColumns = destroyBlocks(rowOfColumns,toDestroy);
        rowOfColumns = applyGravityToRowOfColumns(rowOfColumns);
        ArrayList<Block> replacementBlocks = createReplacementBlocks(rowOfColumns);
        rowOfColumns = addReplacementBlocksToRowOfColumns(replacementBlocks,rowOfColumns);
        animated.setToDestroy(toDestroy);
        animated.setRowOfColumns(rowOfColumns);
        animated.setToPlaceOnTop(replacementBlocks);
        animated.setToFall(blocksThatWillFall);
        return animated;
    }

    public Animated swapAndUpdate(Animated animated){

        RowOfColumns rowOfColumns = animated.getRowOfColumns();
        System.out.println("ITS STILL HERE: SEE?");
        debug.printRowOfColumns(rowOfColumns);

        Block firstClick = animated.getClicks().getFirstClick();
        Block secondClick = animated.getClicks().getSecondClick();

        Boolean swapPossible = isSwapPossible(firstClick,secondClick,rowOfColumns);
        if (swapPossible){
            rowOfColumns = applySwap(firstClick,secondClick,rowOfColumns);
            animated.setRowOfColumns(rowOfColumns);
            animated = update(animated);
        }
        else{
            System.out.println("Swap not possible");
            //I dont know really. G
        }


        return animated;
    }

    public ArrayList<Block> getBlocksThatWillFall(ArrayList<Block> destroyed, RowOfColumns rowOfColumns){
        ArrayList<Block> blocksThatFall = new ArrayList<Block>();
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
            for (Block block: columnOfBlocks.getContainingBlocks()){
                if (destroyed.contains(block)){
                    Block max = block;
                    blocksThatFall.addAll(serviceColumn.getBlocksOnTopOfBlock(max,columnOfBlocks));
                    break;
                }
            }
        }
        return blocksThatFall;
    }





    public RowOfColumns applyGravityToRowOfColumns(RowOfColumns rowOfColumns){

        ArrayList<ColumnOfBlocks> internals = new ArrayList<>();
        for (int i = 0; i < rowOfColumns.getContainingColumns().size();i++){
            ColumnOfBlocks currColumn = rowOfColumns.getColumnOfBlocks(i);
            currColumn = serviceColumn.applyGravityToColumnOfBlocks(currColumn);
            internals.add(currColumn);
        }
        rowOfColumns.setContainingColumns(internals);

        return rowOfColumns;
    }



    public RowOfColumns addReplacementBlocksToRowOfColumns(ArrayList<Block> replacement,RowOfColumns rowOfColumns){
        for (Block block: replacement){
            rowOfColumns.addBlock(block);
        }
        return rowOfColumns;
    }

    public RowOfColumns applySwap(Block firstClick, Block secondClick, RowOfColumns rowOfColumns){

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

        }
        else{
            System.out.println("move was illegal");
        }

        return rowOfColumns;
    }

    public RowOfColumns destroyAndReplaceBlocks(RowOfColumns rowOfColumns){
        ArrayList<Block> toDestroy = getBlocksToDestroy(rowOfColumns);
        rowOfColumns = destroyBlocks(rowOfColumns, toDestroy);
        rowOfColumns = applyGravityToRowOfColumns(rowOfColumns);
        ArrayList<Block> replacementBlocks = createReplacementBlocks(rowOfColumns);
        rowOfColumns = addReplacementBlocksToRowOfColumns(replacementBlocks,rowOfColumns);
        return rowOfColumns;
    }

    public RowOfColumns destroyBlocks(RowOfColumns rowOfColumns, ArrayList<Block> toDestroy){
        rowOfColumns.removeAllBlocks(toDestroy);
        return rowOfColumns;
    }

    public boolean isSwapPossible(Block firstClick, Block secondClick, RowOfColumns rowOfColumns){
        BoardFactory boardFactory = new BoardFactory();
        RowOfColumns hypoRowOfColumns = boardFactory.createRowOfColumnsClone(rowOfColumns);
        hypoRowOfColumns = applySwap(firstClick,secondClick, hypoRowOfColumns);
        System.out.println("hypothetical row of columns");
        debug.printRowOfColumns(hypoRowOfColumns);
        hypoRowOfColumns = destroyAndReplaceBlocks(hypoRowOfColumns);
        System.out.println("hypothetical row of columns after destroy and replace");
        debug.printRowOfColumns(hypoRowOfColumns);

        return didItChange(hypoRowOfColumns, rowOfColumns);
    }

    public boolean didItChange(RowOfColumns hypoRowOfColumns, RowOfColumns rowOfColumns){
        Boolean didItChange = false;
        for (int pos=0;pos<rowOfColumns.getColumnOfBlocks(1).getContainingBlocks().size();pos++) {
            for (int col = 0; col < rowOfColumns.getContainingColumns().size(); col++) {
                String hypo = hypoRowOfColumns.getBlock(col,pos).getBlockImage().getName();
                String real = rowOfColumns.getBlock(col,pos).getBlockImage().getName();
                //System.out.println(hypo);
                //System.out.println(real);
                if (!(hypo.equals(real))){
                    didItChange=true;
                }
            }
        }
        System.out.println(didItChange);
        return didItChange;
    }



    public ArrayList<Block> getBlocksToDestroy(RowOfColumns rowOfColumns){
        ArrayList<Block> blocksToDestroy = new ArrayList<Block>();
        blocksToDestroy.addAll(getBlocksToDestroyVertical(rowOfColumns));
        blocksToDestroy.addAll(getBlocksToDestroyHorizontal(rowOfColumns));

        return blocksToDestroy;
    }

    public ArrayList<Block> getBlocksToDestroyVertical(RowOfColumns rowOfColumns){
        ArrayList<Block> ans = new ArrayList<Block>();
        for (int i = 0; i <= rowOfColumns.getContainingColumns().size(); i++){
            ColumnOfBlocks currColumn = rowOfColumns.getColumnOfBlocks(i);
            ArrayList<Block> threeOrMores = threeOrMore(currColumn.getContainingBlocks());
            ans.addAll(threeOrMores);
        }
        return ans;
    }

    public ArrayList<Block> getBlocksToDestroyHorizontal(RowOfColumns rowOfColumns){
        ArrayList<Block> ans = new ArrayList<Block>();
        int height = rowOfColumns.getColumnOfBlocks(0).getContainingBlocks().size();
        for (int row = 0; row< height;row++){
            ColumnOfBlocks reallyARow = new ColumnOfBlocks();
            for (int col=0; col< rowOfColumns.getContainingColumns().size();col++){
                reallyARow.addBlock(rowOfColumns.getBlock(col,row));
            }
            ArrayList<Block> threeOrMores = threeOrMore(reallyARow.getContainingBlocks());
            ans.addAll(threeOrMores);
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







    public ArrayList<Block> createReplacementBlocks(RowOfColumns rowOfColumns){
        ArrayList<Block> replacement = new ArrayList<Block>();
        for (ColumnOfBlocks column : rowOfColumns.getContainingColumns()){
            ArrayList<Block> perColumn = serviceColumn.createReplacementBlocks(column);
            replacement.addAll(perColumn);
        }
        return replacement;
    }










}
