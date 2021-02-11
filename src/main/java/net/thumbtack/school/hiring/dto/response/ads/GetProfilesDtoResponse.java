package net.thumbtack.school.hiring.dto.response.ads;

import net.thumbtack.school.hiring.model.ads.Profile;

import java.util.Set;

public class GetProfilesDtoResponse {

    private final Set<Profile> profiles;

    public GetProfilesDtoResponse(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }
}
