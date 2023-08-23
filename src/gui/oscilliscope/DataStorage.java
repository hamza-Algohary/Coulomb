package gui.oscilliscope;

import java.util.HashMap;
import java.util.LinkedList;

public class DataStorage extends LinkedList<HashMap<String , Double>>{
    public double max = 50;
    public void append(HashMap<String , Double> x){
        while(size()>50){
            remove(0);
        }
        add(x);
    }
}