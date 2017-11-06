import java.util.ArrayList;

/**
 * Created by pjoa09 on 11/6/17.
 */
public class Testing {

    Service service = new Service();
    ServiceColumn serviceColumn = new ServiceColumn();
    Debug debug = new Debug();


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



        //testCol.removeBlock(toRemove);

        testCol.removeBlock(toRemove5);

        serviceColumn.applyGravityToColumnOfBlocks(testCol);

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

    public void testThreesARow(){
        for (int i = 0; i<20;i++) {
            ColumnOfBlocks testCol = createColumn();
            ArrayList<Block> result = service.threeOrMoreInAColumn(testCol);
            System.out.println("----res--");
            for (Block res : result) {
                debug.printBlock(res);
            }
            System.out.println("--------");
        }
    }


}
