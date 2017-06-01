package ru.spbau.mit.node;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWorker implements Runnable {

    public double getWeightIn() {
        return weightIn;
    }

    private double weightIn = 1.;

    public boolean isClosed() {
        return closed;
    }

    private boolean closed = false;
    private Socket client;

    private int port;

    ClientWorker(Socket client, int port) {
        this.client = client;
        this.port = port;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }

        while (true) {
            try {
                weightIn = Double.parseDouble(in.readLine());
                Thread.sleep(100);
                weightIn = 0.;
//                if (!in.ready()) {
//                    closed = true;
//                    break;
//                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
