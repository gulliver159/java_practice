package net.thumbtack.school.hiring.dto.request.users;

public class LoginDtoRequest {
    private final String login, password;

    public LoginDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
