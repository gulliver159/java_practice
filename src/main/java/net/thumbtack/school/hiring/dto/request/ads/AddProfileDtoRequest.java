package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;
import net.thumbtack.school.hiring.model.ads.*;

import java.util.Set;

public class AddProfileDtoRequest {
    private final String token;
    private final Set<Requirement> requirements;

    public AddProfileDtoRequest(String tokenJson, Set<Requirement> requirements) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.requirements = requirements;
    }

    public String getToken() {
        return token;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }
}
