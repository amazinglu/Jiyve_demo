package com.example.amazinglu.jiyve_demo.Model;

import java.util.Date;

public class Restaurant {
    public String name, type;
    public double distance;
    public double recommend;
    public HappyHour happyHour;

    public Restaurant() {
        this.distance = 0.0;
        this.recommend = 0.0;
        this.happyHour = new HappyHour();
    }

    public Restaurant(String name, String type, HappyHour happyHour,
                      double distance, double recommend) {
        this.name = name;
        this.type = type;
        this.happyHour = happyHour;
        this.distance = distance;
        this.recommend = recommend;
    }
}
