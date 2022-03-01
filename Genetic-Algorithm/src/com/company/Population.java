package com.company;

import java.util.*;

public class Population {
    private Individual[] group;
    private int geneLength;
    private int popSize;

    public Population(){
     //   fittest = 0;
      //  secondFittest = 0;
      //  leastFit = 0;
        geneLength = 8;
        Coordinate[] coordinates = new Coordinate[8];
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
        }

        group = new Individual[100];
        popSize = 100;
        double min = Integer.MAX_VALUE;
        double secondMin = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        int fitness;
        for (int i = 0; i < popSize; i++) {
            Collections.shuffle(Arrays.asList(coordinates));
            group[i] = new Individual(coordinates);
        }
     //   calculateFittest();
    }
    public Population(Coordinate[] coordinates){
        geneLength = coordinates.length;
        Random random = new Random();

        group = new Individual[100];
        popSize = 100;
        double min = Integer.MAX_VALUE;
        double secondMin = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        int fitness;
        for (int i = 0; i < popSize; i++) {
            Collections.shuffle(Arrays.asList(coordinates));
            group[i] = new Individual(coordinates);
        }
    }
    public void selection(){
        Individual[] children = new Individual[popSize];
        for (int i = 0; i < popSize; i++) {
            children[i] = new Individual(group[i]);
        }
        Random random = new Random();
        for (int j = 0; j < popSize; j++) {
            int fIndex = random.nextInt(popSize/2);
            int sIndex = random.nextInt(popSize/2);
            crossover(children,j,fIndex,sIndex);
        }
    }
    public void selection_V2(){
        Arrays.sort(group, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return (int)(o1.getFitness() - o2.getFitness());
            }
        });
        printGroup();
        int[] indexes = new int[popSize/2];
        for (int i = 0; i < popSize/2; i++) {
            indexes[i] = i;
        }
        Collections.shuffle(Arrays.asList(indexes));


        Individual[] children = new Individual[popSize];
        for (int i = 0; i < popSize; i++) {
            children[i] = new Individual(group[i]);
        }
        Random random = new Random();
        for (int j = 0; j < popSize/2; j+=2) {
            int fIndex = indexes[j];
            int sIndex = indexes[j+1];
            if(j!=popSize/2-1){
                crossover(children,j+popSize/2,fIndex,sIndex);
            }
            else{
                crossover(children,j+popSize/2,fIndex,new Random().nextInt(popSize/2));
            }
        }
    }

    private void crossover(Individual[] children,int child,int firstIndex,int secondIndex){
        Individual fittestInd = new Individual(children[firstIndex]);
        Individual secondFittestInd = new Individual(children[secondIndex]);
        Coordinate[] firstChild = new Coordinate[geneLength];
        Coordinate[] secondChild = new Coordinate[geneLength];
        int f = 0, s = 0;
        HashSet<Integer> firstRemaining = new HashSet<>();
        HashSet<Integer> secondRemaining = new HashSet<>();

        for (int i = 0; i < geneLength; i++) {
            firstRemaining.add(i);
            secondRemaining.add(i);
        }
        int i = firstRemaining.iterator().next();

        while (!firstRemaining.isEmpty() || !secondRemaining.isEmpty()){
            if(!firstRemaining.contains(i)){
                i = firstRemaining.iterator().next();
            }
            firstRemaining.remove(i);
            firstChild[f++] = secondFittestInd.getCoordinates(i);
            int j = fittestInd.getPosition(secondFittestInd.getCoordinates(i));
            j = fittestInd.getPosition(secondFittestInd.getCoordinates(j));
            secondChild[s++] = secondFittestInd.getCoordinates(j);
            secondRemaining.remove(j);

            i = fittestInd.getPosition(secondFittestInd.getCoordinates(j));
        }
        Random random = new Random();
        int n = 1 + random.nextInt(100);
        if(n%7==0){
            int fIndex = random.nextInt(geneLength);
            int sIndex = random.nextInt(geneLength);
            Coordinate temp = new Coordinate(firstChild[fIndex]);
            firstChild[fIndex] = new Coordinate(firstChild[sIndex]);
            firstChild[sIndex] = new Coordinate(temp);
        }
        group[child] = new Individual(firstChild);
    }

    private void crossover_V2(Individual[] children,int child,int firstIndex,int secondIndex){
        Individual fittestInd = new Individual(children[firstIndex]);
        Individual secondFittestInd = new Individual(children[secondIndex]);
        Coordinate[] firstChild = new Coordinate[geneLength];
        Coordinate[] secondChild = new Coordinate[geneLength];

        HashSet<Integer> firstRemaining = new HashSet<>();
        HashSet<Integer> secondRemaining = new HashSet<>();

        for (int i = 0; i < geneLength; i++) {
            firstRemaining.add(i);
            secondRemaining.add(i);
        }
        Random random = new Random();
        int startGene = random.nextInt(geneLength);
        int endGene = random.nextInt(geneLength);
        if(startGene>endGene){
            int temp = startGene;
            startGene = endGene;
            endGene = temp;
        }
        for (int i = startGene; i <= endGene; i++) {
            firstChild[i] = secondFittestInd.getCoordinates(i);
            secondChild[i] = fittestInd.getCoordinates(i);
            firstRemaining.remove(fittestInd.getPosition(firstChild[i]));
            secondRemaining.remove(secondFittestInd.getPosition(secondChild[i]));
        }

        int k=0;
        int m=0;
        for (int i = 0; i < geneLength; i++) {
            if(i>=startGene && i<=endGene){
                continue;
            }

                firstChild[i] = fittestInd.getCoordinates(i);
                while (!firstRemaining.contains(k)){
                    k++;
                }
                firstChild[i] = fittestInd.getCoordinates(k);
                firstRemaining.remove(k);


                while (!secondRemaining.contains(m)){
                    m++;
                }
                secondChild[i] = secondFittestInd.getCoordinates(m);
                secondRemaining.remove(m);

        }
        //int n = 1 + random.nextInt(100);
        //if(n%7==0){
        //    int fIndex = random.nextInt(geneLength);
        //    int sIndex = random.nextInt(geneLength);
        //    Coordinate temp = new Coordinate(firstChild[fIndex]);
        //    firstChild[fIndex] = new Coordinate(firstChild[sIndex]);
        //    firstChild[sIndex] = new Coordinate(temp);
        //}
        group[child] = new Individual(firstChild);
    }

    public void printGroup(){
        String str="";
        for (int i = 0; i < popSize; i++) {
            str+=group[i].getFitness()+" ";
        }
        System.out.println(str);
    }
    public void solve(){
        for (int i = 0; i < 1000; i++) {
            Arrays.sort(group, new Comparator<Individual>() {
                @Override
                public int compare(Individual o1, Individual o2) {
                    return (int)(o1.getFitness() - o2.getFitness());
                }
            });
            if(i==0 || i==249 || i==499 || i==749 || i==999){
                int generation = i+1;
                String str = "";
                System.out.println("Generation"+" " +generation + ": " + group[0].getFitness());
            }
            selection();
        }
        int min = Integer.MAX_VALUE;
        int n=0;
        for (int i = 0; i < popSize; i++) {
            if(group[i].getFitness()<min){
                min=group[i].getFitness();
                n = i;
            }
        }
        //System.out.println(group[n]);
        //System.out.println(group[n].getFitness());
        }
    }


