import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Animated{

    private ArrayList<Block> toDestroy = new ArrayList<Block>();
    private ArrayList<Block> toFall = new ArrayList<Block>();
    private RowOfColumns rowOfColumns = new RowOfColumns();

    private Clicks clicks = new Clicks();

    public Clicks getClicks() {
        return clicks;
    }

    public void setClicks(Clicks clicks) {
        this.clicks = clicks;
    }

    public RowOfColumns getRowOfColumns() {
        return rowOfColumns;
    }

    public void setRowOfColumns(RowOfColumns rowOfColumns) {
        this.rowOfColumns = rowOfColumns;
    }

    public ArrayList<Block> getToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(ArrayList<Block> toDestroy) {
        this.toDestroy = toDestroy;
    }

    public ArrayList<Block> getToFall() {
        return toFall;
    }

    public void setToFall(ArrayList<Block> toFall) {
        this.toFall = toFall;
    }

}



