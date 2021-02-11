package net.thumbtack.school.storages;

import org.springframework.stereotype.Component;

@Component("audioStorage")
public class AudioRecordingStorage implements DataStorage {

    public String save(String path) {
        System.out.println(AudioRecordingStorage.class + " сохраняет файл со ссылкой " + path);
        return path;
    }

    public AudioRecordingStorage() {
    }
}
