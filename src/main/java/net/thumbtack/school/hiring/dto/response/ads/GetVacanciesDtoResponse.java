package net.thumbtack.school.hiring.dto.response.ads;

import net.thumbtack.school.hiring.model.ads.Vacancy;

import java.util.Set;

public class GetVacanciesDtoResponse {

    Set<Vacancy> vacancies;

    public GetVacanciesDtoResponse(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }
}
