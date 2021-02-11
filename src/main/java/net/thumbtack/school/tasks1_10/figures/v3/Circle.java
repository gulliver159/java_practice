package net.thumbtack.school.tasks1_10.figures.v3;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorException;

import java.util.Objects;

public class Circle extends Figure {

    private int radius;
    private Point2D center;


    //Конструкторы
    public Circle(Point2D center, int radius, Color color) throws ColorException {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius, Color color) throws ColorException {
        this(new Point2D(xCenter, yCenter), radius, color);
    }

    public Circle(int radius, Color color) throws ColorException {
        this(0, 0, radius, color);
    }

    public Circle(Color color) throws ColorException {
        this(0, 0, 1, color);
    }

    public Circle(int xCenter, int yCenter, int radius, String color) throws ColorException {
        this(xCenter, yCenter, radius, Color.colorFromString(color));
    }

    public Circle(Point2D center, int radius, String color) throws ColorException {
        this(center.getX(), center.getY(), radius, color);
    }

    public Circle(int radius, String color) throws ColorException {
        this(0, 0, radius, color);
    }

    public Circle(String color) throws ColorException {
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

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
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
