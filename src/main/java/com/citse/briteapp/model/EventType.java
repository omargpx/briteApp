package com.citse.briteapp.model;

public enum EventType {

    NORMAL(1,"face-to-face conference"),
    VIRTUAL(2,"virtual conference");

    private final int order;
    private final String description;

    EventType(int order, String description) {
        this.order = order;
        this.description = description;
    }

    public int getOrder() {
        return this.order;
    }

    public String getDescription() {
        return this.description;
    }
}
