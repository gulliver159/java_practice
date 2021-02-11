package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;

public class MarkAndRemoveVacancyDtoRequest {
    private final String token, jobTitle;

    public MarkAndRemoveVacancyDtoRequest(String tokenJson, String jobTitle) {
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
