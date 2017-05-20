package ru.spbau.mit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        NetworkMain networkMain = new NetworkMain();
        for (int i = 0; i < 5; ++i) {
            networkMain.createNode();
        }
        networkMain.nodeToPort();
        networkMain.connect(1, 2);
//        networkMain.connect(1, 3);
        networkMain.connect(2, 3);
        networkMain.connect(3, 4);
        networkMain.connect(4, 1);
        networkMain.connect(2, 5);
//        networkMain.connect(4, 5);
//        networkMain.connect(4, 6);
//        networkMain.connect(5, 6);
//        networkMain.connect(5, 1);
//        networkMain.connect(6, 1);
        new Thread(networkMain).start();
    }

//    private JTextArea textArea;
//    private JButton button;
//
//    public static void main(String[] args) {
//        new Main();
//    }
//
//    Main() {
//        JFrame frame = new JFrame("Distributed Real Balancing");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setPreferredSize(new Dimension(500, 500));
//        frame.setMinimumSize(new Dimension(500, 500));
//
//        JPanel panel = new JPanel(new GridLayout(0, 2, 1, 1));
//        textArea = new JTextArea();
//        JPanel panel1 = new JPanel();
//        panel.add(panel1);
//        textArea.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED));
//        button = new JButton("run");
//        JPanel panel2 = new JPanel(new GridLayout(0, 1, 10, 10));
//        panel2.setBorder(new EmptyBorder(2, 1, 1, 2));
//        panel2.add(textArea);
//        JPanel panel3 = new JPanel(new GridLayout(4, 1));
//        panel3.add(button);
//        panel2.add(panel3);
//        panel.add(panel2);
//
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (textArea.getLineCount() == 0) {
//                    return;
//                }
//                String[] text = textArea.getText().split(System.lineSeparator());
//                Scanner scanner = new Scanner(text[0]);
//                int vertexCount = scanner.nextInt();
//                int edgeCount = scanner.nextInt();
//                List<List<Integer>> inAdjList = new ArrayList<>(vertexCount);
//                List<List<Integer>> outAdjList = new ArrayList<>(vertexCount);
//                for (String line: textArea.getText().split(System.lineSeparator())) {
////                    Scanner scanner = new Scanner(line);
//                }
//            }
//        });
//
//        frame.setContentPane(panel);
//        frame.setVisible(true);
//    }
}
