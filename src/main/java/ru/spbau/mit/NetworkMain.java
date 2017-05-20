package ru.spbau.mit;

import ru.spbau.mit.node.Node;

import java.util.*;

public class NetworkMain implements Runnable {

    private static final int START_PORT = 1234;
    private static final String HOST_NAME = "localhost";
    private int currentFreePort = START_PORT;
    private int currentNode = 0;
    private Map<Integer, Integer> nodeToPort = new HashMap<>();
    private List<Node> nodes = new ArrayList<>();
    private Random random = new Random();

    public void createNode() {
        double betta = random.nextDouble();
        Node newNode = new Node(currentNode, currentFreePort, betta);
        nodes.add(newNode);
        nodeToPort.put(currentNode, currentFreePort);
        ++currentFreePort;
        ++currentNode;
    }

    public void connect(int u, int v) {
        Node begin = nodes.get(u - 1);
        begin.connectTo(HOST_NAME, nodes.get(v - 1).getServerPort());
    }

    public void stopAll() {
        for (Node node: nodes) {
            node.stop();
        }
    }

    public void startAll() {
        for (Node node: nodes) {
            node.start();
        }
    }

    public boolean updateWeights() {
        boolean is_changed = false;
        for (Node node : nodes) {
            if (node.updateWeight()) {
                is_changed = true;
            }
        }
        return is_changed;
    }

    public void nodeToPort() {
        nodeToPort.forEach((i1, i2) -> System.out.println(i1 + " to " + i2));
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean is_changed = true;
//        System.out.println("update start");
        while(is_changed) {
            stopAll();
            System.out.println("start updating");
            if (!updateWeights()) {
                is_changed = false;
            }
            System.out.println("finish updating");
            startAll();
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("update end");
    }
}
