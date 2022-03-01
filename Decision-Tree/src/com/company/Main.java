package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
        InputStream inputStream = null;
        String data;
        try {
            File file = new File("breast-cancer.data");
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

        String[] patients = data.split("\n");
        int[] indexes = new int[patients.length];
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

        ArrayList<Patient>[] groups = new ArrayList[10];
        int k=0;
        for (int i = 0; i < 9; i++) {
            groups[i] = new ArrayList<>();
            for (int j = 0; j < patients.length/10; j++) {
                String[] pol = patients[indexes[k++]].split(",");
                Patient patient = new Patient(pol);
                groups[i].add(patient);
            }
        }
        groups[9] = new ArrayList<>();
        while (k < patients.length){
            String[] pol = patients[indexes[k++]].split(",");
            Patient politician = new Patient(pol);
            groups[9].add(politician);
        }

        int totalCorrect = 0;
        for (int i = 0; i < 10; i++) {
            ArrayList<Patient> learningGroup = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if(i==j)continue;
                for (Patient pol: groups[j]){
                    Patient politician = new Patient(pol);
                    learningGroup.add(politician);
                }
            }
            Classifier classifier = new Classifier(learningGroup);
            classifier.buildTree();
            int correct=0;
            for (Patient pol : groups[i]){
                String prediction = classifier.predictEvent(pol);
                String event = pol.getEventType();
                if(prediction.equals(event)) correct++;
            }
            totalCorrect+=correct;
            System.out.println(correct+"/"+groups[i].size());
        }
        System.out.println("Total\n"+totalCorrect+"/"+patients.length);
    }
}
