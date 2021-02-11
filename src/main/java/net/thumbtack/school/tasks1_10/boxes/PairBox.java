package net.thumbtack.school.tasks1_10.boxes;

import net.thumbtack.school.tasks1_10.area.HasArea;
import net.thumbtack.school.tasks1_10.figures.v3.Figure;

public class PairBox<T extends Figure, V extends Figure> implements HasArea {
    private T contentFirst;
    private V contentSecond;

    public PairBox(T contentFirst, V contentSecond) {
        this.contentFirst = contentFirst;
        this.contentSecond = contentSecond;
    }

    public T getContentFirst() {
        return contentFirst;
    }

    public void setContentFirst(T contentFirst) {
        this.contentFirst = contentFirst;
    }

    public V getContentSecond() {
        return contentSecond;
    }

    public void setContentSecond(V contentSecond) {
        this.contentSecond = contentSecond;
    }

    public double getArea() {
        return contentFirst.getArea() + contentSecond.getArea();
    }

    public boolean isAreaEqual(PairBox<? extends Figure, ? extends Figure> another) {
        return getArea() == another.getArea();
    }

    public static boolean isAreaEqual(PairBox<? extends Figure, ? extends Figure> box1,
                                      PairBox<? extends Figure, ? extends Figure> box2) {
        return box1.getArea() == box2.getArea();
    }
}
