package net.thumbtack.school.hiring.model.users;

import net.thumbtack.school.hiring.model.ads.Profile;

public class Employee extends User {

    private Profile profile;

    public Employee(String firstName, String lastName, String patronymic, String email, String login, String password) {
        super(firstName, lastName, patronymic, email, login, password);
    }

    public Employee(String firstName, String lastName, String email, String login, String password) {
        super(firstName, lastName, email, login, password);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }


}
