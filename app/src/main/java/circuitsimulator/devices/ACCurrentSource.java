package circuitsimulator.devices;


public class ACCurrentSource extends CurrentSource{
    double amplitude = 0 , frequency = 0;
    public ACCurrentSource(String start , String end , double frequency , double amplitude){
        super(start, end, 0);
        this.amplitude = amplitude;
        this.frequency = frequency;
    }
    @Override 
    public double current(){
        return amplitude*Math.sin(frequency*2*Math.PI*circuitSimulator.clock.getSeconds());
    }
}
