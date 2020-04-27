/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


//public class Machine extends TimerTask implements Runnable {
public class Machine implements Runnable {
    public int id;
    private boolean isRunning;
    private int minTemp;
    private int maxTemp;

    private int currentTemp = 100;
    private Cooler connectedCooler = null;

    public Cooler getConnectedCooler() {
        return connectedCooler;
    }


    Timer timer = new Timer();
    Random rand = new Random();
    private boolean doStop = false;


    public Machine(int minTemp, int maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    @Override
    public void run() {
        System.out.printf("Machine %d is starting....\n", this.id);
        while (keepRunning()) {
            currentTemp += rand.nextInt(5);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (currentTemp < minTemp || currentTemp > maxTemp) {
                this.stopMachine();
                throw new MachineTemperatureException(this.id, this.getCurrentTemp());
            }
        }
    }

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    public void StartMachine() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void stopMachine() {
        System.out.printf("Machine %d is stopping.... \n", this.id);
        this.doStop();
    }

    public int getCurrentTemp() {
        return this.currentTemp;
    }
    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }
    public int getminTemp() {
        return this.minTemp;
    }

    public int getMaxTemp() {
        return this.maxTemp;
    }

    public synchronized boolean connectCooler(Cooler cooler ){
        if (cooler.isConnectedToMachine() || connectedCooler !=null) {
            System.out.printf("Machine %d failed to connect with cooler %d. \n\n", this.id, ((MonitoringCooler)cooler).id);
            return false;
        }
        connectedCooler = cooler;

        System.out.printf("Machine %d connected with cooler %d. \n\n", this.id, ((MonitoringCooler)cooler).id);
        return true;
    }
    public void disconnectCooler(){
        System.out.printf("Machine %d disconnected from cooler. \n\n", this.id, ((MonitoringCooler)connectedCooler).id) ;
        ((MonitoringCooler)connectedCooler).setConnected(false);
        this.connectedCooler = null;


    }
}