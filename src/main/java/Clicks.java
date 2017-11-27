/**
 * Created by pjoa09 on 10/24/17.
 */
public class Clicks {

    private Block firstClick;

    private Block secondClick;

    public boolean areClicksNextToEachOther() {
        if (howAreClicksArranged().equals("horizontal")){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean areClicksOnTopOfOneAnother() {
        return howAreClicksArranged().equals("vertical");
    }

    public String howAreClicksArranged(){

        boolean inSameRow = firstClick.getPositionInColumn()==secondClick.getPositionInColumn();
        boolean inSameColumn = firstClick.getColumnNumber()==secondClick.getColumnNumber();
        //they are in the same row and one apart
        boolean horizontal = inSameRow && Math.abs(firstClick.getColumnNumber()-secondClick.getColumnNumber())==1;
        //they are in the same column and one apart
        boolean vertical = inSameColumn && Math.abs(firstClick.getPositionInColumn()-secondClick.getPositionInColumn())==1;
        if (horizontal){
            return "horizontal";
        }
        else if (vertical){
            return "vertical";
        }
        else{
            return "I dunno";
        }
    }


    public Block getFirstClick() {
        return firstClick;
    }

    public void setFirstClick(Block firstClick) {
        this.firstClick = firstClick;
    }

    public Block getSecondClick() {
        return secondClick;
    }

    public void setSecondClick(Block secondClick) {
        this.secondClick = secondClick;
    }
}
