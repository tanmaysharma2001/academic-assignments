/* Tanmay Sharma - t.sharma@innopolis.university */
/* Assignment 3 - Hands Of a Clock - Final Submission. */

import java.io.*;

public class HandsOfClock {

    public static void main(String[] args) {

        try {

            // Creating a new file instance of the file "input.txt".
            File file = new File("input.txt");

            // Creating a new file instance of the 'file' "input.txt".
            FileReader fr = new FileReader(file);

            // Creating a buffering character input stream for the file.
            BufferedReader br = new BufferedReader(fr);

            String line;

            // The result file.
            String inputTime = "";

            while ((line = br.readLine()) != null) {
                inputTime = inputTime + line; // appends line to string buffer
            }

            fr.close(); // closes the stream and release the resources

            
            /* Seperating the time into hours and minutes */
            String[] time = inputTime.split(":");

            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);

            /* Angle between the hour hand and the minute hand. */
            double angle = 0.0;

            FileWriter fw = new FileWriter("output.txt");

            if ((hours < 24 && hours >= 12) && minutes < 60) {

                /* If hours > 12, we need to subtract 12 from it. */
                hours = hours - 12;

                /* Angle made by Hour Hand in the given time.*/
                double angleOfHourHand = hours * 30;

                /* Angle made by Minute Hand in the given time.*/
                double angleOfMinuteHand = minutes * 6;

                /* Angle between the hour hand and the minute hand
                 * is the absolute value of the difference of the 
                 * angle subtended by both of them.
                 * */
                angle = Math.abs(angleOfHourHand - angleOfMinuteHand);

                String result = Integer.toString((int) angle);

                for (int i = 0; i < result.length(); i++) {
                    fw.write(result.charAt(i));
                }

                fw.close();

            } else if (hours < 12 && minutes < 60) {

                double angleOfHourHand = hours * 30;

                double angleOfMinuteHand = minutes * 6;

                angle = Math.abs(angleOfHourHand - angleOfMinuteHand);

                String result = Integer.toString((int) angle);

                for (int i = 0; i < result.length(); i++) {
                    fw.write(result.charAt(i));
                }

                fw.close();

            } else {
                String result = "Wrong format";

                for (int i = 0; i < result.length(); i++) {
                    fw.write(result.charAt(i));
                }

                fw.close();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
/*

21:30
90

12:10
60

12:60
Wrong format
*/