import re
import sys
from collections import Counter


def main():
    allSongs = []
    distinctSongsMap = {}

    try:
        file = open("universal_top_spotify_songs.csv", "r", encoding="utf-8")
        s = file.readlines()
        file.close()
        s_iter = iter(s)

        try:
            first_line = next(s_iter)
        except StopIteration:
            first_line = ""

        for line in s_iter:
            line = line.rstrip("\n")
            try:

                parts = re.split(r',(?=(?:[^"]*"[^"]*")*[^"]*$)', line)

                for i in range(len(parts)):
                    if "," not in parts[i]:
                        parts[i] = parts[i].replace('"', "")

                oneSong = Song(
                    parts[0].strip(),  # songId
                    parts[1].strip(),  # songName
                    parts[2].strip(),  # artist
                    int(parts[3].strip()),  # dailyRank
                    parts[6].strip(),  # country
                    parts[7].strip(),  # snapshotDate
                    int(parts[8].strip()),  # popularity
                    True if parts[9].strip().lower() == "true" else False,  # isExplicit
                    int(parts[10].strip()),  # duration
                    parts[12].strip(),  # albumReleaseDate
                    float(parts[13].strip()),  # danceability
                    int(parts[15].strip()),  # key
                    float(parts[16].strip()),  # loudness
                    int(parts[17].strip()),  # mode
                    float(parts[19].strip()),  # acousticness
                    float(parts[22].strip()),  # valence
                    float(parts[23].strip()),  # tempo
                    int(parts[24].strip()),  # timeSignature
                )

                allSongs.append(oneSong)

                if (
                    oneSong.getSongId() != "1GZnoLPpR9p2CwclsZnOXD"
                    and oneSong.getSongId() != "1DNXCTUHCGcr9kJ6LynYEC"
                    and oneSong.getSongId() != "10dCarem6DIkGBPEJoL6qv"
                    and oneSong.getSongId() != "5bDYi5F6QeZvjxzF6WUfea"
                ):

                    song_id = oneSong.getSongId()
                    if song_id not in distinctSongsMap:
                        existingSong = oneSong
                        existingSong.setNumOfDaysOnList(1)
                        existingSong.setAvgPopularity(float(oneSong.getPopularity()))
                        existingSong.setAvgDailyRank(float(oneSong.getDailyRank()))
                        distinctSongsMap[song_id] = existingSong
                    else:
                        existingSong = distinctSongsMap[song_id]
                        newNumOfDays = existingSong.getNumOfDaysOnList() + 1
                        existingSong.setNumOfDaysOnList(newNumOfDays)
                        existingSong.setAvgPopularity(
                            (
                                existingSong.getAvgPopularity() * (newNumOfDays - 1)
                                + oneSong.getPopularity()
                            )
                            / newNumOfDays
                        )
                        existingSong.setAvgDailyRank(
                            (
                                existingSong.getAvgDailyRank() * (newNumOfDays - 1)
                                + oneSong.getDailyRank()
                            )
                            / newNumOfDays
                        )
                        distinctSongsMap[song_id] = existingSong
            except ValueError as e:
                raise RuntimeError(e)
    except Exception as e:
        print(e)

    artist_counts = count_artist_appearances(allSongs)
    writeDistinctSongs(list(distinctSongsMap.values()), "filtered_songs.csv", artist_counts)

    distinctCount = len(distinctSongsMap)
    print("Number of distinct songs: " + str(distinctCount))

def count_artist_appearances(allSongs):
    artists = [artist.strip() for song in allSongs for artist in song.getArtist().split(",")]
    return Counter(artists)
    

def categorize_artist(artist_counts, artist):
    count = artist_counts.get(artist.strip(), 0)
    if count > 50:
        return "Very Popular"
    elif count > 20:
        return "Moderately Popular"
    else:
        return "Less Popular"    


def writeDistinctSongs(songs, file, artist_counts):
    try:
        fw = open(file, "w", encoding="utf-8")
        count = 0

        fw.write(
            "Artist,Times Appeared,Popularity,Explicit,Duration,Danceability,Loudness,Mode,Acousticness,Valence,Tempo,Time Signature\n"
        )
        for song in songs:
            if isValidSong(song):
                artist_category = categorize_artist(artist_counts, song.getArtist())
                fw.write(
                    "{},{},{:.6f},{},{},{},{},{},{},{},{},{}\n".format(
                        artist_category,
                        getNClass(song.getNumOfDaysOnList()),
                        song.getAvgPopularity(),
                        song.getExplicit(),
                        getDClass(song.getDuration()),
                        getDAVClass(song.getDanceability()),
                        getLClass(song.getLoudness()),
                        getMClass(song.getMode()),
                        getDAVClass(song.getAcousticness()),
                        getDAVClass(song.getValence()),
                        getTClass(song.getTempo()),
                        getTSClass(song.getTimeSignature()),
                    )
                )
                count += 1
        print("Total lines written: " + str(count))
        fw.close()
    except Exception as e:
        print(e)


def getTSClass(timeSignature):
    if timeSignature == 4:
        return "regular"
    else:
        return "irregular"


