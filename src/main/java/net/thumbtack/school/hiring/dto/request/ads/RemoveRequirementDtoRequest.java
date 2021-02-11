package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;

public class RemoveRequirementDtoRequest {
    private final String token;
    private final String jobTitle;
    private final String titleRequirement;

    public RemoveRequirementDtoRequest(String tokenJson, String jobTitle, String titleRequirement) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.jobTitle = jobTitle;
        this.titleRequirement = titleRequirement;
    }

    public String getToken() {
        return token;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getTitleRequirement() {
        return titleRequirement;
    }
}
