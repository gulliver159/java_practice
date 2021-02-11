package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.response.ads.GetRequirementsDtoResponse;
import net.thumbtack.school.hiring.model.users.Employee;

import java.util.Set;

public class TemporaryUserOnServer {

    Gson gson = new Gson();
    private Server server;

    public String act(Employee employee) {
        String token = server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest(employee.getFirstName(), employee.getLastName(),
                employee.getPatronymic(), employee.getEmail(), employee.getLogin(), employee.getPassword())));
        if (token.contains("error")) {
            return token;
        }

        Set<String> existRequirements = gson.fromJson(server.getExistSkills(),
                GetRequirementsDtoResponse.class).getRequirementsTitles();

        server.leaveEmployee(token);

        return Integer.toString(existRequirements.size());
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
