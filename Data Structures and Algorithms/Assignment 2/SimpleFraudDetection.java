import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/* Tanmay Sharma - DSA Assignment 2 Task 1 : Simple Fraud Detection
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/376242/problem/A
 *
 *
 * Dates are sorted using radix sort and Trailing Day Values are sorted using
 * merge sort.
 * */



public class SimpleFraudDetection {



    /* Radix Sort is used for sorting the dates (keys) and the Amount (values)
     * related to them.
     *
     * Radix Sort is just a combination of various counting sorts.
     *
     * Time Complexity - O(n)
     */

    public static void countSort(String[] arr, int div, int mod, int range, Node[] data) {



        String[] ans = new String[arr.length];



        Node[] ansData = (Node[]) new Node[arr.length];


        // A frequency Array - used for storing frequency of each values.
        int[] farr = new int[range];


        for (String s : arr) {

            farr[Integer.parseInt(s, 10) / div % mod]++;

        }



        // The frequency array should be convert it into prefix sum array

        for (int i = 1; i < farr.length; i++) {

            farr[i] += farr[i - 1];

        }





        // The ans array is getting filled (stable sorting).

        for (int i = arr.length - 1; i >= 0; i--) {

            int pos = farr[Integer.parseInt(arr[i], 10) / div % mod] - 1;

            ans[pos] = arr[i];

            ansData[pos] = data[i];

            farr[Integer.parseInt(arr[i], 10) / div % mod]--;

        }


        // The original Array is getting filled with the help
        // of 'ans' array.
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ans[i];
            data[i] = ansData[i];
        }
    }




    public static void sortDates(String[] arr, Node[] data) {

        // Sorting by Days
        countSort(arr, 1000000, 100, 32, data);

        // Sorting by Months
        countSort(arr, 10000, 100, 13, data);

        // Sorting by Year.
        countSort(arr, 1, 10000, 2501, data);

    }





    // For sorting trailing day values.
    // Time Complexity - O(n*logn)
    public static List<Double> sorted(List<Double> list) {
        
        if (list.size() < 2) {
            return list;
        }
        
        int mid = list.size()/2;
        
        return merged(
                sorted(list.subList(0, mid)),
                sorted(list.subList(mid, list.size())));
    }

    private static List<Double> merged(List<Double> left, List<Double> right) {
        
        int leftIndex = 0;
        
        int rightIndex = 0;
        
        List<Double> merged = new ArrayList<Double>();

        
        while (leftIndex < left.size() && rightIndex < right.size()) {
            
            if (left.get(leftIndex) < right.get(rightIndex)) {
                merged.add(left.get(leftIndex++));
            } 
            else {
                merged.add(right.get(rightIndex++));
            }
        
        }
        
        merged.addAll(left.subList(leftIndex, left.size()));
        
        merged.addAll(right.subList(rightIndex, right.size()));
        
        return merged;
    }




    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int d = sc.nextInt();

        sc.nextLine();


        // Array to store Dates.
        String[] dates = new String[n];

        // Array to Node.
        Node[] data = (Node[]) new Node[n];

        for (int i = 0; i < n; i++) {
            String[] input = (sc.nextLine()).split(" ");
            String Date = input[0];
            String[] DateValues = Date.split("-");
            String currentDate = DateValues[2] + DateValues[1] + DateValues[0];
            dates[i] = currentDate.toString();
            double amountDay = Double.parseDouble(input[1].substring(1));

            Node newNode = new Node(Date, amountDay);
            data[i] = newNode;
        }



        // Radix Sort to sort dates along with values.
        sortDates(dates, data);



        // Using Java's Date feature to form a range of dates
        // from first date in the date array to the last date
        // (which are sorted already in the order)
        String startDate = data[0].dateInString;
        String endDate = data[data.length - 1].dateInString;

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<String> allDates = new ArrayList<>();

        while (!start.isAfter(end)) {

            // all dates between the range are getting added
            // in the allDates array.
            allDates.add(start.toString());

            start = start.plusDays(1);

        }


        // The needed queue 'q'.
        Queue<List<Node>> q = new LinkedList<>();

        int curr = 0;

        // The Date and Values getting added in the queue.
        // Same values getting added as list.
        for (int i = 0; i < allDates.size(); i++) {
            List<Node> newList = new ArrayList<>();
            for (int j = curr; j < data.length; j++) {
                if ((allDates.get(i)).equals(data[j].dateInString)) {

                    newList.add(data[j]);

                } else {

                    curr = j;

                    break;

                }
            }
            if (newList.isEmpty()) {

                // if the dates is not in the range
                // then 0.00 value is added.

                newList.add(new Node(allDates.get(i), 0.00));

            }

            q.add(newList);

        }

        // Size of the queue
        int size = q.size();


        // The Result value.
        int numberOfAlerts = 0;

        // Variable used for counting how many
        // days passed to reach the trailing days
        // count.
        int daypassed = 0;

        Queue<List<Node>> trailingDaysQueue = new LinkedList<>();

        for (int startIndex = 0; startIndex < size; startIndex++) {

            daypassed++;
            if (daypassed <= d) {

                // Days which are in the trailing days
                // in the trailingDaysQueue.
                trailingDaysQueue.add(q.poll());

                continue;

            }

            // The value of the trailing Days.
            List<Double> trailingDayValues = new ArrayList<>();

            for (List<Node> l : trailingDaysQueue) {
                double sum = 0.0;
                for (Node node : l) {
                    sum += node.getValue();
                }
                trailingDayValues.add(sum);
            }

            List<Double> sortedTrailingDayValues = sorted(trailingDayValues);


            // The current day value which we want to check.
            // The work will be done on the current node for
            // a single node.
            List<Node> thatDayNode = q.poll();

            // Calling poll() method automatically removes the
            // node from the queue indicating that the day is already
            // passed.

            if (thatDayNode.get(0).getValue() != 0) {

                double median = 0.00;

                if (d % 2 == 0) {
                    // Case 1 : When number of trailing days
                    // are even.
                    int index1 = d / 2 - 1;

                    int index2 = d / 2;

                    double mid1 = sortedTrailingDayValues.get(index1);

                    double mid2 = sortedTrailingDayValues.get(index2);

                    median = (mid1 + mid2);
                } else {

                    // Case 2 : when the trailing days are odd

                    int index1 = d / 2;

                    median = sortedTrailingDayValues.get(index1) * 2;

                }

                // Checking whether it is a fault or not.
                double amount = 0.0;
                for (Node nnode : thatDayNode) {
                    amount += nnode.getValue();

                    if (amount >= median) {
                        numberOfAlerts++;
                    }

                }

            }

            trailingDaysQueue.add(thatDayNode);

            trailingDaysQueue.poll();

        }

        System.out.println(numberOfAlerts);

        sc.close();
    }
}


// A Node class required for storing the dates and their
// amount together.
class Node {

    // Date Class used again.
    private final Date date;

    // Date in String format.
    String dateInString;

    // The amount related to Date.
    private double value;

    Node left, right;

    public Node(String date, double value) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        this.date = sdformat.parse(date);

        this.value = value;

        this.dateInString = date;

        left = right = null;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public int getDay() {
        // Getting the date's day. (In Integer)
        String[] seperated = dateInString.split("-");
        return Integer.parseInt(seperated[2]);
    }

    public void setValue(double newValue) {
        this.value = newValue;
    }
}