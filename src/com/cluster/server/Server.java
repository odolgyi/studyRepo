package com.cluster.server;

import com.cluster.exception.MyOptional;
import com.cluster.exception.SignalFailedException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Server implements isFaillable {

    private static final Random RANDOM = new Random();

    private int id;
    private List<MyOptional<Node>> nodeList = new ArrayList<>();
    private boolean transactionPassed;

    public Server(int id, int bound) {
        this.id = id;
        int maxNumberOfNodes = RANDOM.nextInt(bound - 1) + 1;
        for (int i = 0; i < maxNumberOfNodes; i++) {
            MyOptional<Node> nodeIsNotEmpty = new MyOptional<>(new Node(i, false));
            if (nodeIsNotEmpty.isPresent()) {
                nodeList.add(nodeIsNotEmpty);
            }
        }
    }

    @Override
    public int getSize() {
        return nodeList.size();
    }


    public List<MyOptional<Node>> getAllNodes() {
        return nodeList;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\n" + "Server id=" + id + nodeList.stream()
                .filter(MyOptional::isPresent)
                .map(MyOptional::get)
                .map(Objects::toString)
                .map(string -> string + "\n")
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return id == server.id &&
                Objects.equals(nodeList, server.nodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeList);
    }

    @Override
    public boolean isFailed() {
        return transactionPassed;
    }

    @Override
    public isFaillable getInnerFallible(int number) {
        return nodeList.get(number).get();
    }

    @Override
    public void doTransaction() {
        try {
            for (MyOptional<Node> node : nodeList) {
                node.get().doTransaction();
            }
        } catch (SignalFailedException e) {
            throw new SignalFailedException(MessageFormat.format("Server №{0} has transactionPassed", id), e);
        }
        transactionPassed = true;
    }
}
