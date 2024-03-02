package bussiness.logic;

import model.Client;
import model.Server;

import java.util.List;

public class ConcreteStrategy implements Strategy{

    @Override
    public void addClient(List<Server> servers, Client client) {
        //To do generate method stub
        Server toAdd= servers.get(0);
        for(Server server: servers){
            if(server.getWaitingPeriod().intValue()<toAdd.getWaitingPeriod().intValue()){
                toAdd=server;
            }
        }
        toAdd.addClient(client);
    }
}
