package com.gmail.kramarenko104.kassa;

public class Kassa {

    private boolean isWorking;
    private boolean isOccupied;
    private boolean isStop;

    public Kassa(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public synchronized boolean isWorking() {
        return isWorking;
    }

    public synchronized void setWorking(boolean working) {
        this.isWorking = working;
    }
    
    public synchronized boolean isStop() {
        return isStop;
    }

    public synchronized void setStop(boolean isStop) {
    	this.isStop = isStop;
    }

    public synchronized boolean isOccupied() {
        return isOccupied;
    }

    public synchronized void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
