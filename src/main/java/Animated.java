import java.util.ArrayList;

/**
 * Created by pjoa09 on 10/10/17.
 */
public class Animated{

    private ArrayList<Block> toFade = new ArrayList<Block>();
    private ArrayList<Block> toMoveDown = new ArrayList<Block>();
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


    public void addToFade(Block block){
        if (!this.toFade.contains(block) && block.isActive()) {
            this.toFade.add(block);
        }
    }


    public void addAllToFade(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addToFade(block);
        }
    }

    public void removeFromToFade(Block block) {
        if (this.toFade.contains(block)){
            this.toFade.remove(block);
        }
        else{
            System.out.println("It's not in tofade");
        }
    }

    public void addToMoveDown(Block block){
        if (!this.toMoveDown.contains(block)) {
            this.toMoveDown.add(block);
        }
    }

    public void addAllToMoveDown(ArrayList<Block> blocks){
        for (Block block : blocks) {
            this.addToMoveDown(block);
        }
    }

    public void removeFromToMoveDown(Block block) {
        if (this.toMoveDown.contains(block)){
            this.toMoveDown.remove(block);
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

    public ArrayList<Block> getToFade() {
        return toFade;
    }

    public void setToFade(ArrayList<Block> toFade) {
        this.toFade = toFade;
    }

    public ArrayList<Block> getToMoveDown() {
        return toMoveDown;
    }

    public void setToMoveDown(ArrayList<Block> toMoveDown) {
        this.toMoveDown = toMoveDown;
    }

    public ArrayList<Block> getToPlaceOnTop() {
        return toPlaceOnTop;
    }

    public void setToPlaceOnTop(ArrayList<Block> toPlaceOnTop) {
        this.toPlaceOnTop = toPlaceOnTop;
    }


}



