package net.thumbtack.school.hiring.exception;

public enum ServerErrorCode {
    INVALID_FIRST_NAME("Передано невалидное имя"),
    INVALID_LAST_NAME("Передана невалидная фамилия"),
    INVALID_LOGIN("Передан невалидный логин"),
    INVALID_PASSWORD("Передан невалидный пароль"),
    INVALID_EMAIL("Передан невалидный e-mail"),
    INVALID_NAME_COMPANY("Передано невалидный название компании"),
    INVALID_ADDRESS("Передан невалидный адрес"),

    INVALID_JOB_TITLE("Передано невалидное название вакансии"),
    INVALID_SALARY("Передано невалидное значение зарплаты"),

    INVALID_TITLE_REQUIREMENT("Передано невалидное название требования"),
    INVALID_PROFICIENCY_LEVEL("Передан невалидный уровень владения"),

    VACANCY_NOT_FOUND("Вакансия с таким названием не была найдена"),
    SKILL_NOT_FOUND("Умение с таким названием не было найдено"),
    REQUIREMENT_NOT_FOUND("Требование с таким названием не было найдено"),

    BUSY_LOGIN("Пользователь с таким логином уже существует"),
    USER_NOT_FOUND("Пользователь с таким логином не был найден"),
    INCORRECT_PASSWORD("Введен неверный пароль"),
    EMPLOYEE_NOT_FOUND("Работник с таким логином не был найден не был найден"),

    TOKEN_NOT_FOUND("Пользователь с данным токеном не был найден");


    String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }
}
