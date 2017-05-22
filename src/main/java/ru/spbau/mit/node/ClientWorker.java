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
        Scanner in = null;
        try {
            in = new Scanner(client.getInputStream());
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }

        while (true) {
            try {
                weightIn = in.nextDouble();
                Thread.sleep(100);
                if (!in.hasNextDouble()) {
                    closed = true;
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
