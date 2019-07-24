package com.cluster;

public class Node implements isFailable {
    private int number;
    private boolean isSignalFailed;
    private Signal signal;

    public Node(int number, boolean isSignalFailed) {
        this.number = number;
        this.isSignalFailed = isSignalFailed;
    }

    public int getNodeNumber() {
        return number;
    }

    public void addSignalToNode(Signal signal){
        this.signal = signal;
    }

    public void removeSignalFromNode(){
         this.signal=null;
    }

    public void setSignalFailed() {
        isSignalFailed = true;
    }

    @Override
    public String toString() {
        return "\n+ Node{" + "number=" + number + ", isSignalFailed=" + isSignalFailed + '}';
    }

    @Override
    public boolean isFailable() {
        return isSignalFailed;
    }
}
