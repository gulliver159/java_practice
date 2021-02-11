package net.thumbtack.school.hiring.dto.response.ads;

import java.util.Set;

public class GetRequirementsDtoResponse {

    private final Set<String> requirementsTitles;

    public GetRequirementsDtoResponse(Set<String> requirementsTitles) {
        this.requirementsTitles = requirementsTitles;
    }

    public Set<String> getRequirementsTitles() {
        return requirementsTitles;
    }
}
