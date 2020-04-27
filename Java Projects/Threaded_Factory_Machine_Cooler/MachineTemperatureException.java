public class MachineTemperatureException extends RuntimeException {
    int machineID;
    int temperature;

    MachineTemperatureException(int machineID, int temperature){
        this.machineID = machineID;
        this.temperature = temperature;
    }
    public String toString() {
        return "MachineTemperatureException[Machine: "  + machineID + ", Temperature: " + temperature+"]";
    }

}
