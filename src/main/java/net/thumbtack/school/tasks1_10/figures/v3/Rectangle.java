package net.thumbtack.school.tasks1_10.figures.v3;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorException;

import java.util.Objects;

public class Rectangle extends Figure {
    private Point2D leftTop, rightBottom;

    // Конструкторы
    public Rectangle(Point2D leftTop, Point2D rightBottom, Color color) throws ColorException {
        super(color);
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), color);
    }

    public Rectangle(int length, int width, Color color) throws ColorException {
        this(0, -width, length, 0, color);
    }

    public Rectangle(Color color) throws ColorException {
        this(0, -1, 1, 0, color);
    }

    public Rectangle(Point2D leftTop, Point2D rightBottom, String color) throws ColorException {
        this(leftTop, rightBottom, Color.colorFromString(color));
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, String color) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), color);
    }

    public Rectangle(int length, int width, String color) throws ColorException {
        this(0, -width, length, 0, color);
    }

    public Rectangle(String color) throws ColorException {
        this(0, -1, 1, 0, color);
    }

    // Геттеры и сеттеры
    public Point2D getTopLeft() {
        return leftTop;
    }

    public Point2D getBottomRight() {
        return rightBottom;
    }

    public void setTopLeft(Point2D topLeft) {
        this.leftTop = topLeft;
    }

    public void setBottomRight(Point2D bottomRight) {
        this.rightBottom = bottomRight;
    }

    public int getLength() {
        return rightBottom.getX() - leftTop.getX();
    }

    public int getWidth() {
        return rightBottom.getY() - leftTop.getY();
    }

    public double getArea() {
        return getLength() * getWidth();
    }

    public double getPerimeter() {
        return 2 * (getLength() + getWidth());
    }

    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    public void enlarge(int nx, int ny) {
        rightBottom.moveTo(getLength() * nx + leftTop.getX(), getWidth() * ny + leftTop.getY());
    }

    public boolean isInside(int x, int y) {
        return leftTop.getX() <= x && x <= rightBottom.getX() && leftTop.getY() <= y && y <= rightBottom.getY();
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    public boolean isIntersects(Rectangle rectangle) {
        boolean isIntersectsX = this.rightBottom.getX() >= rectangle.leftTop.getX()
                && this.leftTop.getX() <= rectangle.rightBottom.getX();
        boolean isIntersectsY = this.leftTop.getY() <= rectangle.rightBottom.getY()
                && this.rightBottom.getY() >= rectangle.leftTop.getY();
        return isIntersectsX && isIntersectsY;
    }

    public boolean isInside(Rectangle rectangle) {
        boolean isInsideX = this.leftTop.getX() <= rectangle.leftTop.getX()
                && this.rightBottom.getX() >= rectangle.rightBottom.getX();
        boolean isInsideY = this.leftTop.getY() <= rectangle.leftTop.getY()
                && this.rightBottom.getY() >= rectangle.rightBottom.getY();
        return isInsideX && isInsideY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(leftTop, rectangle.leftTop) &&
                Objects.equals(rightBottom, rectangle.rightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), leftTop, rightBottom);
    }
}
