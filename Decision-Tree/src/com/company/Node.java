package com.company;

import java.util.ArrayList;

public class Node {
    private String key;
    private String value;
    private ArrayList<Node> children;

    public Node(){
        this.key = "";
        this.value = "";
        children = new ArrayList<>();
    }
    public Node(String key,String str){
        this.key = key;
        this.value=str;

        children = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }
}
