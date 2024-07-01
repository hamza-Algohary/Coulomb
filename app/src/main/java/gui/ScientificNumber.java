package gui;

import algebra.Utils;

public class ScientificNumber {
    public double number;
    public int exponent = 0;
    @Override
    public String toString() {
        int significant = 1;
        if(exponent < 0) significant = (int) Utils.pow(10,-exponent);
        else if(exponent > 0) significant = (int) 1e3;

        if( ( ( (int) number)* significant) == (int) (number*significant) )
            return ((int)number) + "E" + exponent;
        else
            return number + "E" + exponent;
    }
    public ScientificNumber(String str){
        str = str.replace("G", "E9");
        str = str.replace("M", "E6");
        str = str.replace("k", "E3");
        str = str.replace("m", "E-3");
        str = str.replace("u", "E-6");
        str = str.replace("n", "E-9");
        str = str.replace("p", "E-12");
        var tokens = Component.split(str, 'E');
        number = Double.parseDouble(tokens.get(0));
        if(tokens.size() > 1)
            exponent = Integer.parseInt(tokens.get(1));
    }
    public double toDouble(){
        return number * Utils.pow(10, exponent);
    }

    //public static String doubleToString(){
//
    //} 
}
