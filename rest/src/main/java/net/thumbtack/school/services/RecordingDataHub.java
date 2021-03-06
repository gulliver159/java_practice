package net.thumbtack.school.services;

import net.thumbtack.school.dto.RecordingDto;
import net.thumbtack.school.storages.DataStorage;
import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecordingDataHub {
    DataStorage audioStorage;
    DataStorage videoStorage;

    public RecordingDataHub(@Qualifier("audioStorage") DataStorage audioStorage,
                            @Qualifier("videoStorage") DataStorage videoStorage) {
        this.audioStorage = audioStorage;
        this.videoStorage = videoStorage;
    }

    public String save(RecordingDto recordingDto) {
        Recording recording = new Recording(recordingDto.getArtist(), recordingDto.getTrackType(), recordingDto.getSongTitle(),
                recordingDto.getAlbumName(), recordingDto.getManufactureYear(), recordingDto.getLinkToCover(),
                recordingDto.getGenre(), recordingDto.getDuration(), recordingDto.getPathToAudio(), recordingDto.getPathToVideo());
        System.out.println(RecordingDataHub.class + " сохраняет файл " + recording);
        String path = UUID.randomUUID().toString();
        if (recording.getTrackType() == TrackType.AUDIO) {
            recording.setPathToAudio(path);
            return audioStorage.save(path);
        }
        else {
            recording.setPathToVideo(path);
            return videoStorage.save(path);
        }
    }
}
