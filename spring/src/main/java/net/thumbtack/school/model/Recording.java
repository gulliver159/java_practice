package net.thumbtack.school.model;

public class Recording {
    private String artist;
    private TrackType trackType;
    private String songTitle;
    private String albumName;
    private int manufactureYear;
    private String linkToCover;
    private String genre;
    private int duration;
    private String path;

    public Recording(String artist, TrackType trackType, String songTitle, String albumName, int manufactureYear,
                     String linkToCover, String genre, int duration, String path) {
        this.artist = artist;
        this.trackType = trackType;
        this.songTitle = songTitle;
        this.albumName = albumName;
        this.manufactureYear = manufactureYear;
        this.linkToCover = linkToCover;
        this.genre = genre;
        this.duration = duration;
        this.path = path;
    }

    public Recording(String artist, TrackType trackType, String songTitle) {
        this.artist = artist;
        this.trackType = trackType;
        this.songTitle = songTitle;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "artist='" + artist + '\'' +
                ", trackType=" + trackType +
                ", songTitle='" + songTitle + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
