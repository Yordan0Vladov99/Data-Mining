package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

class Individual {

    private double fitness;
    private Coordinate[] coordinates;
    private HashMap<Coordinate,Integer> positions;
    private int coordinateLength;

    public Individual() {
        coordinateLength = 8;
        coordinates = new Coordinate[8];
        positions = new HashMap<>();
        Random random = new Random();
        double x;
        double y;
        int whole;
        boolean neg;
        for (int i = 0; i < 8; i++) {
            whole = random.nextInt(30000);
            neg = random.nextBoolean();
            x = (double)whole/100;
            if(neg)x*=-1;

            whole = random.nextInt(30000);
            neg = random.nextBoolean();
            y = (double)whole/100;
            if(neg)y*=-1;
            coordinates[i] = new Coordinate(x,y);
            positions.put(coordinates[i],i);
        }

        calcFitness();
    }
    public Individual(Coordinate[] arr){
        coordinateLength = arr.length;
        coordinates = new Coordinate[coordinateLength];
        positions = new HashMap<>();

        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(arr[i]);
            positions.put(coordinates[i],i);
        }

        calcFitness();
    }
    public Individual(Individual ind){
        coordinateLength = ind.coordinateLength;
        coordinates = new Coordinate[coordinateLength];
        positions = new HashMap<>();

        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinate(ind.coordinates[i]);
            positions.put(coordinates[i],i);
        }

        fitness = ind.fitness;
    }


    public Coordinate getCoordinates(int index){

        return coordinates[index];
    }
    public int getPosition(Coordinate index){
        return positions.get(index);
    }

    public int getFitness(){
        return (int)fitness;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        for (int i = 1; i < coordinateLength; i++) {
            Coordinate first = coordinates[i];
            Coordinate second = coordinates[i - 1];
            fitness+=Math.sqrt((first.getX()-second.getX())*(first.getX()-second.getX()) + (first.getY()-second.getY())*(first.getY()-second.getY()));
        }
        Coordinate first = coordinates[0];
        Coordinate second = coordinates[coordinateLength-1];
        //fitness+=Math.sqrt((first.getX()-second.getX())*(first.getX()-second.getX()) + (first.getY()-second.getY())*(first.getY()-second.getY()));
    }


    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }
}
