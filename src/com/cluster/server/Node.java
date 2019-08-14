package com.cluster.server;

import com.cluster.exception.SignalFailedException;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Random;

public class Node implements isFaillable, Serializable {

    private static final Random RANDOM = new Random();

    private int number;
    private boolean transactionPassed;


    public Node(int number, boolean transactionPassed) {
        this.number = number;
        this.transactionPassed = transactionPassed;
    }

    @Override
    public int getId() {
        return number;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isFailed() {
        return transactionPassed;
    }

    @Override
    public isFaillable getInnerFallible(int number) {
        return null;
    }

    @Override
    public void doTransaction() {
        int number = RANDOM.nextInt(10);
        if (number == 0) {
            throw new SignalFailedException(MessageFormat.format("Node №{0} has transactionPassed", getId()));
        }
        transactionPassed = true;
    }

    @Override
    public String toString() {
        return "Node{id=" + number + ", transactionPassed=" + transactionPassed + "}";
    }
}
