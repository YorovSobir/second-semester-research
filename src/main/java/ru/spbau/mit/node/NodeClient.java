package ru.spbau.mit.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class NodeClient implements Runnable {

    private String hostName;
    private int port;
    private boolean send = true;

    public double getWeightOut() {
        return weightOut;
    }

    public void setWeightOut(double weightOut) {
        this.weightOut = weightOut;
    }

    public void stop() {
        send = false;
    }

    public void start() {
        send = true;
    }

    private double weightOut = 1.;

    public NodeClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try{
            Socket socket = new Socket(hostName, port);
            out = new PrintWriter(socket.getOutputStream(),
                    true);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + hostName);
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }

        while (true) {
            while (!send);
//                System.out.println("client");
            try {
                out.print(weightOut);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
