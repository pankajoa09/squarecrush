/**
 * Created by pjoa09 on 9/26/17.
 */
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class BlockImage {
    private final String imageLocation = "BlockImages";

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<BlockImage> getListOfBlockImages() {
        return listOfBlockImages;
    }

    public void setListOfBlockImages(ArrayList<BlockImage> listOfBlockImages) {
        this.listOfBlockImages = listOfBlockImages;
    }

    Image image;
    private ArrayList<BlockImage> listOfBlockImages=setBlockImagesFromDirectory();


    public void create(String name,Image image){
        this.name = name;
        this.image = image;
    }

    public void createRandom(){
        Random rand = new Random();
        int n = rand.nextInt(listOfBlockImages.size());
        //this is horribly wrong but I see no two ways about it
        this.name =  listOfBlockImages.get(n).getName();
        this.image = listOfBlockImages.get(n).getImage();
    }


    private ArrayList<BlockImage> setBlockImagesFromDirectory() {
        File folder = new File("./"+imageLocation);
        ArrayList<BlockImage> listOfBlockImages = new ArrayList<BlockImage>();
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) { //too lazy to consider extensions yet to identify images
                System.out.println("File " + listOfFiles[i].getName());
                String imageName = listOfFiles[i].getName();
                Image image = new Image(getClass().getResource(imageName).toExternalForm());
                BlockImage blockImage = new BlockImage();
                blockImage.create(imageName,image);
                listOfBlockImages.add(blockImage);
            }
        }
        return listOfBlockImages;
    }




}
