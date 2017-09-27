import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/26/17.
 */
public class RowOfColumns {



    private ArrayList<ColumnOfBlocks> containingColumns = new ArrayList<ColumnOfBlocks>();

    public void addColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        this.containingColumns.add(columnOfBlocks);
    }

    public void removeColumnOfBlocks(ColumnOfBlocks columnOfBlocks){
        this.containingColumns.remove(columnOfBlocks);
    }

    public ArrayList<ColumnOfBlocks> getContainingColumns() {
        return containingColumns;
    }

    public void setContainingColumns(ArrayList<ColumnOfBlocks> containingColumns) {
        this.containingColumns = containingColumns;
    }
}
