package net.thumbtack.school.hiring.dto.request.users;

import com.google.gson.Gson;

public class ChangeRegistrationDataDtoRequest {
    private final String token;
    private String companyName, address;
    private final String firstName, lastName;
    private final String patronymic;
    private final String email;
    private final String password;

    public ChangeRegistrationDataDtoRequest(String tokenJson, String companyName, String address, String firstName, String lastName,
                                            String patronymic, String email, String password) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.companyName = companyName;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
    }

    public ChangeRegistrationDataDtoRequest(String tokenJson, String firstName, String lastName, String patronymic,
                                            String email, String password) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
    }

    public String getToken() {
        return token;
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

    public String getPassword() {
        return password;
    }
}
