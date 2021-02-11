package net.thumbtack.school.hiring.dto.request.users;

public class RegisterEmployeeDtoRequest {
    private final String firstName, lastName;
    private String patronymic;
    private final String email;
    private final String login, password;

    public RegisterEmployeeDtoRequest(String firstName, String lastName, String patronymic, String email, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public RegisterEmployeeDtoRequest(String firstName, String lastName, String email, String login, String password) {
    	this(firstName, lastName, null, email, login, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
