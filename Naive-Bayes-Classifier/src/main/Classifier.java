package main;

import java.util.ArrayList;

public class Classifier {
    private Politician[] politicians;
    private Odds[] republicanOdds;
    private Odds[] democratOdds;
    public Classifier(Politician[] politicians){
        this.politicians = new Politician[politicians.length];
        for (int i = 0; i < politicians.length; i++) {
            this.politicians[i] = new Politician(politicians[i]);
        }
        republicanOdds = new Odds[politicians[0].getLength()];
        democratOdds = new Odds[politicians[0].getLength()];

    }
    public Classifier(ArrayList<Politician> politicians){
        this.politicians = new Politician[politicians.size()];

        int k=0;
        for(Politician p:politicians){
            this.politicians[k++] = new Politician(p);
        }
        republicanOdds = new Odds[this.politicians[0].getLength()];
        democratOdds = new Odds[this.politicians[0].getLength()];

    }
    public void trainClassifier(){
        for (int i = 0; i < politicians[0].getLength(); i++) {
            int rYes = 0;
            int rNo = 0;
            int rQuestion = 0;
            int rTotal = 0;

            int dYes = 0;
            int dNo = 0;
            int dTotal = 0;
            int dQuestion = 0;

            for (int j = 0; j < politicians.length; j++) {
                if(politicians[j].getParty().equals("republican")){
                    rTotal++;
                    char vote = politicians[j].getVote(i);
                    if(vote == 'y'){
                        rYes++;
                    }
                    else if(vote == 'n'){
                        rNo++;
                    }
                    else {
                        rQuestion++;
                    }
                }
                else{
                    dTotal++;
                    char vote = politicians[j].getVote(i);
                    if(vote == 'y'){
                        dYes++;
                    }
                    else if(vote == 'n'){
                        dNo++;
                    }
                    else {
                        dQuestion++;
                    }
                }
            }
            if(rTotal==0){
                republicanOdds[i] = new Odds(0,0,0);
            }
            else {
                double rYesOdds = (double)rYes/(double)rTotal;
                double rNoOdds = (double)rNo/(double)rTotal;
                double rQuestionOdds = (double)rQuestion/(double)rTotal;
                republicanOdds[i] = new Odds(rYesOdds,rNoOdds,rQuestionOdds);
            }

            if(dTotal==0){
                democratOdds[i] = new Odds(0,0,0);
            }
            else {
                double dYesOdds = (double)dYes/(double)dTotal;
                double dNoOdds = (double)dNo/(double)dTotal;
                double dQuestionOdds = (double)dQuestion/(double)dTotal;
                democratOdds[i] = new Odds(dYesOdds,dNoOdds,dQuestionOdds);
            }

        }
    }

    public  void printOdds(){
        System.out.println("Republican");
        StringBuilder stringBuilder = new StringBuilder("Y:");
        for (int i = 0; i < republicanOdds.length; i++) {
            stringBuilder.append(republicanOdds[i].getyOdds());
            if(i!= republicanOdds.length-1) {
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder("N:");
        for (int i = 0; i < republicanOdds.length; i++) {
            stringBuilder.append(republicanOdds[i].getnOdds());
            if(i!= republicanOdds.length-1) {
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder("Q:");
        for (int i = 0; i < republicanOdds.length; i++) {
            stringBuilder.append(republicanOdds[i].getqOdds());
            if(i!= republicanOdds.length-1){
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());


        System.out.println("Democrat");
         stringBuilder = new StringBuilder("Y:");
        for (int i = 0; i < democratOdds.length; i++) {
            stringBuilder.append(democratOdds[i].getyOdds());
            if(i!= democratOdds.length-1){
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());

        stringBuilder = new StringBuilder("N:");
        for (int i = 0; i < democratOdds.length; i++) {
            stringBuilder.append(democratOdds[i].getnOdds());
            if(i!= democratOdds.length-1){
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());

        stringBuilder = new StringBuilder("Q:");
        for (int i = 0; i < democratOdds.length; i++) {
            stringBuilder.append(democratOdds[i].getqOdds());
            if(i!= democratOdds.length-1){
                stringBuilder.append(",");
            }
        }
        System.out.println(stringBuilder.toString());

    }

    public String predictParty(Politician pol){
        double rOdds = 1;
        double dOdds = 1;

        for (int i = 0; i < pol.getLength(); i++) {
            char vote = pol.getVote(i);
            if(vote == 'y'){
                rOdds *= republicanOdds[i].getyOdds();
                dOdds *= democratOdds[i].getyOdds();
            }
            else if(vote == 'n'){
                rOdds *= republicanOdds[i].getnOdds();
                dOdds *= democratOdds[i].getnOdds();
            }
            else {
                rOdds *= republicanOdds[i].getqOdds();
                dOdds *= democratOdds[i].getqOdds();
            }
        }
        if(rOdds > dOdds){
            return "republican";
        }
        else {
            return "democrat";
        }
    }
}
