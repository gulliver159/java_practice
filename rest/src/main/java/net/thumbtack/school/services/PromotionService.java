package net.thumbtack.school.services;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class PromotionService {
    public PromotionService() {
    }

    public int createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        System.out.println(PromotionService.class + " запустит рекламную кампанию композиции " + recording +
                " в " + campaignCreateDate.toString());
        return (int) Math.random() * 1000; // id рекламной компании
    }

    public void stopCampaign(Recording recording, int id, ZonedDateTime campaignCreateDate) {
        System.out.println(PromotionService.class + " остановит рекламную кампанию с id = " + id + " композиции " + recording +
                " в " + campaignCreateDate.toString());
    }

    public void removeCampaign(Recording recording, int id, ZonedDateTime campaignCreateDate) {
        System.out.println(PromotionService.class + " удалит рекламную кампанию с id = " + id + " композиции " + recording +
                " в " + campaignCreateDate.toString());
    }

    public String getInfoAboutCampaign(Recording recording, int id) {
        return "Информация о рекламной кампании с id = " + id + "композиции " + recording;
    }
}
