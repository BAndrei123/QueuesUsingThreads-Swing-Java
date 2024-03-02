package gui;

import bussiness.logic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationFrame extends JDialog{
    private JTextField nrOfClients;
    private JTextField nrOfQueues;
    private JTextField simulationInterval;
    private JTextField minimumAtime;
    private JTextField maximumAtime;
    private JTextField minimumStime;
    private JTextField maximStime;
    private JButton startSimulationButton;
    private JPanel SimulationFrame;
    private JTextArea outputArea;

    public SimulationFrame(JFrame parent){
        super(parent);
        setTitle("Simulation");
        setContentPane(SimulationFrame);
        setMinimumSize(new Dimension(950,800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        outputArea.setEditable(false);


        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!nrOfClients.getText().matches("\\d+") || !nrOfQueues.getText().matches("\\d+") || !simulationInterval.getText().matches("\\d+") && !minimumAtime.getText().matches("\\d+") || !minimumStime.getText().matches("\\d+") || !maximumAtime.getText().matches("\\d+") || !maximumAtime.getText().matches("\\d+"))
                    JOptionPane.showMessageDialog(SimulationFrame.this,"Error, only integers are accepted","Try again",JOptionPane.ERROR_MESSAGE);
                else {
                    int nrClients= Integer.parseInt(nrOfClients.getText());
                    int nrQueues= Integer.parseInt(nrOfQueues.getText());
                    int simInt= Integer.parseInt(simulationInterval.getText());
                    int minATime= Integer.parseInt(minimumAtime.getText());
                    int maxAtime= Integer.parseInt(maximumAtime.getText());
                    int minPTime= Integer.parseInt(minimumStime.getText());
                    int maxPTime= Integer.parseInt(maximStime.getText());


              //      System.out.println(nrQueues);

                    SimulationManager gen = new SimulationManager(outputArea, nrClients, nrQueues, simInt, minATime, maxAtime, minPTime, maxPTime);
                    Thread t = new Thread(gen);
                    outputArea = gen.getOutput();
                    t.start();
                }
            }
        });
        setVisible(true);
    }
}
