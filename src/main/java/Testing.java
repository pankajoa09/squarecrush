import com.sun.rowset.internal.Row;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by pjoa09 on 11/6/17.
 */
public class Testing {

    Service service = new Service();
    ServiceColumn serviceColumn = new ServiceColumn();
    Debug debug = new Debug();
    BoardFactory boardFactory = new BoardFactory();


    private ColumnOfBlocks createColumn(){
        ColumnOfBlocks testCol = new ColumnOfBlocks();
        System.out.println("----");
        for (int i = 0; i < 5; i++){
            Block block = new Block();
            block.createRandomBlock(i,0,2);
            debug.printBlock(block);
            testCol.addBlock(block);
        }
        System.out.println("---");
        return testCol;
    }



    public void testApplyGravityToColumnOfBlocks(){
        ColumnOfBlocks testCol = createColumn();
        Block toRemove1 = testCol.getBlock(0);
        Block toRemove2 = testCol.getBlock(1);
        Block toRemove3 = testCol.getBlock(2);
        Block toRemove4 = testCol.getBlock(3);
        Block toRemove5 = testCol.getBlock(4);
        testCol.removeBlock(toRemove1);
        testCol.removeBlock(toRemove5);
        testCol.removeBlock(toRemove4);
        testCol = serviceColumn.applyGravityToColumnOfBlocks(testCol);
//        for (Block block: testCol.getContainingBlocks()){
//            debug.printBlock(block);
        //}
        for (int i = 0; i<5;i++){
            debug.printBlock(testCol.getBlock(i));
        }

    }

    public void testApplyCrunch(){
        ColumnOfBlocks testCol = createColumn();


        Block toRemove1 = testCol.getBlock(0);
        Block toRemove2 = testCol.getBlock(1);
        Block toRemove3 = testCol.getBlock(2);
        Block toRemove4 = testCol.getBlock(3);
        Block toRemove5 = testCol.getBlock(4);
        testCol.removeBlock(toRemove2);
        testCol.removeBlock(toRemove4);
        testCol = serviceColumn.applyCrunchToColumnOfBlocks(testCol);
        for (Block block: testCol.getContainingBlocks()){
            debug.printBlock(block);
        }


    }

    public void testShiftDownBlocks(){
        ColumnOfBlocks testCol = createColumn();
        ArrayList<Block> tshft = serviceColumn.shiftDownBlocks(testCol.getContainingBlocks(),1);
        for (Block blk: tshft){
            debug.printBlock(blk);
        }
    }

    public void testAddingToColumn(){
        ColumnOfBlocks testCol = createColumn();
        Block tor = testCol.getBlock(4);
        Block toa = new Block();
        toa.createNullBlock(4,0);
        testCol.removeBlock(tor);
        testCol.addBlock(toa);
        System.out.println("post:");
        for (Block block : testCol.getContainingBlocks()){
            debug.printBlock(block);
        }

    }



    public void testThrees(){
        for (int i = 0; i<20;i++) {
            ColumnOfBlocks testCol = createColumn();
            ArrayList<Block> result = service.threeOrMore(testCol.getContainingBlocks());

            System.out.println("----res--");
            for (Block res : result) {
                debug.printBlock(res);
            }
            System.out.println("--------");

        }
    }


    public void testGetBlocksToDestroy(){
        RowOfColumns testRow = boardFactory.createRowOfColumns();
        ArrayList<Block> dest = service.getBlocksToDestroyHorizontal(testRow);

        debug.printArrayInRowOfColumns(dest, testRow);

    }

    public void testDestroyBlocks(){
        RowOfColumns testRow = boardFactory.createRowOfColumns();
        ArrayList<Block> dest = service.getBlocksToDestroy(testRow);
        debug.printArrayInRowOfColumns(dest,testRow);
        testRow = service.destroyBlocks(testRow,dest);
        debug.printArrayInRowOfColumns(dest,testRow);

    }

