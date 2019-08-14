package com.cluster;

import com.cluster.exception.SignalFailedException;
import com.cluster.search.BinaryFailSearchEngine;
import com.cluster.server.Cluster;

import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Cluster cluster = new Cluster();
        cluster.addRandomNumberOfServers(6);
        System.out.println("Number of servers in the cluster " + cluster.getSize());
        System.out.println(cluster);
        System.out.println("---------------------------------------------");

        try {
            cluster.doTransaction();
            transactionSuccessful(cluster);
        } catch (SignalFailedException e) {
            transactionFailed(cluster, e);
        }
    }

    private static void transactionFailed(Cluster cluster, SignalFailedException e) throws InterruptedException {
        System.out.println(cluster);

        System.out.println("Detected fail:");
        e.printStackTrace();
        Thread.sleep(10); // To guarantee stacktrace printing

        System.out.println("Searching transaction to confirm fail:");
        BinaryFailSearchEngine engine = new BinaryFailSearchEngine();
        System.out.println("\n" + engine.binarySearchServer(cluster));
    }

    private static void transactionSuccessful(Cluster cluster) {
        System.out.println(cluster);
        System.out.println("Transaction has passed successfully!");
    }

    public static void writeOutput() throws IOException {
        File file = new File("D:\\Dropbox\\Job\\Java\\my tasks Java\\studyRepo\\src\\com\\cluster\\resources\\data.json");
       // ObjectMapper objectMapper = new ObjectMapper();
    }
}