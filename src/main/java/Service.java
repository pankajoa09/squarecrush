







import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/23/17.
 */
public class Service{


    public Animated updateAnimated(Block firstClick, Block secondClick, RowOfColumns rowOfColumns){
        Animated animated = new Animated();
        animated.getClicks().setFirstClick(firstClick);
        animated.getClicks().setSecondClick(secondClick);
        RowOfColumns hypoRowOfColumns = rowOfColumns.clone();
        hypoRowOfColumns = applySwap(firstClick,secondClick, rowOfColumns);
        Boolean swapPossible = isSwapPossible(hypoRowOfColumns, rowOfColumns);
        if (swapPossible){
            rowOfColumns = applySwap(firstClick,secondClick,rowOfColumns);
            ArrayList<Block> toDestroy = getBlocksToDestroy(rowOfColumns);
            rowOfColumns = destroyBlocks(toDestroy,rowOfColumns);
            ArrayList<Block> replacementBlocks = createReplacementBlocks(toDestroy,rowOfColumns);
            rowOfColumns = applyGravityToRowOfColumns(rowOfColumns);
            rowOfColumns = addReplacementBlocksToRowOfColumns(replacementBlocks,rowOfColumns);
            animated.addAllToFade(toDestroy);
            animated.setRowOfColumns(rowOfColumns);
            animated.addAllToPlaceOnTop(replacementBlocks);
            animated.addAllToMoveDown(replacementBlocks);
//            animated.addAllToMoveDown();
        }
        else{
        }
        return animated;
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
        ColumnOfBlocks firstColumn = rowOfColumns.getColumnOfBlocks(firstClick.getColumnNumber());
        ColumnOfBlocks secondColumn = rowOfColumns.getColumnOfBlocks(secondClick.getColumnNumber());
        firstColumn.removeBlock(firstClick);
        firstColumn.addBlock(secondClick);
        secondColumn.removeBlock(secondClick);
        secondColumn.addBlock(firstClick);
        return rowOfColumns;
    }

    private Boolean isSwapPossible(RowOfColumns hypoRowOfColumns, RowOfColumns rowOfColumns){
        hypoRowOfColumns = destroyBlocks(getBlocksToDestroy(rowOfColumns),rowOfColumns);
        return didItChange(hypoRowOfColumns, rowOfColumns);
    }

    private Boolean didItChange(RowOfColumns hypoRowOfColumns, RowOfColumns rowOfColumns){
        System.out.println("---------------");
        Boolean didItChange = false;
        for (int pos=0;pos<rowOfColumns.getColumnOfBlocks(1).getContainingBlocks().size();pos++) {
            for (int col = 0; col < rowOfColumns.getContainingColumns().size(); col++) {
                if (!(rowOfColumns.getBlock(col, pos).getBlockImage().equals(hypoRowOfColumns.getBlock(col, pos).getBlockImage()))) {
                    didItChange = true;
                }
            }
        }
        return didItChange;
    }



    private ArrayList<Block> getBlocksToDestroy(RowOfColumns rowOfColumns){
        ArrayList<Block> blocksToDestroy = new ArrayList<Block>();
        blocksToDestroy.addAll(getBlocksToDestroyHorizontal(rowOfColumns));
        blocksToDestroy.addAll(getBlocksToDestroyVertical(rowOfColumns));
        return blocksToDestroy;
    }

    private ArrayList<Block> threeOrMoreInAColumn(ColumnOfBlocks columnOfBlocks){
        int theNumber = 3;
        ArrayList<Block> ans = new ArrayList<Block>();
        for (int i=0; i<= columnOfBlocks.getContainingBlocks().size();i++){
            Block currBlock = columnOfBlocks.getBlock(i);
            ArrayList<Block> blockList = new ArrayList<Block>();
            for (int j=i; j<= columnOfBlocks.getContainingBlocks().size();j++){
                Block nextBlock = columnOfBlocks.getBlock(j);
                if (currBlock.getBlockImage().getName().equals(nextBlock.getBlockImage().getName())){
                    //then they the same kinda block
                    blockList.add(nextBlock);
                }
            }
            if (blockList.size()>=theNumber){
                ans.addAll(blockList);
            }
        }
        return ans;
    }

    private RowOfColumns destroyBlocks(ArrayList<Block> blocks, RowOfColumns rowOfColumns){
        for (Block block: blocks){
            rowOfColumns.removeBlock(block);
        }
        return rowOfColumns;
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
        return ans;
    }

    private ArrayList<Block> getBlocksToDestroyHorizontal(RowOfColumns rowOfColumns){
        ArrayList<Block> ans = new ArrayList<Block>();
        int height = rowOfColumns.getColumnOfBlocks(0).getContainingBlocks().size();
        for (int row = 0; row<= height;row++){
            ColumnOfBlocks reallyARow = new ColumnOfBlocks();
            for (int col=0; col<= rowOfColumns.getContainingColumns().size();col++){
                reallyARow.addBlock(rowOfColumns.getBlock(col,row));
            }
            ArrayList<Block> threeOrMores = threeOrMoreInAColumn(reallyARow);
            ans.addAll(threeOrMores);
        }
        return ans;
    }




}
