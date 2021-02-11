package net.thumbtack.school.hiring.dto.request.users;

import com.google.gson.Gson;

public class ContainsTokenDtoRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public ContainsTokenDtoRequest(String tokenJson) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
    }

    //for tests
    public ContainsTokenDtoRequest(int token) {
        this.token = String.valueOf(token);
    }
}
