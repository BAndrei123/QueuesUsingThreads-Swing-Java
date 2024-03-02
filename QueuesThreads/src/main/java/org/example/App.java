package org.example;


import bussiness.logic.SimulationManager;
import gui.SimulationFrame;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
       SimulationFrame simulationFrame=new SimulationFrame(null);
        /*JTextArea jTextArea = null;
        SimulationManager gen=new SimulationManager(jTextArea,4,2,60,2,30,2,4);
        Thread t= new Thread(gen);
        t.start();*/
    }
}
