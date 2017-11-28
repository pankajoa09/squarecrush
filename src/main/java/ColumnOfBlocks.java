

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class ColumnOfBlocks {



    Debug debug = new Debug();

    int positionInRowOfColumns;

    private ArrayList<Block> containingBlocks = new ArrayList<Block>();

    public int getPositionInRowOfColumns() {
        return positionInRowOfColumns;
    }

    public void setPositionInRowOfColumns(int positionInRowOfColumns) {
        this.positionInRowOfColumns = positionInRowOfColumns;
    }

    public int size(){
        return containingBlocks.size();
    }

    public ArrayList<Block> getContainingBlocks() {
        Collections.sort(this.containingBlocks, (Block s1, Block s2) ->
                (s1.getPositionInColumn() - s2.getPositionInColumn()));
        return this.containingBlocks;
    }

    int maxHeight;

    public int getMaxHeight(){
        return this.maxHeight;
    }

    public void updateMaxHeight(int height){
        if (height > this.maxHeight){
            this.maxHeight = height;
        }
    }

    public void setContainingBlocks(ArrayList<Block> containingBlocks) {
        this.containingBlocks = containingBlocks;
    }

    public void addBlock(Block block){
        if ((!this.containingBlocks.contains(block)) && (block.getColumnNumber()==this.getPositionInRowOfColumns())) {
            this.containingBlocks.add(block);
            updateMaxHeight(this.size());
        }
        else if (this.containingBlocks.contains(block)){
            System.out.print("block exists in column ");
            debug.printBlock(block);
        }
        else if (block.getColumnNumber()!=this.getPositionInRowOfColumns()){
            System.out.print("block doesn't belong to the column number: "+this.getPositionInRowOfColumns()+" ");
            debug.printBlock(block);
        }


    }


    public void addAllBlocks(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addBlock(block);
        }
    }


    public void removeBlock(Block block){
        this.containingBlocks.remove(block);

    }

    public void removeAllBlocks(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.removeBlock(block);
        }
    }




    public Block getBlock(int positionInColumn) {
        Block ans = new Block();
        //System.out.println("----getblock---");
        for (Block poss : this.getContainingBlocks()) {
            //debug.printBlock(poss);
            if (poss.getPositionInColumn() == positionInColumn) {
                    ans = poss;
            }
        }
        //System.out.println("----getblockEND---");
        //System.out.print("ANS:");
        //debug.printBlock(ans);
        return ans;
    }


}
