package algebra;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Vector;

import gui.ScientificNumber;


public class Utils {
    public static double[] convertDoubleArraytodouble(Double array[]){
        double result[] = new double[array.length];
        for(int i = 0;i<array.length;i++){
            result[i] = array[i];
        }
        return result;
    }
    public static <T> int indexOf(T[] array ,  T element){
        for(int i=0 ; i<array.length ; i++){
            if(array[i].equals(element)){
                return i;
            }
        }
        return -1;
    }
    public static <K , V> V getKey(HashMap<K , V> map , K targetKey , V defaultValue){
        for(K key : map.keySet()){
            if(key.equals(targetKey)){
                return map.get(key);
            }
        }
        map.put(targetKey , defaultValue);
        return map.get(targetKey);
    }
    public static boolean compareDoubles(double x , double y , int precision){
        return (int)x*precision ==  (int)y*precision;
    }
    public static <T> void swap(T x , T y){
        T tmp = x;
        x = y;
        y = tmp;
    }
    public static <T> Vector<T> shortenVector(Vector<T> vect , int size){
        Vector<T> vector = new Vector<>();
        for(int i=0 ; i<vect.size() && i<size; i++){
            vector.add(vect.get(i));
        }
        return vector;
    }

    /*public static <T> T[] shortenArray(T[] arr , int size){
        return (T[]) shortenVector(new Vector<T>(Arrays.asList(arr)), size).toArray();
    }*/
    public static Double[] stringsToDoubles(String args[]){
        Double result[] = new Double[args.length];
        for(int i=0;i<args.length;i++){
            result[i] = Double.parseDouble(args[i]);
        }
        return result;
    }
    public static String[] getAllVarNamesFromEquations(final Equations equations){
        Vector<String> names = new Vector<>();
        for(Equation equation : equations){
            for(Variable variable : equation.vars){
                if(!names.contains(variable.name)){
                    names.add(variable.name);
                }
            }
        }
        return names.toArray(new String[0]);
    }
    public static double[] getAllConstantsFromEquations(final Equations equations){
        Vector<Double> constants = new Vector<>();
        for(Equation equation : equations){
            constants.add(equation.constant);
        }
        return Utils.convertDoubleArraytodouble(constants.toArray(new Double[0]));
    }
    public static <T> Vector<T> vectorize(T t){
        Vector<T> vec = new Vector<>();
        vec.add(t);
        return vec;
    }

    /////////// TESTING VARIABLES SHOULD NOT CHANGE //////////
    public static double calculateBiggestDifference(HashMap<String , Double> map1 , HashMap<String , Double> map2){
        double biggestDifference = 0;
        for(var key : map1.keySet()){
            double difference = Math.abs(map1.get(key) - map2.get(key));
            biggestDifference = Math.max(difference, biggestDifference);
        }
        return biggestDifference;
    }

    public static double limitDoubleBounds(double number){
        if(Double.isInfinite(number) || Double.isNaN(number)) return Double.MAX_VALUE;
        if(number < Double.MIN_NORMAL) return Double.MIN_NORMAL;
        return number;
    }

    public static double unsigned(double number){
        if(number < 0)
        number *= -1;
        return number;
    }

    public static double pow(double x , int y){
        double result = 1;
        if(y>0)
            for(int i=0 ; i<y ; i++){
                result *= x;
            }
        else if (y<0){
            for(int i=0 ; i>y ; i--){
                result /= x;
            }
        }
        return result;
    }
    public static String doubleToString(double x){
        NumberFormat formatter = new DecimalFormat("00.##E0");
        String formated = adjustExponents(formatter.format(x));

        formated = formated.replace("E9"  , "G");
        formated = formated.replace("E6"  , "M");
        formated = formated.replace("E3"  , "k");
        formated = formated.replace("E-3" , "m");
        formated = formated.replace("E-6" , "u");
        formated = formated.replace("E-9" , "n");
        formated = formated.replace("E-12", "p");

        String unFormated = ""+x;



        if(formated.length() < unFormated.length()){
            return formated;
        }else{
            if(( ( (int) x)* 1e3) == (int) (x*1e3) ){
                unFormated = ""+(int)x;
            }
            return unFormated;
        }
    }
    public static String adjustExponents(String str){
        ScientificNumber number = new ScientificNumber(str);
        int places = number.exponent;

        boolean lessThanOne = places < 0;
        if(lessThanOne) places *= -1;

        int distance = places%3;

        if(lessThanOne){
            number.number /= Utils.pow(10, distance);
            number.exponent += distance;
        }else{
            number.number *= Utils.pow(10, distance);
            number.exponent -= distance;
        }

        return number.toString();
    }
    /*public static boolean areEqual(HashMap<String , Double> map1 , HashMap<String , Double> map2){
        
    }*/
}
