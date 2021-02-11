package net.thumbtack.school.tasks1_10.boxes;

import net.thumbtack.school.tasks1_10.area.HasArea;
import net.thumbtack.school.tasks1_10.figures.v3.Figure;

public class Box<T extends Figure> implements HasArea {
    private static final double EPS = 1E-6;

    private T content;

    public Box(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public double getArea() {
        return content.getArea();
    }

    public boolean isAreaEqual(Box<? extends Figure> another) {
        return Math.abs(getArea() - another.getArea()) < EPS;
    }

    public static boolean isAreaEqual(Box<? extends Figure> box1, Box<? extends Figure> box2) {
        return Math.abs(box1.getArea() - box2.getArea()) < EPS;
    }
}
