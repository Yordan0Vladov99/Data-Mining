package main;

import java.io.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        int clusters = scanner.nextInt();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = null;
        String data="";
        try {
            File file = new File("src/"+filename);
            inputStream = new FileInputStream(file);
            data = readFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] points = data.split("\n");
        Coordinate[] coordinates = new Coordinate[points.length];

        for (int i = 0; i < points.length; i++) {
            String[] coordinate = points[i].split("\\s+");
            coordinates[i] = new Coordinate(Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]));
        }

        Kmeans kmeans = new Kmeans(coordinates,clusters);
        kmeans.CalcClusters();
        kmeans.drawClusters(primaryStage);
    }
}
