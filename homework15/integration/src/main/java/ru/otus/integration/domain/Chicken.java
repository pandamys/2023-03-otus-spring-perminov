package ru.otus.integration.domain;

public class Chicken {
    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Chicken(int weight) {
        this.weight = weight;
    }

    public void addWeight(int weight) {
        this.weight += weight;
    }
}
