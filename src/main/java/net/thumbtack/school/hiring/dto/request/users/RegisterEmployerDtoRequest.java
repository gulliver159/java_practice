package net.thumbtack.school.hiring.dto.request.users;

public class RegisterEmployerDtoRequest {
    private final String companyName, address;
    private final String firstName, lastName;
    private final String patronymic;
    private final String email;
    private final String login, password;

    public RegisterEmployerDtoRequest(String companyName, String address, String firstName, String lastName,
                                      String patronymic, String email, String login, String password) {
        this.companyName = companyName;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public RegisterEmployerDtoRequest(String companyName, String address, String firstName, String lastName,
                                      String email, String login, String password) {
        this(companyName, address, firstName, lastName, null, email, login, password);
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
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
