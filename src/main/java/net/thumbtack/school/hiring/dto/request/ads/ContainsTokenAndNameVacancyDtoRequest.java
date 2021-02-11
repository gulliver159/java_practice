package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;

public class ContainsTokenAndNameVacancyDtoRequest {
    private final String token;
    private final String jobTitle;

    public ContainsTokenAndNameVacancyDtoRequest(String tokenJson, String jobTitle) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.jobTitle = jobTitle;
    }

    public String getToken() {
        return token;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
