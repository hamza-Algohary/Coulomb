package circuitsimulator;

public class Clock {
    public int timestep_micro_second = 5;
    public int current_time_micro_second = 0;
    public double getSeconds(){
        return current_time_micro_second/1000000d;
    }
    void tick(){
        current_time_micro_second += timestep_micro_second;
    }
    void reset(){
        current_time_micro_second = 0;
    }
}
