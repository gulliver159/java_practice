package net.thumbtack.school.hiring.dto.request.users;

import com.google.gson.Gson;

public class HiringEmployeeDtoRequest {
    private final String token;
    private final String jobTitle;
    private final String loginEmployee;

    public HiringEmployeeDtoRequest(String tokenJson, String jobTitle, String loginEmployee) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.jobTitle = jobTitle;
        this.loginEmployee = loginEmployee;
    }

    public String getToken() {
        return token;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLoginEmployee() {
        return loginEmployee;
    }
}
