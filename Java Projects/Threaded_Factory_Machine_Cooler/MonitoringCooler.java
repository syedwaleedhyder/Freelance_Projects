import java.util.ArrayList;

public class MonitoringCooler implements Cooler, Runnable {

    //
    public int id;
    //
    private final ArrayList<Machine> machinesToMonitor;
    int coolingFactor = 10;
    private boolean doStop = false;
    private boolean isConnected = false;

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    MonitoringCooler(ArrayList<Machine> machines) {
        this.machinesToMonitor = machines;
    }

    @Override
    public int getCoolingFactor() {
        return coolingFactor;
    }

    @Override
    public boolean isConnectedToMachine() {
        return isConnected;
    }

    public void startCooler() {
        System.out.printf("Starting cooler. %d\n", id );
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    public void requestStop() {
        System.out.println("Monitoring Cooler is stopping.");
        this.doStop();
    }

    @Override
    public void run() {
        while (keepRunning()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isConnectedToMachine()) {
                for (Machine machine : machinesToMonitor) {
                    if (machine.getConnectedCooler() == this) {
                        //when machine is overcooled
                        if (machine.getminTemp() + DANGER_ZONE > machine.getCurrentTemp()) {
                            System.out.printf("temperature of machine %d is %d \n", machine.id, machine.getCurrentTemp());
                            machine.disconnectCooler();
                        }
                        else{
                            machine.setCurrentTemp(machine.getCurrentTemp()-machine.getConnectedCooler().getCoolingFactor());
                        }
                        break;
                    }
                }
            }
            else{
                for (Machine machine : machinesToMonitor) {
                    //when machine is overheated.
                    if (machine.getMaxTemp() - DANGER_ZONE <= machine.getCurrentTemp()) {
                        System.out.printf("temperature of machine %d is %d \n", machine.id, machine.getCurrentTemp());
                        if (machine.connectCooler(this))
                            this.isConnected = true;
                    }
                }
            }
        }
    }
}