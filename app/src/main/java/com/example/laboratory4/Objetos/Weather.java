package com.example.laboratory4.Objetos;

public class Weather {
    public Main main;
    public String name;
    public int cod;
    public String windOrientation;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }
}
