package ru.spbau.mit.node;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWorker implements Runnable {

    public double getWeightIn() {
        return weightIn;
    }

    private double weightIn = 1.;
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
//            System.out.println("clientWorker");
            try {
                weightIn = in.nextDouble();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
