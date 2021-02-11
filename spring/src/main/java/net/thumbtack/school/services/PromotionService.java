package net.thumbtack.school.services;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class PromotionService {
    public PromotionService() {
    }

    public void createCampaign(Recording recording, ZonedDateTime campaignCreateDate) {
        System.out.println(PromotionService.class + " запустит рекламную кампанию композиции " + recording +
                " в " + campaignCreateDate.toString());
    }
}
