package net.thumbtack.school.tasks1_10.boxes;

import net.thumbtack.school.tasks1_10.figures.v3.Figure;

public class ArrayBox<T extends Figure> {
    private T[] content;

    public ArrayBox(T[] content) {
        this.content = content;
    }

    public T[] getContent() {
        return content;
    }

    public void setContent(T[] content) {
        this.content = content;
    }

    public T getElement(int i) {
        return content[i];
    }

    public void setElement(int i, T value) {
        this.content[i] = value;
    }

    public boolean isSameSize(ArrayBox<? extends Figure> another) {
        return content.length == another.content.length;
    }
}
