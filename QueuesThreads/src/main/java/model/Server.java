package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class Server implements Runnable{
    private int id;
    private BlockingQueue<Client> clients;
    private final AtomicInteger waitingPeriod;

    private final AtomicInteger serviceTime;

    private final AtomicInteger totalWaiting;

    public int flagtoBreak=0;

    public Server(int id) {
        clients= new LinkedBlockingDeque<>();
        waitingPeriod=new AtomicInteger(0);
        serviceTime=new AtomicInteger(0);
        totalWaiting=new AtomicInteger(0);
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public AtomicInteger getTotalWaiting() {
        return totalWaiting;
    }



    public void addClient(Client newClient){
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
        totalWaiting.addAndGet( newClient.getServiceTime());
    }


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (clients.size() > 0) {
                    try {
                        Client client = clients.peek();
                        Thread.sleep(1000*client.getServiceTime());
                        clients.poll();
                        if (waitingPeriod.intValue() > client.getServiceTime()) {
                            waitingPeriod.addAndGet(-client.getServiceTime());

                        } else {
                            waitingPeriod.set(0);
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(clients.isEmpty() && flagtoBreak==1)
                break;
        }
    }


    public String toString(){
        String print="";
        print +="Queue" + getId() +": ";
        if(clients.size()>0)
            for(Client client: clients){
                print+=client.toString()+"; ";
            }
        else{
            print+="closed";
        }
        return print;
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public int getQueueSize(){
        return clients.size();
    }


    public List<Client> getQueue() {
        return (List<Client>) clients;
    }
}
