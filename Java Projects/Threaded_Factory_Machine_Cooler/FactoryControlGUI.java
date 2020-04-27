import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;


public class FactoryControlGUI extends JPanel implements ItemListener {
    private Map<Integer, Integer> bars =
            new LinkedHashMap<Integer, Integer>();
    private  int machines;
    private int coolers;
    private ArrayList<Machine> machinesArray = new ArrayList<>();
    private ArrayList<Cooler> coolersArray = new ArrayList<>();
    static JRadioButton startButton, stopButton;
    static JLabel startLabel, stopLabel;


    public FactoryControlGUI(int machines, int coolers){
        this.machines = machines;
        this.coolers = coolers;
        for(int i = 1; i<=machines; i++) {
            Machine machine = new Machine(50,200);
            machine.id = i;
            machinesArray.add(machine);
        }
        for(int i = 1; i<=coolers; i++){
            Cooler cooler = new MonitoringCooler(machinesArray);
            ((MonitoringCooler)cooler).id = i;
            coolersArray.add(cooler);
        }
        setLayout(null);
        setPreferredSize(new Dimension(550, 550));

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == startButton) {
            if (e.getStateChange() == 1) {
                // Start button selected
                startThreads();
            }
        }
        else {

            if (e.getStateChange() == 1) {
                stopThreads();
            }
        }
    }

    private void stopThreads() {
        for(Machine machine: machinesArray){
            machine.doStop();
        }
        for(Cooler cooler: coolersArray){
            ((MonitoringCooler)cooler).doStop();
        }
    }


    public void populateBarData(){
        Color colorBar;
        for(Machine machine: machinesArray){
            addBar(machine.id, machine.getCurrentTemp());
        }
    }

    /**
     * Add new bar to chart
     *
     * @param machineID machine number to display bar
     * @param temperatureOfMachine size of bar
     */
    public void addBar(int machineID , int temperatureOfMachine ) {
        bars.put(machineID, temperatureOfMachine);
        repaint();
    }

    public Color getColorOfBar(int temperature){
        if(temperature>200)
            return Color.RED;
        else if(temperature>50)
            return Color.YELLOW;
        else
            return Color.BLUE;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // For clearing the frame

        if(machinesArray == null || coolersArray == null){
            return;
        }

        // paint bars

        int width = ((getWidth()-40) / bars.size()) - 2;
        int x = 1;
        for (Map.Entry<Integer, Integer> bar : bars.entrySet()) {
            Integer machineID = bar.getKey();
            Integer currentTemperature = bar.getValue();
            Color colorOfBar = getColorOfBar(currentTemperature);

            int height = currentTemperature * 2;
            g.setColor(colorOfBar);
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            g.drawRect(x, getHeight() - height, width, height);

            String attached;
            if(machinesArray.get(machineID-1).getConnectedCooler() == null){
                attached = "-";
            }
            else {
                attached = "+";
            }
            g.drawString(attached, x+width/2, getHeight()-5 );

            x += (width + 2);
        }
        // Margins at different temperatures
        g.setColor(Color.BLUE);       // change the drawing color
        g.drawLine(0,getHeight()-100, getWidth()-35, getHeight()-100);
        g.drawString("50", getWidth()-30, getHeight()-100 );
        g.setColor(Color.BLACK);       // change the drawing color
        g.drawLine(0,getHeight()-250, getWidth()-35, getHeight()-250);
        g.drawString("125", getWidth()-30, getHeight()-250 );
        g.setColor(Color.RED);       // change the drawing color
        g.drawLine(0,getHeight()-400, getWidth()-35, getHeight()-400);
        g.drawString("200", getWidth()-30, getHeight()-400 );
        g.setColor(Color.BLACK);       // change the drawing color
        g.drawLine(0,getHeight()-500, getWidth()-35, getHeight()-500);
        g.drawString("250", getWidth()-30, getHeight()-500 );
    }

    public void startThreads(){


        for(Cooler cooler: coolersArray){
            ((MonitoringCooler)cooler).startCooler();
        }
        for(Machine machine: machinesArray){
            machine.StartMachine();
        }


    }
    public static void main(String[] args) {

        //Main program logic

        //GUI Logic
        JFrame frame = new JFrame("Bar Chart");
        int machines = 50, coolers = 5;
        FactoryControlGUI factoryControlGUI = new FactoryControlGUI(machines, coolers);

        JPanel buttonPanel = new JPanel();

        startButton = new JRadioButton("Start");
        stopButton = new JRadioButton("Stop");

        startButton.addItemListener(factoryControlGUI);
        stopButton.addItemListener(factoryControlGUI);

        ButtonGroup bg = new ButtonGroup();
        bg.add(startButton);
        bg.add(stopButton);

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.add(factoryControlGUI, BorderLayout.SOUTH);
        frame.add(buttonPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while(true){
            factoryControlGUI.populateBarData();
            factoryControlGUI.repaint();
        }
    }
}