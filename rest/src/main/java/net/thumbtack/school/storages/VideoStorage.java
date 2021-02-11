package net.thumbtack.school.storages;

import org.springframework.stereotype.Component;

@Component("videoStorage")
public class VideoStorage implements DataStorage {

    public String save(String path) {
        System.out.println(VideoStorage.class + " сохраняет файл со ссылкой " + path);
        return path;
    }

    public VideoStorage() {
    }
}
