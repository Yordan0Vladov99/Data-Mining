package com.company;

import java.util.HashMap;

public class Patient {
    private String eventType;
    private HashMap<String,String> attributes;

    public Patient(String eventType, String ageRange, String menopause, String tumorSize, String invNodes, String nodeCaps, String degMalig, String breast, String breastQuad, String iradiat) {
        this.eventType = eventType;
        attributes = new HashMap<>();
        attributes.put("age",ageRange);
        attributes.put("menopause",menopause);
        attributes.put("tumor-size",tumorSize);
        attributes.put("inv-nodes",invNodes);
        attributes.put("node-caps",nodeCaps);
        attributes.put("deg-malig",degMalig);
        attributes.put("breast",breast);
        attributes.put("breast-quad",breastQuad);
        attributes.put("irradiat",iradiat);
    }
    public Patient(Patient patient){
        this.eventType = patient.eventType;
        attributes = new HashMap<>();
        attributes.put("age",patient.getAttribute("age"));
        attributes.put("menopause",patient.getAttribute("menopause"));
        attributes.put("tumor-size",patient.getAttribute("tumor-size"));
        attributes.put("inv-nodes",patient.getAttribute("inv-nodes"));
        attributes.put("node-caps",patient.getAttribute("node-caps"));
        attributes.put("deg-malig",patient.getAttribute("deg-malig"));
        attributes.put("breast",patient.getAttribute("breast"));
        attributes.put("breast-quad",patient.getAttribute("breast-quad"));
        attributes.put("irradiat",patient.getAttribute("irradiat"));
    }
    public Patient(String[] patient){
        this.eventType = patient[0];
        attributes = new HashMap<>();
        attributes.put("age",patient[1]);
        attributes.put("menopause",patient[2]);
        attributes.put("tumor-size",patient[3]);
        attributes.put("inv-nodes",patient[4]);
        attributes.put("node-caps",patient[5]);
        attributes.put("deg-malig",patient[6]);
        attributes.put("breast",patient[7]);
        attributes.put("breast-quad",patient[8]);
        attributes.put("irradiat",patient[9]);
    }
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAttribute(String attribute){
        return attributes.get(attribute);
    }

    @Override
    public String toString() {
        return eventType+","+attributes.get("age")+","+attributes.get("menopause")+","+attributes.get("tumor-size")+","+attributes.get("inv-nodes")+","+attributes.get("node-caps")+","+attributes.get("deg-malig")+","+attributes.get("breast")+","+attributes.get("breast-quad")+","+attributes.get("irradiat");
    }
}
