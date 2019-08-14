package com.cluster.server;

public interface isFaillable {

    int getId();

    boolean isFailed();

    isFaillable getInnerFallible(int number);

    void doTransaction();

    int getSize();
}
