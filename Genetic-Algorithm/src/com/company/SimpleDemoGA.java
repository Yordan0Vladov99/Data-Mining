package com.company;

import java.util.*;


//Main class
public class SimpleDemoGA {


    public static void main(String[] args) {
        Coordinate[] coordinates = new Coordinate[12];
        coordinates[0] = new Coordinate(0.190032E-03,-0.285946E-03);
        coordinates[1] = new Coordinate(168.521,31.4012);
        coordinates[2] = new Coordinate(112.198,-110.561);
        coordinates[3] = new Coordinate(69.4331,-246.780);
        coordinates[4] = new Coordinate(-27.0206,-282.758);
        coordinates[5] = new Coordinate(179.933,-318.031);
        coordinates[6] = new Coordinate(217.343,-447.089);
        coordinates[7] = new Coordinate(335.751,-269.577);
        coordinates[8] = new Coordinate(320.350,-160.900);
        coordinates[9] = new Coordinate(306.320,-108.090);
        coordinates[10] = new Coordinate(383.458,-0.608756E-03);
        coordinates[11] = new Coordinate(492.671,-131.563);


        Population population = new Population(coordinates);
        population.solve();

    }

}


