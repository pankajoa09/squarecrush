/**
 * Created by pjoa09 on 10/10/17.
 */
public class Event {

    Debug debug = new Debug();
    Engine engine = new Engine();
    Service service = new Service();

    private static Block firstClick;


    public Animated rectangleBlockClickHandler(int columnNumber, int positionInColumn, RowOfColumns rowOfColumns){
        Block clicked = rowOfColumns.getBlock(columnNumber,positionInColumn);
        Animated animated = new Animated();
        if (isSecondClick(clicked)){
            Block firstClick = getFirstClick();
            Block secondClick = clicked;
            animated = service.updateAnimated(firstClick,secondClick,rowOfColumns);
//            Animated animated = service.updateAnimated(firstClick,secondClick);
        }
        else{
           storeFirstClick(clicked);
        }
        return animated;
    }

    private Boolean isSecondClick(Block block){
        Boolean answer = false;
        if (!firstClick.equals(null)){
            answer = true;
        }
        return answer;
    }

    private void storeFirstClick(Block clicked){
        firstClick = clicked;
    }

    private Block getFirstClick(){
        return firstClick;
    }






}
