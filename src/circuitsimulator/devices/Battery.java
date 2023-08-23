package circuitsimulator.devices;

import algebra.Equations;
import circuitsimulator.Device;

public class Battery extends Device {
    public String start, end, mid;
    public double internalResistance = 1e-3;
    double voltage = 0;

    protected enum ResistorConnection {
        PARALLEL, SERIES
    }

    protected ResistorConnection connection = ResistorConnection.SERIES;

    public Battery(String start, String end, double voltage) {
        this.id = newId();
        this.start = start;
        this.end = end;
        this.mid = start + end + "internal";
        this.voltage = voltage;
    }

    public double voltage() {
        return voltage;
    }

    @Override
    public Equations getActiveEquations() {
        IdealBattery battery;
        Resistance resistance;
        if (connection == ResistorConnection.SERIES) {
            battery = new IdealBattery(start, end, voltage());
            resistance = new Resistance(start, end, internalResistance);
        } else {
            battery = new IdealBattery(start, mid, voltage());
            resistance = new Resistance(mid, end, internalResistance);
        }

        battery.id = this.id;

        Equations equations = battery.getActiveEquations();
        equations.addAll(resistance.getActiveEquations());
        return equations;
    }

}
