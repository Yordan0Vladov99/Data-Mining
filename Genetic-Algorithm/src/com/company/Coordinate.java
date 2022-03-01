package com.company;

import java.util.Objects;

public class Coordinate {
    private double X;
    private double Y;

    public Coordinate(double x, double y) {
        X = x;
        Y = y;
    }

    public Coordinate(Coordinate coordinate){
        this.X = coordinate.X;
        this.Y = coordinate.Y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.X, X) == 0 &&
                Double.compare(that.Y, Y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    @Override
    public String toString() {
        return  X + "," + Y;
    }
}
