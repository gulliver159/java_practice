package net.thumbtack.school.hiring.dto.response.users;

public class LoginOrRegisterDtoResponse {
    private final String token;

    public LoginOrRegisterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
