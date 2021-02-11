package net.thumbtack.school.services;

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

    public String save(Recording recording) {
        System.out.println(RecordingDataHub.class + " сохраняет файл " + recording);
        String path = UUID.randomUUID().toString();
        recording.setPath(path);
        if (recording.getTrackType() == TrackType.AUDIO) {
            return audioStorage.save(path);
        }
        else {
            return videoStorage.save(path);
        }
    }
}
