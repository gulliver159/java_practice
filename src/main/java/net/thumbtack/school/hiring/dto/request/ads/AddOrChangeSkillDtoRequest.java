package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;
import net.thumbtack.school.hiring.model.ads.Requirement;

public class AddOrChangeSkillDtoRequest {
    private final String token;
    private final Requirement requirement;

    public AddOrChangeSkillDtoRequest(String tokenJson, Requirement requirement) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.requirement = requirement;
    }

    public String getToken() {
        return token;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}
