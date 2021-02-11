package net.thumbtack.school.tasks1_10.figures.v2;

import java.util.Objects;

public class Circle extends Figure {

    private int radius;
    private Point2D center;

    //Конструкторы

    public Circle(Point2D center, int radius, int color) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius, int color) {
        this(new Point2D(xCenter, yCenter), radius, color);
    }

    public Circle(int radius, int color) {
        this(0, 0, radius, color);
    }

    public Circle(int color) {
        this(0, 0, 1, color);
    }

    public Point2D getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

    public void enlarge(int n) {
        radius *= n;
    }

    public boolean isInside(int x, int y) {
        return Math.pow((x - center.getX()), 2) + Math.pow((y - center.getY()), 2) <= Math.pow(radius, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius &&
                Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius, center);
    }
}
