package gui;

public class Prefixes {
    
    public static double parseDouble(String str) throws Exception{
        char c = str.charAt(str.length()-1);
        String number = str.substring(0, str.length()-2);
        double prefix = 1;
        switch (c) {
            case 'G': prefix = 1e9;   break;
            case 'M': prefix = 1e6;   break;
            case 'k': prefix = 1e3;   break;            
            case 'm': prefix = 1e-3;  break;
            case 'u': prefix = 1e-6;  break;
            case 'n': prefix = 1e-9;  break;
            case 'p': prefix = 1e-12; break;
        }
        return Double.parseDouble(number)*prefix;
    }

    //private static double parseDoubleScientificNotation(String str){
//
    //}

    /*public static String toScientificNotation(double number){
        NumberFormat formatter = new DecimalFormat("00.##E00");
        String str = formatter.format(number);  
        var tokens = Component.split(str, 'E');//str.split("E", 0);
        int exponent = Integer.parseInt(tokens.get(1));
        String prefix = "";
        switch (exponent){
            case 9 : prefix = "G";
            case 6 : prefix = "M";
            case 3 : prefix = "k";
            case -3 : prefix = "m";
            case -6 : prefix = "u";
            case -9 : prefix = "n";
            case -12 : prefix = "p";
        }

        if(prefix.equals("")){
            return ""+number;
        }

        return tokens.get(0) + prefix;
    }*/
}
