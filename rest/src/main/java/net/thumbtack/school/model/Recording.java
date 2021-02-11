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
    private String pathToAudio;
    private String pathToVideo;

    public Recording(String artist, TrackType trackType, String songTitle, String albumName, int manufactureYear,
                     String linkToCover, String genre, int duration, String pathToAudio, String pathToVideo) {
        this.artist = artist;
        this.trackType = trackType;
        this.songTitle = songTitle;
        this.albumName = albumName;
        this.manufactureYear = manufactureYear;
        this.linkToCover = linkToCover;
        this.genre = genre;
        this.duration = duration;
        this.pathToAudio = pathToAudio;
        this.pathToVideo = pathToVideo;
    }

    public Recording(String artist, TrackType trackType, String songTitle, int manufactureYear, String genre, int duration,
                     String pathToAudio, String pathToVideo) {
        this.artist = artist;
        this.trackType = trackType;
        this.songTitle = songTitle;
        this.manufactureYear = manufactureYear;
        this.genre = genre;
        this.duration = duration;
        this.pathToAudio = pathToAudio;
        this.pathToVideo = pathToVideo;
    }

    public String getPathToAudio() {
        return pathToAudio;
    }

    public String getPathToVideo() {
        return pathToVideo;
    }

    public void setPathToAudio(String pathToAudio) {
        this.pathToAudio = pathToAudio;
    }

    public void setPathToVideo(String pathToVideo) {
        this.pathToVideo = pathToVideo;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "artist='" + artist + '\'' +
                ", trackType=" + trackType +
                ", songTitle='" + songTitle + '\'' +
                ", manufactureYear=" + manufactureYear +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", pathToAudio='" + pathToAudio + '\'' +
                ", pathToVideo='" + pathToVideo + '\'' +
                '}';
    }
}
