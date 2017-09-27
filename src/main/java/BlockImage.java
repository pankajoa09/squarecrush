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

import org.apache.commons.io.FilenameUtils;


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



    Image image;



    public void create(String name,Image image){
        this.name = name;
        this.image = image;
    }

    public void createRandom(){
        Random rand = new Random();
        ArrayList<BlockImage> listOfBlockImages = getBlockImagesFromDirectory();
        int n = rand.nextInt(listOfBlockImages.size());
        //this is horribly wrong but I see no two ways about it
        this.name =  listOfBlockImages.get(n).getName();
        this.image = listOfBlockImages.get(n).getImage();
    }


    private ArrayList<BlockImage> getBlockImagesFromDirectory() {
        File folder = new File("src/main/resources/");

        ArrayList<BlockImage> listOfBlockImages = new ArrayList<BlockImage>();
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile() && FilenameUtils.getExtension(listOfFiles[i].toString()).equals("png")) {
                Image image = new Image(getClass().getResource(listOfFiles[i].getName()).toExternalForm());
                BlockImage blockImage = new BlockImage();
                blockImage.create(listOfFiles[i].getName(),image);
                listOfBlockImages.add(blockImage);
            }

        }
        return listOfBlockImages;
    }




}
