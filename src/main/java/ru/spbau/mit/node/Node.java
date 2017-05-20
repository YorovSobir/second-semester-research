package ru.spbau.mit.node;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private static double epsilon = 1.e-10;
    private int node;
    private double betta;
    private int serverPort;
    private NodeServer server;
    private List<NodeClient> outAdjList = new ArrayList<>();

    public Node(int node, int serverPort, double betta) {
        this.node = node;
        this.serverPort = serverPort;
        this.betta = betta;
        init();
    }

    public void stop() {
        for (NodeClient node: outAdjList) {
            node.stop();
        }
    }

    public void start() {
        for (NodeClient node: outAdjList) {
            node.start();
        }
    }

    public boolean updateWeight() {
        double totalInWeight = server.totalInWeight();
        int outDegree = outAdjList.size();
        boolean isChanged = false;
        double totalOutWeight = 0.;
        for (NodeClient outAdj: outAdjList) {
            double oldWeight = outAdj.getWeightOut();
            double newWeight = oldWeight +
                    betta * (totalInWeight / outDegree - oldWeight);
            if (Math.abs(oldWeight - newWeight) > epsilon) {
                isChanged = true;
            }
            outAdj.setWeightOut(newWeight);
            totalOutWeight += newWeight;
        }
        System.out.println(node + " total in = " + totalInWeight
                            + " total out = " + totalOutWeight);
        if (Math.abs(totalInWeight - totalOutWeight) < epsilon) {
            System.out.println(node + " I'm balanced");
        }
        return isChanged;
    }

    public int getServerPort() {
        return serverPort;
    }

    private void init() {
        server = new NodeServer(serverPort);
        Thread thread = new Thread(server);
        thread.start();
    }

//    public void serverJoin() {
//        try {
//            System.out.println(serverPort + " started");
//            thread.join();
//            System.out.println(serverPort + " started");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void connectTo(String hostname, int port) {
        NodeClient client = new NodeClient(hostname, port);
        outAdjList.add(client);
        new Thread(client).start();
    }
}
