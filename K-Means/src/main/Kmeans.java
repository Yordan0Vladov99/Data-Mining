package main;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Kmeans {
    private Coordinate[] points;
    private Coordinate[] centroids;
    private ArrayList<Integer>[] clusters;
    private double xAxisMin;
    private double xAxisMax;
    private double yAxisMin;
    private double yAxisMax;

    public Kmeans(Coordinate[] points, int clustersCount) {
        double minX = 1000000;
        double maxX = -1;

        double minY = 1000000;
        double maxY = -1;
        this.points = new Coordinate[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
            if(minX > points[i].getX()) minX = points[i].getX();
            if(maxX < points[i].getX()) maxX = points[i].getX();

            if(minY > points[i].getY()) minY = points[i].getY();
            if(maxY < points[i].getY()) maxY = points[i].getY();
        }
        xAxisMin = minX;
        xAxisMax = maxX;
        yAxisMax = maxY;
        if (clustersCount <= 0) clustersCount = 1;

        this.clusters = new ArrayList[clustersCount];
        this.centroids = new Coordinate[clustersCount];

        /*Random random = new Random();
        for (int i = 0; i < clusters; i++) {
            this.centroids[i] = new Coordinate((int) minX + random.nextInt((int) maxX + 1 - (int) minX), (int) minY
                    + random.nextInt((int) maxY + 1 - (int) minY));
            this.clusters[i] = new ArrayList<Integer>();
        }
        xAxisMin = minX;
        xAxisMax = maxX;
        yAxisMin = minY;
        yAxisMax = maxY;

         */



    }
    private double CalcWSSM(Coordinate[] centroids,ArrayList<Integer>[] clusters){
        double sum = 0;
        for (int i = 0; i < clusters.length; i++) {
            for (int j:clusters[i]){
                sum+= Coordinate.EuclideanDistance(centroids[i],points[j]);
            }
        }
        return sum;
    }
    public void CalcClusters(){
        double WSSM = Double.MAX_VALUE;

        for (int n = 0; n < 100; n++) {
            ArrayList<Integer>[] clusters = new ArrayList[this.clusters.length];
            Coordinate[] centroids = new Coordinate[this.centroids.length];

            Random random = new Random();
            for(int i = 0; i < this.clusters.length; i++){
                clusters[i] = new ArrayList<Integer>();
            }
            int randIndex = random.nextInt(points.length);
            centroids[0] = new Coordinate(points[randIndex]);
            for (int i = 1; i < this.centroids.length; i++) {
                double max = Double.MIN_VALUE;
                int index = -1;
                for (int j = 0; j < points.length; j++) {
                    double min = Double.MAX_VALUE;
                    for (int k = 0; k < i; k++) {
                        double distance = Coordinate.EuclideanDistance(centroids[k],points[j]);
                        if(distance < min) {
                            min = distance;
                        }
                    }
                    if(min > max){
                        max = min;
                        index = j;
                    }
                }
                centroids[i] = new Coordinate(points[index]);
            }

            CalcClustersUtil(centroids,clusters);
            double newSum = CalcWSSM(centroids,clusters);
            if(newSum < WSSM){
                this.clusters = clusters;
                this.centroids = centroids;
                WSSM = newSum;
                System.out.println("cycle "+n+" update");
            }
        }
    }
    public void CalcClustersUtil(Coordinate[] centroids,ArrayList<Integer>[] clusters) {
        boolean converged;
        Random random = new Random();
        int iter = 0;
        while (true) {
            iter++;
            converged = true;
            for (int i = 0; i < points.length; i++) {
                int cluster = -1;
                double min = Double.MAX_VALUE;
                for (int j = 0; j < centroids.length; j++) {
                    double distance = Coordinate.EuclideanDistance(points[i], centroids[j]);
                    if (distance < min) {
                        cluster = j;
                        min = distance;
                    }
                }
                clusters[cluster].add(i);
            }

            for (int i = 0; i < clusters.length; i++) {
                double x = 0;
                double y = 0;
                for (int j : clusters[i]) {
                    x += points[j].getX();
                    y += points[j].getY();
                }
                int size = clusters[i].size();
                Coordinate newCentroid;
                if (size == 0) {
                    newCentroid = centroids[i];
                    //newCentroid = new Coordinate(xAxisMin, yAxisMin);
                    //centroids[i] = new Coordinate((int) xAxisMin + random.nextInt((int) xAxisMax + 1 - (int) xAxisMin), (int) yAxisMin
                    //        + random.nextInt((int) yAxisMax + 1 - (int) yAxisMin));
                } else {
                    newCentroid = new Coordinate(x / (double) size, y / (double) size);
                }
                if (Coordinate.EuclideanDistance(newCentroid, centroids[i]) > 0.1) {
                    converged = false;
                    centroids[i] = newCentroid;
                }
            }
            if (!converged && iter < 1000) {
                for (ArrayList<Integer> cluster : clusters) {
                    cluster.clear();
                }
            } else {
                break;
            }
        }
    }

    public void drawClusters(Stage stage) throws IOException {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(xAxisMin, xAxisMax, (double)(xAxisMax - xAxisMin)/20);
        final NumberAxis yAxis = new NumberAxis(yAxisMin, yAxisMax, (double)(yAxisMax-yAxisMin)/20);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);

        for (int i = 0; i < clusters.length; i++) {
            XYChart.Series series = new XYChart.Series();
            for (int index:clusters[i]){
                series.getData().add(new XYChart.Data(points[index].getX(),points[index].getY()));
            }
            sc.getData().add(series);
        }

        Scene scene = new Scene(sc,500,400);
        WritableImage image = scene.snapshot(null);
        File file = new File("src/clusters.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
        System.out.println("Image Saved");
        stage.setScene(scene);
        stage.show();
    }

}
