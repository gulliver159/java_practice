package net.thumbtack.school.tasks1_10.colors;

public interface Colored {

    void setColor(Color color) throws ColorException;

    void setColor(String colorString) throws ColorException;

    Color getColor();

}
