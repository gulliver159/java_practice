package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;
import net.thumbtack.school.hiring.model.ads.Requirement;

public class AddOrChangeRequirementDtoRequest {
    private final String token;
    private final String jobTitle;
    private final Requirement requirement;

    public AddOrChangeRequirementDtoRequest(String tokenJson, String jobTitle, Requirement requirement) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.jobTitle = jobTitle;
        this.requirement = requirement;
    }

    public String getToken() {
        return token;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}
