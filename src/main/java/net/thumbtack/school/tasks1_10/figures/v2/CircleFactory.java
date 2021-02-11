package net.thumbtack.school.tasks1_10.figures.v2;

public class CircleFactory {

    private static int circleCount = 0;

    public static Circle createCircle(Point2D center, int radius, int color) {
        circleCount++;
        return new Circle(center, radius, color);
    }

    public static int getCircleCount() {
        return circleCount;
    }

    public static void reset() {
        circleCount = 0;
    }

}
