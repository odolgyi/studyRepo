package com.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Server {
    private int number;
    private List<Node> nodeList = new ArrayList<>();

    public Server(int number) {
        this.number = number;
    }

    public List<Node> addRandomValueOfNode(int bound) {
        Random random = new Random();
        int randomValue = (random.nextInt(bound))+1 ;
        for (int i = 0; i < randomValue; i++) {
            nodeList.add(new Node(i, false));
        }
        return nodeList;
    }

    public void setNodeWithFailedSignal(){
        int numberOfNode = nodeList.size() -1;
        nodeList.get(numberOfNode).setSignalFailed();
    }

    public List<Node> getAllNodes(){
        return nodeList;
    }

    public Node getNodeByIndex(int index){
        return nodeList.get(index);
    }

    public int getServerNumber() {
        return number;
    }

    @Override
    public String toString() {
        return  " number = " + number+", ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return number == server.number &&
                Objects.equals(nodeList, server.nodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, nodeList);
    }
}
