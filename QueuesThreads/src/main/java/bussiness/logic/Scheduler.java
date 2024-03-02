package bussiness.logic;

import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.List;


public class Scheduler {
    private List<Server> servers;
    private int NoServers;
    private Strategy strategy;
    private final List<Thread> threads = new ArrayList<>();
    public Scheduler(int NoServers){
     this.NoServers=NoServers;
     servers=new ArrayList<>();
     for(int i=0; i < NoServers; i++){
         Server  server= new Server(i+1);

         Thread thread=new Thread(server);
         thread.start();
         threads.add(thread);
         servers.add(server);
     }
     strategy=new ConcreteStrategy();
    }
    public void dispatchClient(Client client){
        strategy.addClient(servers,client);
    }

    public String toString(){
        String printed = "";
        for(Server server : servers){
            printed += server.toString() + '\n';
        }
        printed += "\n";
        return printed;
    }

    public List<Server> getServers() {
        return servers;
    }
}
