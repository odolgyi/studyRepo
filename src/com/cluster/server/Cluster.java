package com.cluster.server;

import com.cluster.exception.MyOptional;
import com.cluster.exception.SignalFailedException;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Cluster implements isFaillable {

    private final static Random RANDOM = new Random();

    private boolean transactionPassed;
    private Map<MyOptional<Server>, List<MyOptional<Node>>> mapOfServers = new HashMap<>();

    public void addRandomNumberOfServers(int bound) {
        int randomValue = (RANDOM.nextInt(bound)) + 1;
        for (int i = 0; i < randomValue; i++) {
            MyOptional<Server> server = new MyOptional<>(new Server(i, bound));
            mapOfServers.put(server, server.get().getAllNodes());
        }
    }

    public List<Server> getSortedServerList() {
        return new ArrayList<>(mapOfServers.keySet()).stream().filter(MyOptional::isPresent).map(MyOptional::get).sorted(Comparator.comparing(Server::getId)).collect(toList());
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isFailed() {
        return transactionPassed;
    }

    @Override
    public isFaillable getInnerFallible(int number) {
        return getSortedServerList().get(number);
    }

    @Override
    public void doTransaction() throws SignalFailedException {
        try {
            for (Server server : getSortedServerList()) {
                server.doTransaction();
            }
            transactionPassed = true;
        } catch (SignalFailedException e) {
            throw new SignalFailedException("Cluster has failed transaction", e);
        }
    }

    @Override
    public int getSize() {
        return mapOfServers.size();
    }

    @Override
    public String toString() {
        return "Cluster " + getSortedServerList().stream()
                .map(Objects::toString)
                .map(string -> string.replaceAll("\\[", "\n"))
                .collect(toList()).toString()
                .replaceAll("\n, ", "\n")
                .replaceAll("\n]}", "")
                .replaceAll("\\[", "")
                .replaceAll(".\\,", "");
    }
}