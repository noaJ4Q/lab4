package com.example.laboratory4.Objetos;

public class Weather {
    public Main main;
    public String name;
    public int cod;
    public String windDirection;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }
}
