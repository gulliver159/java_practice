package net.thumbtack.school.tasks1_10.figures.v3;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorException;

import java.util.Objects;

public class Rectangle3D extends Rectangle {

    private int height;

    public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height, Color color) throws ColorException {
        super(leftTop, rightBottom, color);
        this.height = height;
    }

    public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height, Color color) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), height, color);
    }

    public Rectangle3D(int length, int width, int height, Color color) throws ColorException {
        this(0, -width, length, 0, height, color);
    }

    public Rectangle3D(Color color) throws ColorException {
        this(0, -1, 1, 0, 1, color);
    }

    public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height, String color) throws ColorException {
        this(leftTop, rightBottom, height, Color.colorFromString(color));
    }

    public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height, String color) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), height, color);
    }

    public Rectangle3D(int length, int width, int height, String color) throws ColorException {
        this(0, -width, length, 0, height, color);
    }

    public Rectangle3D(String color) throws ColorException {
        this(0, -1, 1, 0, 1, color);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getVolume() {
        return getArea() * height;
    }

    public boolean isInside(int x, int y, int z) {
        return super.isInside(x, y) && z >= 0 && z <= height;
    }

    public boolean isInside(Point3D point) {
        return isInside(point.getX(), point.getY(), point.getZ());
    }

    public boolean isInside(Rectangle3D rectangle) throws ColorException {
        return super.isInside(new Rectangle(rectangle.getTopLeft(), rectangle.getBottomRight(), getColor())) &&
                rectangle.height <= this.height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}
