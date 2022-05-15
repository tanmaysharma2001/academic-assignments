/* Tanmay Sharma - t.sharma@innopolis.university */
/* Assignment 3 - Inside Box - Final Submission */

import java.util.*;
import java.io.*;

public class InsideBox {

    /* Comparing the tuples individually to know which one is bigger. */
    static Boolean compareDimension(int[] dimension1, int[] dimension2) {
        int boxA = 0;
        int boxB = 0;

        for(int i = 0; i < dimension1.length; i++) {
            for(int j = 0; j < dimension2.length; j++) {
                if(dimension1[i] > dimension2[j]) {
                    boxA++;
                }
                else if(dimension1[i] < dimension2[j]) {
                    boxB++;
                }
                else {
                    continue;
                }
            }
        }

        return boxA > boxB;

    }

    /* Checking if one box will fit into another. */
    static Boolean willFit(int[] dimension1, int[] dimension2) {
        for(int i = 0; i < 3; i++) {
            if(dimension1[i] < dimension2[i]) {
                continue;
            }
            else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        try {

            /* Creating a new file instance for the file "input.txt" */
            File file = new File("input.txt");

            /* Creating a file reader instance for the 'file' "input.txt" */
            FileReader fr = new FileReader(file);

            /* Creating a buffered reader to read the file linewise. */
            BufferedReader br = new BufferedReader(fr);

            String line;

            /* Final Result of the reading. */
            String inputString = "";

            while ((line = br.readLine()) != null) {
                inputString = inputString + line;
            }

            fr.close();
            
            String String1 = inputString.replace("[", "");
            String String2 = String1.replace("]", "");

            String[] stringDimensions = String2.split(" ");

            int lengthOfDimensions = stringDimensions.length / 3;

            int[][] dimensions = new int[lengthOfDimensions][3];

            
            /* Storing the values from the String of input in a two dimension array. */
            
            for (int i = 0; i < stringDimensions.length; i++) {
                dimensions[i / 3][i % 3] = Integer.parseInt(stringDimensions[i]);
            }


            /* Sorting the tuples based on their dimensions sum. */
            
            for(int i = 0; i < dimensions.length; i++) {
                for(int j = i + 1; j < dimensions.length; j++) {
                    int[] temp = new int[dimensions[i].length];
                    if(compareDimension(dimensions[i], dimensions[j])) {
                        temp = dimensions[i].clone();
                        dimensions[i] = dimensions[j].clone();
                        dimensions[j] = temp.clone();
                    }
                }
            }

            
            /* Sorting the tuples elements using Java's timsort method. */
            
            for (int i = 0; i < dimensions.length; i++) {
                Arrays.sort(dimensions[i]);
            }

            /* Array of depths of the tuples with other elements. */
            List<Integer> depths = new ArrayList<Integer>();

            for (int i = 0; i < dimensions.length; i++) {

                /* Minimum depth should be 1. */
                int depth = 1;
                
                /* Temporary value of 'i' to store to 
                 * iterate from the same place again. 
                 */
                int tempI = i;

                for (int j = i + 1; j < dimensions.length; j++) {

                    if(willFit(dimensions[i], dimensions[j])) {
                        
                        /* If one box can be fit into another then the 
                         * value of depth will be increased. 
                         */
                        depth++;
                        
                        /* Value of 'i' is set to 'j' to check for the 
                        next box if it will fit inside the previous ones. */
                        i = j;

                    }
                    else {
                        continue;
                    }
                }

                /* Storing the depth into the depth array. */

                depths.add(depth);
                
                /* Restoring value of 'i' */
                i = tempI;

            }

            
            
            /* Selecting the maximum value from the depths list. */
            
            int l;

            int max = depths.get(0);

            for (l = 1; l < depths.size(); l++) {
                if (depths.get(l) > max) {
                    max = depths.get(l);
                }
            }
            
            /* Creating an instance of the FileWriter. */
            FileWriter fw = new FileWriter("output.txt");

            String result = Integer.toString((int) max);

            for (int i = 0; i < result.length(); i++) {
                
                /* Writing into the output file. */
                fw.write(result.charAt(i));
                
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*

[1 1 1] [2 2 2] [3 3 3]
3

[5 4 2] [6 4 5] [6 7 4] [2 3 5]
2

*/