import java.util.Arrays;

/**
 * Created by pjoa09 on 10/30/17.
 */
public enum Elvis {
    INSTANCE;
    private String favoriteSongs = "hi";

    public void printFavorites() {
        System.out.println(favoriteSongs);
    }

    public void changeFav(String string){
        favoriteSongs = string;
    }





}