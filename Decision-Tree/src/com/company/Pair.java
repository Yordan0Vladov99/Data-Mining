package com.company;

public class Pair {
    private String key;
    private double value;
    private boolean isUpdated;

    public Pair() {
        this.key = "nothing";
        this.value = -1;
        //this.isUpdated = false;
    }

    public Pair(String key, double value) {
        this.key = key;
        this.value = value;
        //this.isUpdated = false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
