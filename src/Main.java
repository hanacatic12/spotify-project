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

        ArrayList<Song> songsFinal = new ArrayList<>();
        File file = new File("C:\\Users\\HOME\\IdeaProjects\\spotify project\\src\\universal_top_spotify_songs.csv");
        try {
            Scanner s = new Scanner(file);
            if(s.hasNextLine()) s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try {
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].replace("\"", ""); // Remove all double quotes
                    }

                    Song oneSong = new Song(parts[0],
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            parts[6],
                            Integer.parseInt(parts[8]),
                            Boolean.parseBoolean(parts[9]),
                            Integer.parseInt(parts[10]),
                            parts[12],
                            Double.parseDouble(parts[13]),
                            Integer.parseInt(parts[15]),
                            Double.parseDouble(parts[16]),
                            Integer.parseInt(parts[17]),
                            Double.parseDouble(parts[19]),
                            Double.parseDouble(parts[22]),
                            Double.parseDouble(parts[23]),
                            Integer.parseInt(parts[24]));

                    allSongs.add(oneSong);

                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}