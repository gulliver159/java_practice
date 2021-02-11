package net.thumbtack.school.dto;

import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.validators.PathsOfRecording;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@PathsOfRecording
public class RecordingDto {
    @NotNull
    private String artist;

    @NotNull
    private TrackType trackType;

    @NotNull
    private String songTitle;

    private String albumName;

    @NotNull
    @Min(1970)
    private int manufactureYear;

    private String linkToCover;

    @NotNull
    private String genre;

    @NotNull
    private int duration;

    private String pathToAudio;
    private String pathToVideo;

    public RecordingDto() {
    }

    public RecordingDto(@NotNull String artist, @NotNull TrackType trackType, @NotNull String songTitle,
                        @NotNull @Min(1970) int manufactureYear, @NotNull String genre, @NotNull int duration,
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public void setTrackType(TrackType trackType) {
        this.trackType = trackType;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getLinkToCover() {
        return linkToCover;
    }

    public void setLinkToCover(String linkToCover) {
        this.linkToCover = linkToCover;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPathToAudio() {
        return pathToAudio;
    }

    public void setPathToAudio(String pathToAudio) {
        this.pathToAudio = pathToAudio;
    }

    public String getPathToVideo() {
        return pathToVideo;
    }

    public void setPathToVideo(String pathToVideo) {
        this.pathToVideo = pathToVideo;
    }

    @Override
    public String toString() {
        return "RecordingDto{" +
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
