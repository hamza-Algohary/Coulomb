package circuitsimulator;

import java.util.HashMap;
import java.util.Vector;

import algebra.Equation;
import algebra.Equations;
import algebra.Utils;
import algebra.Variable;

public class CircuitSimulator {

    public static final int MAX_ITERATIONS = 1000;

    public class NON_CONVERGANCE extends Exception{

    }

    private static class Network extends HashMap<String , Vector<Variable>>{
        public boolean conatinsNode(String head){
            for(var node : this.keySet()){
                if(node.compareTo(head)==0){
                    return true;
                }
            }
            return false;
        }
        public Vector<Equation> getEquations(){
            Vector<Equation> equations = new Vector<>();
            boolean first = true;
            for(var node : this.keySet()){
                if(first){
                    Equation ground = new Equation();
                    ground.vars.add(new Variable("V"+node, 1));
                    ground.constant = 0;
                    equations.add(ground);
                    first = false;
                }else{
                    equations.add(new Equation(this.get(node) , 0));
                }
            }
    
            return equations;
        }
    };
    private static class Networks extends Vector<Network>{
        public boolean conatainsNode(String head){
            for(var network : this){
                if(network.conatinsNode(head)){
                    return true;
                }
            }
            return false;
        }
        public Equations getEquations(){
            Equations equations = new Equations();
            for(var network : this){
                equations.addAll(network.getEquations());
            }
            return equations;
        }
    };
    public static class Circuit extends Vector<Device> {
        public Network nodeCurrents = new Network();
        private boolean areConnected(String node1 , String node2){
            for(var current1 : nodeCurrents.get(node1)){
                for(var current2 : nodeCurrents.get(node2)){
                    if(current1.name.compareTo(current2.name) == 0){
                        return true;
                    }
                }
            }
            return false;
        }
        private Network getNetwork(String head , Network network){ 
            network.put(head, nodeCurrents.get(head));
            for(var node : nodeCurrents.keySet()){
                if(!network.containsKey(node) && areConnected(head, node)){
                    network.putAll(getNetwork(node, network));
                }
            }
            return network;
        }
        private Networks getNetworks(){
            Networks networks = new Networks();
            for(var node : nodeCurrents.keySet()){
                if(!networks.conatainsNode(node))
                    networks.add(getNetwork(node, new Network()));
            }  
            return networks;
        }
        public void addCurrentTo(String I , String point){
            Utils.getKey(nodeCurrents,point, new Vector<Variable>()).add(new Variable(I , 1));
        }
        public void addCurrentFrom(String I , String point){
            Utils.getKey(nodeCurrents,point, new Vector<Variable>()).add(new Variable(I , -1));
        }
        public Equations getTestingEquations(){
            nodeCurrents.clear();
            Equations equations = new Equations();
            for(var device : this)
                equations.addAll(device.getTestingEquations());            
            equations.addAll(this.getNetworks().getEquations());
            return equations;
        }
        public Equations getActiveEquations(){
            nodeCurrents.clear();
            Equations equations = new Equations();
            for(var device : this)
                equations.addAll(device.getActiveEquations());            
            equations.addAll(this.getNetworks().getEquations());
            return equations;
        }
        boolean isTerminal(String point){
            for(var node : nodeCurrents.keySet()){
                if(node.compareTo(point)==0){
                    return true;
                }
            }
            return false;
        }

        public void reset(){
            for(Device device : this){
                device.reset();
            }
        }
    };

    public Circuit circuit = new Circuit();
    public HashMap<String , Double> data = new HashMap<>();
    public HashMap<String , Double> testingData = new HashMap<>();
    public Clock clock = new Clock();
    public void addDevice(Device device){
        //device.circuitSimulator = this;
        circuit.add(device);
    }
    public void removeDevice(Device device){
        circuit.remove(device);
    }
    public CircuitSimulator(){
        Device.circuitSimulator = this;
    }

    private boolean hasNonLinear(){
        for(var device : circuit){
            if(!device.isLinear())
                return true;
        }
        return false;
    }
    private void doTestingComputation()throws Exception{
        if(hasNonLinear()){
            //testingData.clear();
            // DON NOT SWAP THE FOLLOWING TWO LINES.
            testingData = circuit.getTestingEquations().solve();
            HashMap<String , Double> lastTestingData = circuit.getTestingEquations().solve();

            int iterations = 0;
            while(Utils.calculateBiggestDifference(lastTestingData, testingData) > 1e-4){
                lastTestingData = testingData;
                testingData = circuit.getTestingEquations().solve();
                iterations++;
                if(iterations > MAX_ITERATIONS){
                    //System.out.println("Iterations = " + iterations);
                    throw new NON_CONVERGANCE();
                }
                //System.out.println("difference = " + Utils.calculateBiggestDifference(lastTestingData, testingData));
            }
        }
    }
    public boolean isFirstIteration(){
        return clock.current_time_micro_second == 0;
    }
    private void compute()throws Exception{
        doTestingComputation();
        data = circuit.getActiveEquations().solve();    
    }
    public void tick()throws Exception{
        compute();
        clock.tick();
    }

    public void reset(){
        clock.reset();
        circuit.reset();
    }
}