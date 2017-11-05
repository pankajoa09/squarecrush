import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Animated{

    private ArrayList<Block> toDestroy = new ArrayList<Block>();
    private ArrayList<Block> toFall = new ArrayList<Block>();
    private ArrayList<Block> toPlaceOnTop = new ArrayList<Block>();
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


    public void addToDestroy(Block block){
        if (!this.toDestroy.contains(block)) {
            this.toDestroy.add(block);
        }
    }


    public void addAllToDestroy(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addToDestroy(block);
        }
    }

    public void removeFromToDestroy(Block block) {
        if (this.toDestroy.contains(block)){
            this.toDestroy.remove(block);
        }
        else{
            System.out.println("It's not in tofade");
        }
    }

    public void addToFall(Block block){
        if (!this.toFall.contains(block)) {
            this.toFall.add(block);
        }
    }

    public void addAllToFall(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addToFall(block);
        }
    }

    public void removeFromToFall(Block block) {
        if (this.toFall.contains(block)){
            this.toFall.remove(block);
        }
        else{
            System.out.println("It's not in tomovedown");
        }
    }




    public void addToPlaceOnTop(Block block){
        if (!this.toPlaceOnTop.contains(block)) {
            this.toPlaceOnTop.add(block);
        }
    }

    public void addAllToPlaceOnTop(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addToPlaceOnTop(block);
        }
    }



    public void removeFromToPlaceOnTop(Block block) {
        if (this.toPlaceOnTop.contains(block)){
            this.toPlaceOnTop.remove(block);
        }
        else{
            System.out.println("It's not in toplaceontop");
        }
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

    public ArrayList<Block> getToPlaceOnTop() {
        return toPlaceOnTop;
    }

    public void setToPlaceOnTop(ArrayList<Block> toPlaceOnTop) {
        this.toPlaceOnTop = toPlaceOnTop;
    }


}



