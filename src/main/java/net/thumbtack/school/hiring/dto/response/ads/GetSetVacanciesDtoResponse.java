package net.thumbtack.school.hiring.dto.response.ads;

import net.thumbtack.school.hiring.model.ads.Vacancy;

import java.util.Set;

public class GetSetVacanciesDtoResponse {

    private final Set<Vacancy> vacancies;

    public GetSetVacanciesDtoResponse(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }
}
