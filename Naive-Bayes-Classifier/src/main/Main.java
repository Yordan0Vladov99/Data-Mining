package main;

import java.io.*;
import java.util.*;

public class Main {
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
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = null;
        String data;
        try {
            File file = new File("house-votes-84.data");
            inputStream = new FileInputStream(file);
            data = readFromInputStream(inputStream);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] politicians = data.split("\n");
        int[] indexes = new int[politicians.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        Random random = new Random();

        for (int i = 0; i < indexes.length; i++) {
            int r = random.nextInt(indexes.length);
            int temp = indexes[r];
            indexes[r] = indexes[i];
            indexes[i] = temp;
        }

        ArrayList<Politician>[] groups = new ArrayList[10];
        Classifier[] classifiers = new Classifier[9];
        int k=0;
        for (int i = 0; i < 9; i++) {
            groups[i] = new ArrayList<>();
            for (int j = 0; j < politicians.length/10; j++) {
                String[] pol = politicians[indexes[k++]].split(",");
                Politician politician = new Politician(pol);
                groups[i].add(politician);
            }
        }
        groups[9] = new ArrayList<>();
        while (k < politicians.length){
           String[] pol = politicians[indexes[k++]].split(",");
           Politician politician = new Politician(pol);
           groups[9].add(politician);
       }

        int totalCorrect = 0;

        for (int i = 0; i < 10; i++) {
            ArrayList<Politician> learningGroup = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if(i==j)continue;
                for (Politician pol: groups[j]){
                    Politician politician = new Politician(pol);
                    learningGroup.add(politician);
                }
            }
            Classifier classifier = new Classifier(learningGroup);
            classifier.trainClassifier();
            int correct=0;
            for (Politician pol : groups[i]){
                String prediction = classifier.predictParty(pol);
                String party = pol.getParty();
                if(prediction.equals(party)) correct++;
            }
            totalCorrect+=correct;
            System.out.println(correct+"/"+groups[i].size());
        }
        System.out.println("Total\n"+totalCorrect+"/"+politicians.length);
    }
}
