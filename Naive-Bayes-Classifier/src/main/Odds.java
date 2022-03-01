package main;

public class Odds {
    private double yOdds;
    private double nOdds;
    private double qOdds;

    public Odds() {
        this.yOdds = 0;
        this.nOdds = 0;
        this.qOdds = 0;
    }

    public Odds(double yOdds, double nOdds, double qOdds) {
        this.yOdds = yOdds;
        this.nOdds = nOdds;
        this.qOdds = qOdds;
    }

    public double getyOdds() {
        return yOdds;
    }

    public void setyOdds(double yOdds) {
        this.yOdds = yOdds;
    }

    public double getnOdds() {
        return nOdds;
    }

    public void setnOdds(double nOdds) {
        this.nOdds = nOdds;
    }

    public double getqOdds() {
        return qOdds;
    }

    public void setqOdds(double qOdds) {
        this.qOdds = qOdds;
    }
}
