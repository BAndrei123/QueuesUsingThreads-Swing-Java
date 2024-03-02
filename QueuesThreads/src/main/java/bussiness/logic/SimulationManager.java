package bussiness.logic;

import gui.SimulationFrame;
import model.Client;
import model.Server;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class SimulationManager implements Runnable{
    private AtomicInteger timelimit=new AtomicInteger(0);
    public int minProcessingTime;
    public int maxProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfClients;
    public int numberOfservers;

    private Scheduler scheduler;

    private String message="";
    private List<Client> clientList;

    private int ok=0;

    private JTextArea output;


    public SimulationManager(JTextArea jTextArea, int nrClients, int nrQ, Integer simTime, int minAtime, int maxAtime, int minPTime, int maxPTime){
        //initialize scheduler
        // => create and start numberOfServers threads
        // => initialize selection strategy=> createStrategy
        // genreate numberofClients using generateNRandomclients()
        // and store them to clientsList;


        this.output=jTextArea;
        this.numberOfClients=nrClients;

        this.timelimit.set(simTime);
        this.minArrivalTime=minAtime;
        this.maxArrivalTime=maxAtime;
        this.minProcessingTime=minPTime;
        this.maxProcessingTime=maxPTime;
        this.numberOfservers=nrQ;
        this.scheduler=new Scheduler(numberOfservers);
        for(int i=0; i< this.numberOfservers; i++){
            Thread serversTh= new Thread(this.scheduler.getServers().get(i));
            serversTh.start();
        }
        this.generrateNRandomClients(this.numberOfClients);




    }

    private void generrateNRandomClients(int numberOfClients){
        //generate N random tasks;
        // - random processing time
        // minProcessingTime< processing time < maxProcessingTime
        // -random arrivalTime
        // minArrivalTime< arrivalTime <  maxArrivalTime
        this.clientList=new ArrayList<>();
        Random rand= new Random();
        for(int i=1; i<=numberOfClients; i++){
            int pTime= rand.nextInt(maxProcessingTime-minProcessingTime+1) + minProcessingTime;
            int aTime= rand.nextInt(maxArrivalTime-minArrivalTime+1) + minArrivalTime;
            //int id=rand.nextInt(numberOfClients);
            Client client=new Client(i, aTime, pTime);
            clientList.add(client);

        }

    }

@Override
public void run() {
    try (PrintWriter writer = new PrintWriter("output.txt")) {
        AtomicInteger currentTime = new AtomicInteger(0);
        int peak = 0;
        int max = 0;
        int nrclients = 0;
        float avgWaitingTime = 0;
        float avgServiceTime = 0;
        float sum = 0;
        while (currentTime.intValue() < timelimit.intValue()) {
            if (ok == 0) {
                writer.println("Clients:");
                System.out.println("Clients:");
                message+="Clients:\n";
                for (Client client1 : clientList) {
                    writer.println(client1.toString() + ";");
                    System.out.println(client1.toString() + ";");
                    message+=client1.toString()+";"+"\n";
                    sum += client1.getServiceTime();
                }
                avgWaitingTime = sum / numberOfClients;
                writer.println();
                System.out.println();
                message+="\n";
                ok = 1;
            }

            List<Client> clientsToDispatch = new ArrayList<>();
            for (Client client : this.clientList) {
                if (client.getArrivalTime() == currentTime.intValue()) {
                    clientsToDispatch.add(client);
                }
            }

            for (Client client : clientsToDispatch) {
                scheduler.dispatchClient(client);
                clientList.remove(client);
            }
            if(this.clientList.isEmpty()){
                for (Server server : scheduler.getServers()) {
                    //writer.println(server.getWaitingPeriod().intValue());

                    server.flagtoBreak=1;
                }
            }

            for (Server server : scheduler.getServers()) {
                //writer.println(server.getWaitingPeriod().intValue());

                nrclients = nrclients + server.getQueueSize();
            }
            if (nrclients > max) {
                max = nrclients;
                peak = currentTime.intValue();
            }
            nrclients = 0;
            writer.println("Current time: " + currentTime + "\n");
            writer.print("Waiting clients:");

            System.out.println("Current time: " + currentTime + "\n");
            System.out.print("Waiting clients:");

            message+="Current time: " + currentTime + "\n\n";
            message+="Waiting clients:";

            for (Client client : clientList) {
                writer.print(client.toString() + "; ");
                System.out.print(client.toString() + "; ");
                message+=client.toString()+";";
            }
            writer.println();
            writer.println(scheduler.toString());

            System.out.println();
            System.out.println(scheduler.toString());


            message+="\n";
            message+=scheduler.toString()+"\n";
           output.setText(message);

            currentTime.getAndIncrement();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        sum = 0;
        for (Server server : scheduler.getServers()) {
            sum += server.getTotalWaiting().intValue();
        }
        avgServiceTime = sum / numberOfClients;
        writer.println("Peak hour: " + peak);
        writer.println("Average service time: " + avgServiceTime);
        writer.println("Average waiting time: " + avgWaitingTime);
        System.out.println("Peak hour: " + peak);
        System.out.println("Average service time: " + avgServiceTime);
        System.out.println("Average waiting time: " + avgWaitingTime);
        message+="Peak hour: " + peak + "\n";
        message+="Average service time: " + avgServiceTime + "\n";
        message+="Average waiting time: " + avgWaitingTime + "\n";
        output.setText(message);
        writer.close();
        writer.flush();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
   //   System.exit(0);

}

    public JTextArea getOutput() {
        return output;
    }
}
