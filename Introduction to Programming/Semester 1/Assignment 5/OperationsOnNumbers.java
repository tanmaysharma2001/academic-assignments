/* Tanmay Sharma - t.sharma@innopolis.university */
/* ITP assignment 5 - Operation on Numbers */
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.lang.Math;

public class OperationsOnNumbers {

    /*----------------------- Exceptions -----------------------*/

    static class NonExistingException extends Exception {
        public NonExistingException() {}
        public NonExistingException(String message)
        {
            super(message);
        }
    }

    static class InvalidOperations extends Exception {
        public InvalidOperations() {}

        public InvalidOperations(String message) { super(message); }
    }

    static class InvalidNumbers extends Exception {
        public InvalidNumbers() {}
        public InvalidNumbers(String message) { super(message); }
    }


    /* ----------------------------------------------------------*/
    static String[] filterValues(String[] values, FileWriter fw) throws IOException {
        String[] result = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            int k = 1;
            while(Character.isLetter(values[i].charAt(values[i].length() - k))) {
                k++;
            }
            String suffix = "";
            String tempSuffix = values[i].substring(values[i].length() - k + 1);
            suffix = tempSuffix.toLowerCase();
            k--;
            int endIndex = values[i].length() - k;
            result[i] = values[i].substring(0, endIndex);
            switch(suffix) {
                case "l": {
                    try {
                        Long temp = Long.parseLong(result[i]);
                        if (!temp.toString().equals(result[i])) {
                            fw.write("Exception: Invalid data");
                              fw.close();
                            System.exit(0);
                        }
                    } catch (Exception ex) {
                        fw.write("Exception: Invalid data");
                        fw.close();
                        System.exit(0);
                    }
                }
                break;
                case "f": {
                    try {
                        Float temp = Float.parseFloat(result[i]);
                        if (!temp.toString().equals(result[i])) {
                            fw.write("Exception: Invalid data");
                            fw.close();
                            System.exit(0);
                        }
                    }
                    catch (Exception e) {
                        fw.write("Exception: Invalid data");
                        fw.close();
                        System.exit(0);
                    }
                }
                break;

                case "bi": {
                    try {
                        BigInteger temp = new BigInteger(result[i]);
                        if (!temp.toString().equals(result[i])) {
                            fw.write("Exception: Invalid data");
                            fw.close();
                            System.exit(0);
                        }
                    }
                    catch (Exception e) {
                        fw.write("Exception: Invalid data");
                        fw.close();
                        System.exit(0);
                    }
                }
                break;

                case "bd": {
                    try {
                        BigDecimal temp = new BigDecimal(result[i]);
                        if (!temp.toString().equals(result[i])) {
                            fw.write("Exception: Invalid data");
                            fw.close();
                            System.exit(0);
                        }
                    }
                    catch (Exception e) {
                        fw.write("Exception: Invalid data");
                        fw.close();
                        System.exit(0);
                    }
                }
                break;

                default:
                    if (result[i].indexOf(".") != -1) {
                        try {
                            Double temp = Double.parseDouble(result[i].toString());
                            if (!temp.toString().equals(result[i].toString())) {
                                fw.write("Exception: Invalid data");
                                fw.close();
                                System.exit(0);
                            }
                        } catch (Exception ex) {
                            fw.write("Exception: Invalid data");
                            fw.close();
                            System.exit(0);
                        }
                    } else {
                        try {
                            int temp = Integer.parseInt(result[i].toString());
                            if (!(temp + "").equals(result[i].toString())) {
                                fw.write("Exception: Invalid data");
                                fw.close();
                                System.exit(0);
                            }
                        } catch (Exception ex) {
                            fw.write("Exception: Invalid data");
                            fw.close();
                            System.exit(0);
                        }
                    }

            }
        }
        return result;
    }

    static double addValues(String[] values) {
        double sum = Double.parseDouble(values[0]);
        for(int i = 1; i < values.length; i++) {
            sum = sum + (Double.parseDouble(values[i]));
        }
        return sum;
    }

    static double multiplyValues(String[] values) {
        double product = Double.parseDouble(values[0]);
        for(int i = 1; i < values.length; i++) {
            product = product * (Double.parseDouble(values[i]));
        }
        return product;
    }

    static double[] divideValues(String[] values, double divOperator, String[] tempValues) {
        double div = divOperator;

        double[] result = new double[values.length];

        for(int i = 0; i < values.length; i++) {
            if((tempValues[i].charAt(tempValues[i].length() - 1)) ==  'F' || (tempValues[i].charAt(tempValues[i].length() - 1)) ==  'f') {
                float divOP = (float) div;
                result[i] = (Float.parseFloat(values[i]) / divOP);
            }
            else {
                result[i] = Double.parseDouble(values[i]) / div;
            }
        }

        return result;
    }

    static double average(String[] values) {
        double sum = 0.0;

        for(int i = 0; i < values.length; i++) {
            sum += Math.abs(Double.parseDouble(values[i]));
        }

        double average = sum / values.length;

        return average;
    }

    static String[] removeNegativeValues(String[] values) {

        List<String> tempResult = new ArrayList<String>();

        for(int i = 0; i < values.length; i++) {
            if(!values[i].contains("-")) {
                tempResult.add(values[i]);
            }
        }

        return tempResult.toArray(new String[0]);
    }

    static String[] stringArray(double[] decimalArray) {

        String[] result = new String[decimalArray.length];

        for(int i = 0; i < decimalArray.length; i++) {
            result[i] = Double.toString(decimalArray[i]);
        }

        return result;
    }

    static double[] squareRoot(String[] values) {
        double[] result = new double[values.length];

        for(int i = 0; i < values.length; i++) {
            result[i] = Math.sqrt(Double.parseDouble(values[i]));
        }

        return result;
    }

    public static void main(String[] args) {
        try{
            File file = new File("input.txt");

            FileReader fr = new FileReader(file);

            FileWriter fw = new FileWriter("output.txt");

            BufferedReader br = new BufferedReader(fr);

            String[] inputs = new String[3];

            String line;

            int a = 0;

            while ((line = br.readLine()) != null) {
                inputs[a] = line;
                a++;
            }

            fr.close();

            String[] operationsString = inputs[0].split(" ");

            int[] operations = new int[operationsString.length];

            if(operations.length > 10) {
                fw.write("Exception: The list of operations has an invalid length");
                fw.close();
                throw new InvalidOperations();
            }

            for(int j = 0; j < operationsString.length; j++) {
                try {
                    operations[j] = Integer.parseInt(operationsString[j]);
                }
                catch (Exception e) {
                    fw.write("Exception: Invalid data");
                    fw.close();
                    System.exit(0);
                }
                if (operations[j] <= 6 && operations[j] >= 1) {
                    continue;
                }
                else {
                    fw.write("Exception: Non-existing operation");
                    fw.close();
                    throw new NonExistingException();
                }
            }

            String[] tempValues = inputs[1].split(" ");

            int flag = 0;

            for(int l = 0; l < operations.length; l++) {
                if(operations[l] == 3) {
                    flag = 1;
                }
            }
            if(inputs[2] == null && flag == 1) {
                fw.write("Exception: Invalid data");
                fw.close();
                System.exit(0);
            }

            double divOperator;

            if(flag == 0) {
                divOperator = 0.0;
            }
            else {
                divOperator = Double.parseDouble(inputs[2]);
            }

            String[] values = filterValues(tempValues, fw);

            if(values.length > 20) {
                fw.write("Exception: The list of numbers has an invalid length");
                fw.close();
                throw new InvalidNumbers();
            }

            double[] result;

            for(int q = 0; q < operations.length; q++) {
                int opCode = operations[q];

                switch(opCode) {
                    case 1: fw.write(Double.toString(addValues(values)));
                        fw.write("\n");
                        break;

                    case 2: fw.write(Double.toString(multiplyValues(values)));
                        fw.write("\n");
                        break;

                    case 3:
                        if (divOperator == 0) {
                            fw.write("Exception: Division by 0\n");
                            break;
                        }
                        result = divideValues(values, divOperator, tempValues);
                        for(int i = 0; i < result.length; i++) {
                            fw.write(String.valueOf(result[i]));
                            if(i == result.length - 1) {
                                continue;
                            }
                            fw.write(", ");
                        }
                        fw.write("\n");
                        values = stringArray(result);
                        break;

                    case 4: fw.write(Double.toString(average(values)));
                        fw.write("\n");
                        break;

                    case 5: boolean neg = false;
                        for (int i = 0; i < values.length; i++) {
                            if (values[i].indexOf("-") != -1) {
                                neg = true;
                            }
                        }
                        if (neg) {
                            fw.write("Exception: Square root cannot be calculated for negative value\n");
                            break;
                        }
                        result = squareRoot(values);
                        for (int i = 0; i < result.length; i++) {
                            fw.write(String.valueOf(result[i]));
                            if (i == result.length - 1) {
                                continue;
                            }
                            fw.write(", ");
                        }
                        fw.write("\n");
                        values = stringArray(result);
                        break;

                    case 6:
                        String[] res = removeNegativeValues(values);
                        for (int i = 0;  i < res.length; i++) {
                            fw.write(res[i]);
                            if (i == res.length - 1) {
                                continue;
                            }
                            fw.write(", ");
                        }
                        fw.write("\n");
                        values = res;
                        break;
                }
            }

            fw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch (NonExistingException e) {
            System.exit(0);
        }
        catch (InvalidOperations e) {
            System.exit(0);
        }
        catch (InvalidNumbers e) {
            System.exit(0);
        }
    }
}