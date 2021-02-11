package net.thumbtack.school.tasks1_10.figures.v3;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorException;

import java.util.Objects;

public class Triangle extends Figure {

    private Point2D point1, point2, point3;

    public Triangle(Point2D point1, Point2D point2, Point2D point3, Color color) throws ColorException {
        super(color);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Triangle(Point2D point1, Point2D point2, Point2D point3, String color) throws ColorException {
        this(point1, point2, point3, Color.colorFromString(color));
    }

    public Point2D getPoint1() {
        return point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public Point2D getPoint3() {
        return point3;
    }

    public void setPoint1(Point2D point) {
        this.point1 = point;
    }

    public void setPoint2(Point2D point) {
        this.point2 = point;
    }

    public void setPoint3(Point2D point) {
        this.point3 = point;
    }

    public double getLenghtSide(Point2D point1, Point2D point2) {
        return Math.sqrt((point1.getX() - point2.getX()) * (point1.getX() - point2.getX())
                + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY()));
    }

    public double getSide12() {
        return getLenghtSide(point1, point2);
    }

    public double getSide13() {
        return getLenghtSide(point1, point3);
    }

    public double getSide23() {
        return getLenghtSide(point2, point3);
    }

    public double getPerimeter() {
        return (getSide12() + getSide23() + getSide13());
    }

    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - getSide13()) * (p - getSide23()) * (p - getSide12()));
    }

    public void moveRel(int dx, int dy) {
        point1.moveTo(point1.getX() + dx, point1.getY() + dy);
        point2.moveTo(point2.getX() + dx, point2.getY() + dy);
        point3.moveTo(point3.getX() + dx, point3.getY() + dy);
    }

    private int isInsideCalculate(int x1, int y1, int x2, int y2, int x, int y) {
        return (x1 - x) * (y2 - y1) - (x2 - x1) * (y1 - y);
    }

    public boolean isInside(int x, int y) {
        int a = isInsideCalculate(point1.getX(), point1.getY(), point2.getX(), point2.getY(), x, y);
        int b = isInsideCalculate(point2.getX(), point2.getY(), point3.getX(), point3.getY(), x, y);
        int c = isInsideCalculate(point3.getX(), point3.getY(), point1.getX(), point1.getY(), x, y);

        return ((a >= 0 && b >= 0 && c >= 0) || (a <= 0 && b <= 0 && c <= 0));
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(point1, triangle.point1) &&
                Objects.equals(point2, triangle.point2) &&
                Objects.equals(point3, triangle.point3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), point1, point2, point3);
    }
}
