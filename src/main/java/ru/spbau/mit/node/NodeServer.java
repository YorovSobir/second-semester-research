package ru.spbau.mit.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NodeServer implements Runnable {
    private ServerSocket server;

    public int getPort() {
        return port;
    }

    private int port;
    private String hostName;
    private List<ClientWorker> inAdjList = new ArrayList<>();

    public NodeServer(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public double totalInWeight() {
        double sum = 0.;
        for (Iterator<ClientWorker> it = inAdjList.iterator(); it.hasNext();) {
            ClientWorker temp = it.next();
            if (temp.isClosed()) {
                it.remove();
                continue;
            }
            sum += temp.getWeightIn();
        }
        return sum;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        }

        while(true) {
            ClientWorker w;
            try {
                w = new ClientWorker(server.accept(), port);
                inAdjList.add(w);
                new Thread(w).start();
            } catch (IOException e) {
                System.out.println("Accept failed: " + port);
                System.exit(-1);
            }
        }
    }
}
