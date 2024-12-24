import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ArrayList<Song> allSongs = new ArrayList<>();

        ArrayList<Song> records = new ArrayList<>();
        File file = new File("./universal_top_spotify_songs.csv");
        try {
            Scanner scanner = new Scanner(file);
        } catch () {

        }
    }
}