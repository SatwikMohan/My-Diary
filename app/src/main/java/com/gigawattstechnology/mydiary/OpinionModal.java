package com.gigawattstechnology.mydiary;

public class OpinionModal {
    String topic;
    String solution;

    public OpinionModal(String topic, String solution) {
        this.topic = topic;
        this.solution = solution;
    }

    public String getTopic() {
        return topic;
    }

    public String getSolution() {
        return solution;
    }
}
