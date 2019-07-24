package com.cluster;

import java.util.*;

public class Cluster {

    private int numberOfFailedServer = 0;
    private int numberOfFailedNode = 0;
    Map<Server, List<Node>> mapOfServers = new HashMap<>();

    public void addRandomNumberOfServers(int bound) {
        Random random = new Random();
        int randomValue = (random.nextInt(bound)) + 1;

        for (int i = 0; i < randomValue; i++) {
            Server server = new Server(i);
            mapOfServers.put(server, server.addRandomValueOfNode(bound));
        }
    }

    public int numberOfServers() {
        return mapOfServers.size();
    }

    public void sendData(Signal signal) throws SignalFailedException {
        for (Server server : mapOfServers.keySet()) {
            for (Node node : server.getAllNodes()) {
                if (!node.isFailable()) {
                    node.addSignalToNode(signal);
                    node.removeSignalFromNode();
                } else {
                    numberOfFailedServer = server.getServerNumber();
                    numberOfFailedNode = node.getNodeNumber();
                    throw new SignalFailedException("Signal was failed on the server" + server.toString() + " Node =" + node.getNodeNumber());
                }
            }
        }
    }

    public int BinarySearchServer() {
        List<Server> serverList = getSortedServerList();
        int begin = 0;
        int end = serverList.size() - 1;
        while (begin <= end) {
            int middle = begin + (end - begin) / 2;
            if (numberOfFailedServer == serverList.get(middle).getServerNumber()) {
                return middle;
            }
            if (numberOfFailedServer > serverList.get(middle).getServerNumber()) begin = middle + 1;
            else end = middle - 1;
        }
        return -1;
    }

    public int BinarySearchNode() {
        List<Node> nodeList = getSortedServerList().get(numberOfFailedServer).getAllNodes();  // diff

        int begin = 0;
        int end = nodeList.size() - 1;   // diff
        while (begin <= end) {
            int middle = begin + (end - begin) / 2;
            if (numberOfFailedNode == nodeList.get(middle).getNodeNumber()) {  // diff
                return middle;
            }
            if (numberOfFailedNode > nodeList.get(middle).getNodeNumber()) begin = middle + 1;  // diff
            else end = middle - 1;
        }
        return -1;
    }

    public List<Server> getSortedServerList(){
        List<Server> serverList = new ArrayList<>(mapOfServers.keySet());
        Collections.sort(serverList, Comparator.comparing(Server::getServerNumber));
       return serverList;
    }
}