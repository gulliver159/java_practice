package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;

public class RemoveSkillDtoRequest {
    private final String token;
    private final String titleRequirement;

    public RemoveSkillDtoRequest(String tokenJson, String titleRequirement) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.titleRequirement = titleRequirement;
    }

    public String getToken() {
        return token;
    }

    public String getTitleRequirement() {
        return titleRequirement;
    }
}
