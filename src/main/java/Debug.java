/**
 * Created by pjoa09 on 9/27/17.
 */
public class Debug {

    public void printRowOfColumns(RowOfColumns rowOfColumns){
        for (ColumnOfBlocks columnOfBlocks: rowOfColumns.getContainingColumns()){
            for(Block block: columnOfBlocks.getContainingBlocks()){
                System.out.print(block.getBlockImage().getName().charAt(0)+" ");
            }
            System.out.println("");
        }
    }


}
