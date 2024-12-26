import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ArrayList<Song> allSongs = new ArrayList<>();
        Map<String, Song> distinctSongsMap = new HashMap<>();

        File file = new File("src/universal_top_spotify_songs.csv");
        try {
            Scanner s = new Scanner(file);
            if(s.hasNextLine()) s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try {
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    for (int i = 0; i < parts.length; i++) {
                        // Remove surrounding quotes if there is no comma inside the field
                        if (!parts[i].contains(",")) {
                            parts[i] = parts[i].replace("\"", "");
                        }
                    }

                    Song oneSong = new Song(parts[0].trim(), // songId
                            parts[1].trim(), // songName
                            parts[2].trim(), // artist
                            Integer.parseInt(parts[3].trim()), // dailyRank
                            parts[6].trim(), // country
                            parts[7].trim(), // snapshotDate
                            Integer.parseInt(parts[8].trim()), // popularity
                            Boolean.parseBoolean(parts[9].trim()), // isExplicit
                            Integer.parseInt(parts[10].trim()), // duration
                            parts[12].trim(), // albumReleaseDate
                            Double.parseDouble(parts[13].trim()), // danceability
                            Integer.parseInt(parts[15].trim()), // key
                            Double.parseDouble(parts[16].trim()), // loudness
                            Integer.parseInt(parts[17].trim()), // mode
                            Double.parseDouble(parts[19].trim()), // acousticness
                            Double.parseDouble(parts[22].trim()),// valence
                            Double.parseDouble(parts[23].trim()), // tempo
                            Integer.parseInt(parts[24].trim())); // timeSignature

                    allSongs.add(oneSong);

                    if (!"1GZnoLPpR9p2CwclsZnOXD".equals(oneSong.getSongId()) &&
                        !"1DNXCTUHCGcr9kJ6LynYEC".equals(oneSong.getSongId())) {
                        distinctSongsMap.compute(oneSong.getSongId(), (id, existingSong) -> {
                            if (existingSong == null) {
                                // First time seeing this song
                                existingSong = oneSong;
                                existingSong.setNumOfDaysOnList(1);
                                existingSong.setAvgPopularity(oneSong.getPopularity().doubleValue());
                                existingSong.setAvgDailyRank(oneSong.getDailyRank().doubleValue());
                            } else {
                                // Update aggregated values
                                existingSong.setNumOfDaysOnList(existingSong.getNumOfDaysOnList() + 1);
                                existingSong.setAvgPopularity(
                                        (existingSong.getAvgPopularity() * (existingSong.getNumOfDaysOnList() - 1) + oneSong.getPopularity())
                                                / existingSong.getNumOfDaysOnList());
                                existingSong.setAvgDailyRank(
                                        (existingSong.getAvgDailyRank() * (existingSong.getNumOfDaysOnList() - 1) + oneSong.getDailyRank())
                                                / existingSong.getNumOfDaysOnList());
                            }
                            return existingSong;
                        });
                    }


                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        writeDistinctSongs(new ArrayList<>(distinctSongsMap.values()), "filtered_songs.csv");


        int distinctCount = distinctSongsMap.size();
        System.out.println("Number of distinct songs: " + distinctCount);
    }

    private static void writeDistinctSongs(ArrayList<Song> songs, String file) throws IOException {
        FileWriter fw = new FileWriter(file);
        int count = 0;

        fw.write("Song ID,Song Name,Artist,Num Of Days On List,Avg Popularity,Explicit,Duration,Album Release Date,Danceability,Key,Loudness,Mode,Acousticness,Valence,Tempo,Time Signature\n");

        for (Song song : songs) {
            if (isValidSong(song)) {
                fw.write(String.format("%s,%s,%s,%f,%s,%b,%f,%s,%f,%f,%f,%f,%f,%f,%f,%f\n",
                    song.getSongId(),                   // Song ID
                    song.getSongName(),                 // Song Name
                    song.getArtist(),                   // Artist
                    song.getNumOfDaysOnList() / 10000.0,          // Num Of Days On List
                    getClass(song.getAvgPopularity()),  // Popularity
                    song.getExplicit(),                 // Explicit (boolean)
                    song.getDuration() / 6000000.0,                 // Duration
                    song.getAlbumReleaseDate(),         // Album Release Date
                    song.getDanceability(),             // Danceability
                    song.getKey() / 100.0,                      // Key
                    song.getLoudness(),                 // Loudness
                    song.getMode() / 10.0,                     // Mode
                    song.getAcousticness(),             // Acousticness
                    song.getValence(),                  // Valence
                    song.getTempo() / 1000.0,                    // Tempo
                    song.getTimeSignature() / 10.0             // Time Signature
                ));
                count++;

            }

        }
        System.out.println("Total lines written: " + (count));
        fw.close();
    }

    private static String getClass(Double avgPopularity) {
        if (avgPopularity < 40) return "not popular";
        else if (avgPopularity < 70) return "somewhat popular";
        else if (avgPopularity < 90) return "popular";
        else return "very popular";
    }

    private static boolean isValidSong(Song song) {
        return  !song.getSongId().isEmpty() &&
                song.getSongName() != null &&
                !song.getSongName().isEmpty() &&
                !song.getArtist().isEmpty() &&
                song.getNumOfDaysOnList() > 0 &&
                song.getAvgPopularity() > 0 &&
                song.getDuration() > 0 &&
                !song.getAlbumReleaseDate().isEmpty();
    }

}