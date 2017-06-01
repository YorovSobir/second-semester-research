package ru.spbau.mit;

import ru.spbau.mit.node.Node;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.*;
import java.util.List;


public class GUIMain {

    private JTextArea hostNames = new JTextArea();
    private JTextArea ports = new JTextArea();
    private JTextField host = new JTextField();
    private JTextField port = new JTextField();
    private JButton runButton = new JButton("connect");
    private JButton runServer = new JButton("run");
    private List<JLabel> labelList = new ArrayList<>();
    private JFrame frameLabel = null;
    private JFrame frame = null;

    public HashMap<String, Integer> getHosts() {
        return hosts;
    }

    private HashMap<String, Integer> hosts = new HashMap<>();
    private Node node_;

    public GUIMain() {
        createGUI();
        addActions();
    }

    private void createGUI() {

        frame = new JFrame("Strong connectivity");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setMinimumSize(new Dimension(500, 500));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10,10));

        EmptyBorder defaultBorder = new EmptyBorder(2, 2, 2, 2);
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(defaultBorder);

        JPanel centralPanel = new JPanel(new BorderLayout(10, 10));
        centralPanel.setBorder(defaultBorder);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(defaultBorder);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centralPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        topPanel.add(new JLabel("host:"), constraints);
        constraints.gridx = 2;
        topPanel.add(new JLabel("port:"), constraints);
        constraints.weightx = 0.5;
        constraints.fill = 1;
        constraints.gridx = 1;
        topPanel.add(host, constraints);
        constraints.gridx = 3;
        topPanel.add(port, constraints);
        constraints.gridx = 4;
        constraints.weightx = 0;
        topPanel.add(runServer, constraints);

        JLabel connectLabel = new JLabel("Connect to", SwingConstants.CENTER);
        connectLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        centralPanel.add(connectLabel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(0, 2, 10, 2));

        hostNames.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED));
        JScrollPane scrollHost = new JScrollPane(hostNames);
        JPanel left = new JPanel(new BorderLayout(10, 10));
        left.add(new JLabel("host name", SwingConstants.CENTER), BorderLayout.NORTH);
        left.add(scrollHost, BorderLayout.CENTER);

        ports.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED));
        JScrollPane scrollPort = new JScrollPane(ports);
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.add(new JLabel("port", SwingConstants.CENTER), BorderLayout.NORTH);
        right.add(scrollPort, BorderLayout.CENTER);

        textPanel.add(left);
        textPanel.add(right);
        centralPanel.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.add(runButton);
        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void createNode(String host, Integer port) {
        node_ = new Node(host, port);
        new Thread(node_).start();
        System.out.println("running host = " + host + " port = " + port);
    }

    private void addActions() {
        runButton.addActionListener(e -> {
            if (hostNames.getText().isEmpty() || ports.getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Nothing to do. Please input host name(s) and port(s)",
                        "Host name(s) and port(s)",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (hostNames.getLineCount() != ports.getLineCount()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Host name counts doesn't equal to port counts",
                        "Host name(s) and port(s)",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] hostArray = hostNames.getText().split(System.lineSeparator());
            String[] portArray = ports.getText().split(System.lineSeparator());

            for (int i = 0; i < hostArray.length; ++i) {
                hosts.put(hostArray[i], Integer.parseInt(portArray[i]));
            }

//            node_.update(hosts);
            updateLabelFrame();
        });

        runServer.addActionListener(e -> {
            if (host.getText().isEmpty() || port.getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Please input host name and port",
                        "Host name and port",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            createNode(host.getText(), Integer.parseInt(port.getText()));
        });

    }

    private void updateLabelFrame() {
        labelList.clear();
        if (frameLabel == null) {
            frameLabel = new JFrame("result");
            frameLabel.setLocationRelativeTo(frame);
            frameLabel.setPreferredSize(new Dimension(200, 200));
            frameLabel.setMinimumSize(new Dimension(200, 200));
        }
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        hosts.forEach((host, port) -> {
                    JLabel label = new JLabel(host + " " + port);
                    label.setName(host + " " + port);
                    labelList.add(label);
                    panel.add(label);
        });
        frameLabel.setContentPane(panel);
        frameLabel.setVisible(true);
    }
}
