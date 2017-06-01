package ru.spbau.mit.node;

import java.util.*;

public class Node implements Runnable {
    private static double epsilon = 1.e-3;
    private double betta;
    private NodeServer server;
    private boolean isBalanced = false;
    private List<NodeClient> outAdjList = new ArrayList<>();

    public Node(String hostName, int serverPort) {
        this.betta = new Random().nextDouble();
        server = new NodeServer(hostName, serverPort);
        Thread thread = new Thread(server);
        thread.start();
    }

    public void update(HashMap<String, Integer> hosts) {
        for (Iterator<NodeClient> it = outAdjList.iterator(); it.hasNext();) {
            NodeClient node = it.next();
            if(!hosts.containsKey(node.getHostName())) {
                node.setClose();
                it.remove();
            }
        }
        hosts.forEach(this::connectTo);
    }

    private boolean updateWeight() {
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
        System.out.println(server.getPort() + " total in = " + totalInWeight
                            + " total out = " + totalOutWeight);
        if (Math.abs(totalInWeight - totalOutWeight) < epsilon
                && Math.abs(totalInWeight) > epsilon) {
            isBalanced = true;
            System.out.println("I'm balanced");
        }
        return isChanged;
    }

    private void connectTo(String hostname, int port) {
        for (NodeClient node: outAdjList) {
            if (node.getHostName().equals(hostname)) {
                return;
            }
        }
        NodeClient client = new NodeClient(hostname, port);
        outAdjList.add(client);
        new Thread(client).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean is_changed = true;
        while(true) {
            outAdjList.forEach(NodeClient::stop);
            if (!updateWeight()) {
                is_changed = false;
            }
            outAdjList.forEach(NodeClient::start);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("update end");
    }
}
