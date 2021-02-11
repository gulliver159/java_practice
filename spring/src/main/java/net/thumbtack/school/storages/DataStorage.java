package net.thumbtack.school.storages;

import org.springframework.stereotype.Component;

@Component
public interface DataStorage {
    String save(String path);
}
