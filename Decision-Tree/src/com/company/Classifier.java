package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Classifier {

    private ArrayList<Patient> patients;
    Node rootNode;

    public Classifier(Patient[] patients) {
        this.patients = new ArrayList<>();
        for (Patient patient : patients) {
            this.patients.add(new Patient(patient));
        }
        rootNode = new Node();
    }
    public Classifier(ArrayList<Patient> patients) {
        this.patients = new ArrayList<>(patients);
        rootNode = new Node();
    }

    public double log2(double x){
        return Math.log(x)/Math.log(2);
    }

    public double Entropy(int lower, int higher){
        int total = lower+higher;
        if(total == lower || total == higher){
            return 0;
        }
        double lowerProp = (double)lower/(double)total;
        double higherProp = (double)higher/(double)total;

        return -1*higherProp*log2(higherProp) -lowerProp*log2(lowerProp);
    }
    private String[] setValues(String attribute){
        return switch (attribute) {
            case "age" -> new String[]{"10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-99"};
            case "menopause" -> new String[]{"lt40", "ge40", "premeno"};
            case "tumor-size" -> new String[]{"0-4", "5-9", "10-14", "15-19", "20-24", "25-29", "30-34", "35-39", "40-44", "45-49", "50-54", "55-59"};
            case "inv-nodes" -> new String[]{"0-2", "3-5", "6-8", "9-11", "12-14", "15-17", "18-20", "21-23", "24-26", "27-29", "30-32", "33-35", "36-39"};
            case "node-caps" -> new String[]{"yes", "no", "?"};
            case "deg-malig" -> new String[]{"1", "2", "3"};
            case "breast" -> new String[]{"left", "right"};
            case "breast-quad" -> new String[]{"left_up", "left_low", "right_up", "right_low", "central", "?"};
            default -> new String[]{"yes", "no"};
        };
    }
    private double getGain(ArrayList<Patient> patientsList,String attribute,double entropy){
        HashMap<String,Integer> recurrentPatients = new HashMap<>();
        HashMap<String,Integer> nonRecPatients = new HashMap<>();
        String[] values = setValues(attribute);

        for (String value: values) {
            recurrentPatients.put(value,0);
            nonRecPatients.put(value,0);
        }
        int positiveResult = 0;
        int negativeResult = 0;
        for (Patient p:patientsList){
            String value = p.getAttribute(attribute);
            String event = p.getEventType();
            if(event.equals("no-recurrence-events")){
                positiveResult = nonRecPatients.get(value);
                positiveResult++;
                nonRecPatients.put(value,positiveResult);
            }
            else {
                negativeResult = recurrentPatients.get(value);
                negativeResult++;
                recurrentPatients.put(value,negativeResult);
            }
        }

        double valueEntrophy = 0;
        for (String value:values) {
            int positive = nonRecPatients.get(value);
            int negative = recurrentPatients.get(value);
            double odds = (double)(positive+negative)/ (double)patientsList.size();
            valueEntrophy+=odds*Entropy(positive,negative);
        }
       // System.out.println(entropy + "-" + valueEntrophy);
        return entropy - valueEntrophy;
    }

    public void buildTree(){
        HashMap<String,Boolean> visited = new HashMap<>();
        String[] attributes = {"age","menopause","tumor-size","inv-nodes","node-caps","deg-malig","breast","breast-quad","irradiat"};
        for(String str: attributes){
            visited.put(str,false);
        }
        buildTreeUtil(patients,visited,rootNode);

    }
    public void buildTreeUtil(ArrayList<Patient> patientList,HashMap<String,Boolean> visited,Node root){
        int positiveResult = 0;
        int negativeResult = 0;

        for (Patient p: patientList) {
            String event = p.getEventType();
            if(event.equals("no-recurrence-events")){
                positiveResult++;
            }
            else {
                negativeResult++;
            }
        }
        double entropy = Entropy(negativeResult,positiveResult);
        if(entropy <= 0.5 || patientList.size()<=3){
            String result;
            root.setKey("class");
            if(positiveResult < negativeResult){
                result = "recurrence-events";
            }
            else {
                result = "no-recurrence-events";
            }
            root.getChildren().add(new Node("answer",result));
            return;
        }
        Pair max = new Pair();
        visited.forEach((key,value) -> {
            if(!value){
               double gain = getGain(patientList,key,entropy);
                if(gain>max.getValue()){
                    max.setKey(key);
                    max.setValue(gain);
                    max.setUpdated(true);
                }
            }
        });
        String attribute = max.getKey();
        String[] values = setValues(attribute);
        if(!max.isUpdated()){
            String result;
            root.setKey("class");
            if(positiveResult > negativeResult){
                result = "no-recurrence-events";
            }
            else if (negativeResult > positiveResult) {
                result = "recurrence-events";
            }
            else{
                Random random = new Random();
                int coin = random.nextInt(100);
                if(coin%2==0){
                    result = "no-recurrence-events";
                }
                else {
                    result = "recurrence-events";
                }
            }
            root.getChildren().add(new Node("answer",result));
            return;
        }
        visited.put(attribute,true);
        root.setKey(attribute);
        HashMap<String,ArrayList<Patient>> subGroups = new HashMap<>();
        for(String value: values){
            subGroups.put(value,new ArrayList<>());
        }
        for (Patient patient:patientList){
            String value = patient.getAttribute(attribute);
            subGroups.get(value).add(patient);
        }
        for (String value: values){
            Node node = new Node("",value);
            root.getChildren().add(node);
            buildTreeUtil(subGroups.get(value),new HashMap<>(visited),node);
        }
    }


    public String predictEvent(Patient patient){
        return predictEventUtil(patient,this.rootNode);
    }
    private String predictEventUtil(Patient patient,Node root){
        if(root.getKey().equals("answer")){
            return root.getValue();
        }
        if(root.getKey().equals("class")){
            return root.getChildren().get(0).getValue();
        }
        String value = patient.getAttribute(root.getKey());
        for (Node child: root.getChildren()){
            if(child.getValue().equals(value)){
                return predictEventUtil(patient,child);
            }
        }
        return "error";
    }
}