    public void testReplaceBlocks(){
        RowOfColumns testRow = boardFactory.createRowOfColumns();
        ArrayList<Block> dest = service.getBlocksToDestroy(testRow);
        debug.printArrayInRowOfColumns(dest,testRow);
        testRow = service.destroyBlocks(testRow,dest);
        debug.printArrayInRowOfColumns(dest,testRow);
        testRow = service.applyGravityToRowOfColumns(testRow);
        debug.printRowOfColumns(testRow);
        //System.out.println("----000000---");
        for (ColumnOfBlocks columnOfBlocks: testRow.getContainingColumns()){
            for (Block block: columnOfBlocks.getContainingBlocks()){
          //      debug.printBlock(block);
            }
            //System.out.println("-----");

        }
        System.out.println("replacement");
        ArrayList<Block> repl = service.createReplacementBlocks(testRow);
        for (Block block : repl){
            debug.printBlock(block);
        }
        testRow = service.addReplacementBlocksToRowOfColumns(repl,testRow);
        debug.printRowOfColumns(testRow);

    }

    public void testGetBlock(){
        ColumnOfBlocks testCol = createColumn();
        testCol.removeBlock(testCol.getBlock(0));
        testCol.removeBlock(testCol.getBlock(4));

        ArrayList<Block> results = new ArrayList<>();
        for (int i = 0; i< 5; i++ ){
            System.out.println("GETTING BLOCK AT "+i);
            results.add(testCol.getBlock(i));
        }
        System.out.println("results:");
        for (Block block: results){
            debug.printBlock(block);
        }
    }

    public void testGetToFall(){
        RowOfColumns testCol = boardFactory.createRowOfColumns();
        ArrayList<Block> toDest = service.getBlocksToDestroy(testCol);
        testCol = service.destroyBlocks(testCol,toDest);
        testCol = service.applyGravityToRowOfColumns(testCol);


    }

    public void testClone(){
        RowOfColumns orig = boardFactory.createRowOfColumns();
        RowOfColumns fake = boardFactory.createRowOfColumnsClone(orig);
        ArrayList<Block> dest = service.getBlocksToDestroy(fake);
        fake = service.destroyBlocks(fake,dest);
        ArrayList<Block> repl = service.createReplacementBlocks(fake);
        fake = service.addReplacementBlocksToRowOfColumns(repl,fake);
        debug.printRowOfColumns(orig);
        debug.printRowOfColumns(fake);
        System.out.println(service.didItChange(orig,fake));


    }

    public void testGetToFallNew(){
        ColumnOfBlocks testCol = createColumn();

        Block block0 = testCol.getBlock(0);
        Block block1 = testCol.getBlock(1);
        Block block2 = testCol.getBlock(2);
        Block block3 = testCol.getBlock(3);
        Block block4 = testCol.getBlock(4);


        testCol.removeBlock(block0);
        testCol.removeBlock(block1);
        //testCol.removeBlock(block2);
        //testCol.removeBlock(block3);
        testCol.removeBlock(block4);


        ArrayList<Block> fallers = serviceColumn.getFallingBlocks(testCol);
        for (Block block: fallers){
            System.out.print(block.getShiftDown()+ " ");
            debug.printBlock(block);
        }
        System.out.println("----");
        testCol = serviceColumn.dropBlocksInToColumn(fallers,testCol);
        for (Block blk : testCol.getContainingBlocks()){
            debug.printBlock(blk);
        }

    }

    public void testFall(){
        RowOfColumns rowOfColumns =  boardFactory.createRowOfColumns();
        ArrayList<Block> dest = service.getBlocksToDestroy(rowOfColumns);
        System.out.println("destroy:");
        debug.printArrayInRowOfColumns(dest,rowOfColumns);
        rowOfColumns = service.destroyBlocks(rowOfColumns,dest);
        System.out.println("destroyed:");
        debug.printRowOfColumns(rowOfColumns);
        ArrayList<Block> fallers = service.getFallingBlocks(rowOfColumns);
        System.out.println("fallers:");
        debug.printArrayInRowOfColumns(fallers,rowOfColumns);
        for (Block block: fallers){
            //System.out.print(block.getShiftDown()+ " ");
            //debug.printBlock(block);
        }
        rowOfColumns = service.dropBlocksInToRowOfColumns(fallers,rowOfColumns);
        System.out.println("postfall:");
        debug.printRowOfColumns(rowOfColumns);




    }








}
