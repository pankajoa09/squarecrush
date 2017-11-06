import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pjoa09 on 11/7/17.
 */
public abstract class VectorOfBlocks {

    Debug debug = new Debug();

    int positionInRowOfColumns;

    private ArrayList<Block> containingBlocks = new ArrayList<Block>();

    public int getPositionInRowOfColumns() {
        return positionInRowOfColumns;
    }

    public void setPositionInRowOfColumns(int positionInRowOfColumns) {
        this.positionInRowOfColumns = positionInRowOfColumns;
    }

    public ArrayList<Block> getContainingBlocks() {
        Collections.sort(this.containingBlocks, (Block s1, Block s2) ->
                (s1.getPositionInColumn() - s2.getPositionInColumn()));
        return this.containingBlocks;
    }

    public void setContainingBlocks(ArrayList<Block> containingBlocks) {
        this.containingBlocks = containingBlocks;
    }
}
