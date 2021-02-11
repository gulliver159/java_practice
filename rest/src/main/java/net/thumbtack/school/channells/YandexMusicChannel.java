package net.thumbtack.school.channells;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component("yandexChannel")
public class YandexMusicChannel implements PublishingChannel {
    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        System.out.println(YandexMusicChannel.class + " опубликует композицию " + recording
                + " в " + publishAvailableDate.toString());
    }

    @Override
    public void remove(Recording recording, ZonedDateTime publishAvailableDate) {
        System.out.println(YandexMusicChannel.class + " удалит композицию " + recording
                +  " из канала в " + publishAvailableDate.toString());
    }
}
