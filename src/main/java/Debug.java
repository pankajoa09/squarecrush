


import java.util.ArrayList;

/**
 * Created by pjoa09 on 9/27/17.
 */
public class Debug {






    public void printRowOfColumns(RowOfColumns rowOfColumns){
        System.out.println("-----ROWOFCOLUMNS----");
        ArrayList<Block> fake = new ArrayList<>();
        printArrayInRowOfColumns(fake,rowOfColumns);
        System.out.println("-+-+-+-+-+-+-+-+-+-+");
    }







    public void printArrayInRowOfColumns(ArrayList<Block> targetBlocks,RowOfColumns rowOfColumns){
        System.out.println("+++++++++++++++");
        for (int pos=0;pos<rowOfColumns.size();pos++){
            for (int col=0;col<rowOfColumns.size();col++){
                ColumnOfBlocks columnOfBlocks = rowOfColumns.getColumnOfBlocks(col);
                Block block = columnOfBlocks.getBlock(pos);


                try {
                    if (targetBlocks.contains(block)) {
                        System.out.print(block.getBlockImage().getName().substring(0, 2).toUpperCase() + " ");
                    } else {
                        //System.out.print(block.getPositionInColumn() + "."+block.getColumnNumber()+" ");
                        //System.out.print(block.getBlockImage().getName());
                        System.out.print(block.getBlockImage().getName().substring(0, 2) + " ");
                    }
                }
                catch (NullPointerException npe){
                    System.out.print("n  ");
                }
            }
            System.out.println("");
        }

        System.out.println("++++++++++++");
    }


    //

    public void printBlock(Block block){
        System.out.println(block.getBlockImage().getName()+" pos:"+block.getPositionInColumn()+" col:"+block.getColumnNumber());
    }






}
