package com.cluster;

import java.util.List;
import java.util.Random;

public class Application {

    public static void main(String[] args) {
        Cluster cluster = new Cluster();
        cluster.addRandomNumberOfServers(6);
        System.out.println("Number of servers in the cluster " + cluster.numberOfServers());

        createNodeWithBreakSignal(cluster);

        Signal signalTest = new Signal("wave");
        try {
            cluster.sendData(signalTest);
        } catch (SignalFailedException e) {
            e.printStackTrace();
        }

        System.out.println("Binary search found: Server = " + cluster.BinarySearchServer() + ", Node which failed signal = " + cluster.BinarySearchNode());
    }

    static void createNodeWithBreakSignal(Cluster cluster) {
        List<Server> serverList = cluster.getSortedServerList();
        Random random = new Random();
        if (serverList.size() == 1) {
            serverList.get(0).setNodeWithFailedSignal();
        } else {
            int randomValue = random.nextInt(serverList.size());
            serverList.get(randomValue).setNodeWithFailedSignal();
        }
    }
}