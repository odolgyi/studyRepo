package com.cluster;

public class Signal {
    String name;

    public Signal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Signal: " + "name='" + name + '\'';
    }
}
