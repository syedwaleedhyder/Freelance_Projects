import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // FOR TASK 1 and 2

        Machine m1 = new Machine(50,200);
        Machine m2 = new Machine(60,190);
        Machine m3 = new Machine(70, 180);

        m1.id = 1;
        m2.id = 2;
        m3.id = 3;

        ArrayList<Machine> machines = new ArrayList<>();
        machines.add(m1);
        machines.add(m2);
        machines.add(m3);
        MonitoringCooler cooler = new MonitoringCooler(machines);

        cooler.startCooler();
        m1.StartMachine();
        m2.StartMachine();
        m3.StartMachine();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cooler.requestStop();

    }

}
