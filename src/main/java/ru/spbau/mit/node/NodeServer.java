package ru.spbau.mit.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class NodeServer implements Runnable {
    private ServerSocket server;
    private int port;
    private List<ClientWorker> inAdjList = new ArrayList<>();

    public NodeServer(int port) {
        this.port = port;
    }

    public double totalInWeight() {
        double sum = 0.;
        for (ClientWorker inAdj: inAdjList) {
            sum += inAdj.getWeightIn();
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
