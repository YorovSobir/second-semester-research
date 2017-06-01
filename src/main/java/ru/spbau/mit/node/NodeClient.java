package ru.spbau.mit.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class NodeClient implements Runnable {

    private static final double epsilon = 1.e-10;
    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    private String hostName;
    private int port;
    private boolean send = true;
    private boolean close = false;

    public double getWeightOut() {
        return weightOut;
    }

    public void setWeightOut(double weightOut) {
        this.weightOut = weightOut;
//        System.out.println("weight out = " + weightOut);
//        if (weightOut < epsilon) {
//            this.weightOut = 0.;
//        } else {
//            this.weightOut = weightOut;
//        }
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

    public void setClose() {
        close = true;
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

        while (!close) {
            while (!send);
            try {
                out.println(weightOut);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
