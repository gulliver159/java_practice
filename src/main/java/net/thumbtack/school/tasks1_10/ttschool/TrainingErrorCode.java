package net.thumbtack.school.tasks1_10.ttschool;

public enum TrainingErrorCode {
    //Для класса Trainee
    TRAINEE_WRONG_FIRSTNAME("Передано недопустимое имя ученика"),
    TRAINEE_WRONG_LASTNAME("Передана недопустимая фамилия ученика"),
    TRAINEE_WRONG_RATING("Передана недопустимая оценка ученика"),

    //Для класса Group
    TRAINEE_NOT_FOUND("Ученик не найден"),
    GROUP_WRONG_NAME("Передано недопустимое имя группы"),
    GROUP_WRONG_ROOM("Передано недопустимое название аудитории"),

    //Для класса TraineeQueue
    EMPTY_TRAINEE_QUEUE("Очередь пуста"),

    //Для класса School
    SCHOOL_WRONG_NAME("Передано недопустимое название школы"),
    DUPLICATE_GROUP_NAME("Группа с таким названием уже существует"),
    GROUP_NOT_FOUND("Группа не найдена"),

    //Для класса TraineeMap
    DUPLICATE_TRAINEE("Информация о студенте уже присутствует");


    String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }
}
