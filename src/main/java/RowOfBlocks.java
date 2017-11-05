/**
 * Created by pjoa09 on 11/3/17.
 */
public class RowOfBlocks extends ColumnOfBlocks {

    public Block getBlockHori(int columnNumber){
        Block ans = new Block();
        //ans.createNullBlock(positionInColumn,this.positionInRowOfColumns);
        for (Block block : this.getContainingBlocks()){
            if (block.getColumnNumber() == columnNumber){
                ans = block;
                break;
            }
        }
        return ans;
    }

}
