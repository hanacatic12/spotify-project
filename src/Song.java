public class Song {
    private String songId;
    private String songName;
    private String artist;
    private Integer dailyRank;

    private Integer maxDailyRank;
    private Integer minDailyRank;
    private Double avgDailyRank;

    private String country;
    private Integer popularity;

    private Double avgPopularity;

    private Boolean isExplicit;
    private Integer duration;
    private String albumReleaseDate;
    private Double danceability;
    private Integer key;
    private Double loudness;
    private Integer mode;
    private Double acousticness;
    private Double valence;
    private Double tempo;
    private Integer timeSignature;

    private Integer numOfDaysOnList;


    public Song(String songId, String songName, String artist, Integer dailyRank, String country, Integer popularity, Boolean isExplicit, Integer duration, String albumReleaseDate, Double danceability, Integer key, Double loudness, Integer mode, Double acousticness, Double valence, Double tempo, Integer timeSignature) {
        this.songId = songId;
        this.songName = songName;
        this.artist = artist;
        this.dailyRank = dailyRank;
        this.country = country;
        this.popularity = popularity;
        this.isExplicit = isExplicit;
        this.duration = duration;
        this.albumReleaseDate = albumReleaseDate;
        this.danceability = danceability;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.acousticness = acousticness;
        this.valence = valence;
        this.tempo = tempo;
        this.timeSignature = timeSignature;

        numOfDaysOnList = 0;
        avgPopularity = 0.;
        avgDailyRank = 0.;
        minDailyRank = Integer.MAX_VALUE;
        maxDailyRank = 0;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Boolean getExplicit() {
        return isExplicit;
    }

    public void setExplicit(Boolean explicit) {
        isExplicit = explicit;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getDanceability() {
        return danceability;
    }

    public void setDanceability(Double danceability) {
        this.danceability = danceability;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Double getLoudness() {
        return loudness;
    }

    public void setLoudness(Double loudness) {
        this.loudness = loudness;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Double getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(Double acousticness) {
        this.acousticness = acousticness;
    }

    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    public Integer getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
    }

    public Integer getNumOfDaysOnList() {
        return numOfDaysOnList;
    }

    public void setNumOfDaysOnList(Integer numOfDaysOnList) {
        this.numOfDaysOnList = numOfDaysOnList;
    }

    public Double getAvgPopularity() {
        return avgPopularity;
    }

    public void setAvgPopularity(Double avgPopularity) {
        this.avgPopularity = avgPopularity;
    }

    public Integer getMaxDailyRank() {
        return maxDailyRank;
    }

    public void setMaxDailyRank(Integer maxDailyRank) {
        this.maxDailyRank = maxDailyRank;
    }

    public Integer getMinDailyRank() {
        return minDailyRank;
    }

    public void setMinDailyRank(Integer minDailyRank) {
        this.minDailyRank = minDailyRank;
    }

    public Double getAvgDailyRank() {
        return avgDailyRank;
    }

    public void setAvgDailyRank(Double avgDailyRank) {
        this.avgDailyRank = avgDailyRank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDailyRank() {
        return dailyRank;
    }

    public void setDailyRank(Integer dailyRank) {
        this.dailyRank = dailyRank;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }
}
