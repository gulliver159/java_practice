package net.thumbtack.school.hiring.dto.response.ads;

import net.thumbtack.school.hiring.model.ads.Vacancy;

import java.util.List;

public class GetListVacanciesDtoResponse {

    private final List<Vacancy> vacancies;

    public GetListVacanciesDtoResponse(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }
}
