package net.thumbtack.school.tasks1_10.cars;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorErrorCode;
import net.thumbtack.school.tasks1_10.colors.ColorException;
import net.thumbtack.school.tasks1_10.colors.Colored;

public class Car implements Colored {

    private String model;
    private int weight;
    private int maxSpeed;
    private Color color;


    public Car(String model, int weight, int maxSpeed, Color color) throws ColorException {
        this.model = model;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        setColor(color);
    }

    public Car(String model, int weight, int maxSpeed, String color) throws ColorException {
        this(model, weight, maxSpeed, Color.colorFromString(color));
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) {
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
        this.color = color;
    }

    public void setColor(String colorString) throws ColorException {
        this.color = Color.colorFromString(colorString);
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
