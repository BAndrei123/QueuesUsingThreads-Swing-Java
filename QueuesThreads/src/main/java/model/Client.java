package model;

public class Client {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {

        return serviceTime;
    }

    public String toString(){                                   //prints the contents of the client object in the form
        return "("+getID()+", "+getArrivalTime()+", "+getServiceTime()+")";   // (id, arrivalTime, serviceTime).
    }
}
