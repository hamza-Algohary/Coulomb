package circuitsimulator.devices;

public class ACVoltageSource extends IdealBattery{
    public double amplitude = 0;
    public double frequency = 0;
    public ACVoltageSource(String start , String end , double amplitude , double frequency){
        super(start, end, 0);
        this.amplitude = amplitude;
        this.frequency = frequency;
    }
    @Override
    public double voltage(){
        return amplitude*Math.sin(frequency*2*Math.PI*circuitSimulator.clock.getSeconds());
    }
}
