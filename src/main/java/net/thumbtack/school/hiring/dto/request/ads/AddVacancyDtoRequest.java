package net.thumbtack.school.hiring.dto.request.ads;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;
import net.thumbtack.school.hiring.model.ads.*;

import java.util.Set;

public class AddVacancyDtoRequest {
    private final String token;
    private final String jobTitle;
    private final int salary;
    private final Set<Requirement> requirements;

    public AddVacancyDtoRequest(String tokenJson, String jobTitle, int salary, Set<Requirement> requirements) {
        Gson gson = new Gson();
        this.token = gson.fromJson(tokenJson, ContainsTokenDtoRequest.class).getToken();
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.requirements = requirements;
    }

    public String getToken() {
        return token;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }
}