def getMClass(mode):
    if mode == 0:
        return "minor"
    else:
        return "major"


def getTClass(tempo):
    if tempo < 90:
        return "slow"
    elif tempo < 130:
        return "moderate"
    elif tempo < 180:
        return "fast"
    else:
        return "very fast"


def getLClass(loudness):
    if loudness < -25:
        return "very quiet"
    elif loudness < -15:
        return "quiet"
    elif loudness < -5:
        return "moderate"
    elif loudness < 0:
        return "loud"
    else:
        return "very loud"


def getDAVClass(n):
    if n < 0.3:
        return "low"
    elif n < 0.7:
        return "medium"
    else:
        return "high"


def getDClass(duration):
    if duration < 120000:
        return "very short"
    elif duration < 300000:
        return "short"
    elif duration < 540000:
        return "moderate"
    else:
        return "long"


def getNClass(numOfDaysOnList):
    if numOfDaysOnList <= 2000:
        return "very short"
    elif numOfDaysOnList <= 4000:
        return "short"
    elif numOfDaysOnList <= 6000:
        return "moderate"
    elif numOfDaysOnList <= 8000:
        return "long"
    else:
        return "very long"


def isValidSong(song):
    return (
        song.getSongId() != ""
        and song.getSongName() is not None
        and song.getSongName() != ""
        and song.getArtist() != ""
        and song.getNumOfDaysOnList() > 0
        and song.getAvgPopularity() > 0
        and song.getDuration() > 0
        and song.getAlbumReleaseDate() != ""
    )


class Song:
    def __init__(
        self,
        songId,
        songName,
        artist,
        dailyRank,
        country,
        snapshotDate,
        popularity,
        isExplicit,
        duration,
        albumReleaseDate,
        danceability,
        key,
        loudness,
        mode,
        acousticness,
        valence,
        tempo,
        timeSignature,
    ):
        self.songId = songId
        self.songName = songName
        self.artist = artist
        self.dailyRank = dailyRank
        self.country = country
        self.snapshotDate = snapshotDate
        self.popularity = popularity
        self.isExplicit = isExplicit
        self.duration = duration
        self.albumReleaseDate = albumReleaseDate
        self.danceability = danceability
        self.key = key
        self.loudness = loudness
        self.mode = mode
        self.acousticness = acousticness
        self.valence = valence
        self.tempo = tempo
        self.timeSignature = timeSignature

        self.numOfDaysOnList = 0
        self.avgPopularity = 0.0
        self.avgDailyRank = 0.0

    def getSongId(self):
        return self.songId

    def setSongId(self, songId):
        self.songId = songId

    def getSongName(self):
        return self.songName

    def setSongName(self, songName):
        self.songName = songName

    def getArtist(self):
        return self.artist

    def setArtist(self, artist):
        self.artist = artist

    def getExplicit(self):
        return self.isExplicit

    def setExplicit(self, explicit):
        self.isExplicit = explicit

    def getDuration(self):
        return self.duration

    def setDuration(self, duration):
        self.duration = duration

    def getDanceability(self):
        return self.danceability

    def setDanceability(self, danceability):
        self.danceability = danceability

    def getKey(self):
        return self.key

    def setKey(self, key):
        self.key = key

    def getLoudness(self):
        return self.loudness

    def setLoudness(self, loudness):
        self.loudness = loudness

    def getMode(self):
        return self.mode

    def setMode(self, mode):
        self.mode = mode

    def getAcousticness(self):
        return self.acousticness

    def setAcousticness(self, acousticness):
        self.acousticness = acousticness

    def getValence(self):
        return self.valence

    def setValence(self, valence):
        self.valence = valence

    def getTempo(self):
        return self.tempo

    def setTempo(self, tempo):
        self.tempo = tempo

    def getTimeSignature(self):
        return self.timeSignature

    def setTimeSignature(self, timeSignature):
        self.timeSignature = timeSignature

    def getAlbumReleaseDate(self):
        return self.albumReleaseDate

    def setAlbumReleaseDate(self, albumReleaseDate):
        self.albumReleaseDate = albumReleaseDate

    def getNumOfDaysOnList(self):
        return self.numOfDaysOnList

    def setNumOfDaysOnList(self, numOfDaysOnList):
        self.numOfDaysOnList = numOfDaysOnList

    def getAvgPopularity(self):
        return self.avgPopularity

    def setAvgPopularity(self, avgPopularity):
        self.avgPopularity = avgPopularity

    def getAvgDailyRank(self):
        return self.avgDailyRank

    def setAvgDailyRank(self, avgDailyRank):
        self.avgDailyRank = avgDailyRank

    def getCountry(self):
        return self.country

    def setCountry(self, country):
        self.country = country

    def getDailyRank(self):
        return self.dailyRank

    def setDailyRank(self, dailyRank):
        self.dailyRank = dailyRank

    def getPopularity(self):
        return self.popularity

    def setPopularity(self, popularity):
        self.popularity = popularity

    def getSnapshotDate(self):
        return self.snapshotDate


if __name__ == "__main__":
    main()
