import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


/* Data Structures and Algorithms Assignment - 2 Task B
 * Range Queries.
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/376242/problem/B
 *
 * Tanmay Sharma
 * BS2107
 *
 * The following program uses BTree (of order 3) to analyze a large lists of data
 * as it is well suited to answer questions based on them efficiently.
 *
 * B-tree is particularly well-suited for range queries, as consequent items are
 * often located in the same array, leveraging high-performance cache.
 *
 *  */

public class RangeQueries {

    public static void main(String[] args) throws ParseException {
        BTree<Date, Integer> b = new BTree<>(3);

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        sc.nextLine();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        int i = 0;
        while (i < n) {
            String[] inputs = (sc.nextLine()).split(" ");

            if(inputs[1].equals("DEPOSIT")) {
                String dateInString = inputs[0];

                Date date = sdformat.parse(dateInString);
                int amount = Integer.parseInt(inputs[2]);
                b.add(date, amount);
            }
            else if(inputs[1].equals("WITHDRAW")) {
                String dateInString = inputs[0];
                Date date = sdformat.parse(dateInString);
                int amount = Integer.parseInt(inputs[2]);
                amount = amount * -1;
                b.add(date, amount);
            }
            else if(inputs[0].equals("REPORT")){
                String dateFromInString = inputs[2];
                String dateToInString = inputs[4];
                Date startDate = sdformat.parse(dateFromInString);
                Date endDate = sdformat.parse(dateToInString);
                int diffAmount = b.Report(startDate, endDate);
                System.out.println(diffAmount);
            }
            i++;
        }

        sc.close();

    }
}


// If n>= 1, then for any n-key B-tree of height h and minimum degree t>=2,
// h >= log (n+1)/2 base t.
interface RangeMap<K, V> {

    // Number of nodes in the tree.
    int size();

    // Check whether the tree is empty or not.
    boolean isEmpty();

    // Add the node in the tree.
    void add(K key, V value);

    // Check whether the tree contains the Key.
    boolean contains(K key);

    // Looking up a key in the tree and
    // returns it's amount.
    V lookup(K key);

    // Get the values from a range of keys.
    List<V> lookupRange(K from, K to) throws ParseException;

    // Optional: Remove a key from the Tree.
    V remove(K key);
}


// Node class representing node of a tree.
class NodeOfTree <K extends Comparable<? super K>, V extends Object> {
    private K key;
    private V value;
    private int valueInInt;

    public NodeOfTree(K key, V value) {
        this.key = key;
        this.value = value;
        this.valueInInt = (int) value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public int getValueInInt() {
        return this.valueInInt;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void addValue(V newValue) {
        int result = this.valueInInt + (int) newValue;
        this.valueInInt = result;
        this.value = (V) ((Object) result);
    }

    public int compareTo(NodeOfTree node) {
        return this.key.compareTo((K) node.getKey());
    }

    public String toString() {
        return this.key.toString() + " " + this.value.toString();
    }
}


// BTree using K as an extendable Comparable Object.
class BTree<K extends Comparable<? super K>, V> implements RangeMap<K, V> {

    // Order of the BTree.
    private final int T;

    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(K key) {
        return this.Search(root, key) != null;
    }


    // Optional
    @Override
    public V remove(K key) {
        return null;
    }

    // Node creation
    class Node {

        int n;

        // Array of the Key-Value pair in the tree.
        // The maximum length of the array is 2*T - 1
        // where the order of the Tree is T.
        NodeOfTree[] nodeOfTrees = new NodeOfTree[2*T - 1];

        // Array of the children of nodes connected to
        // particular Node.
        Node[] child = (Node[]) new BTree.Node[2*T];

        boolean leaf = true;

        // To find Key in nodeOfTree array.
        // Returns the index of the passed key
        // in the array.
        public int Find(K k) {
            for (int i = 0; i < this.n; i++) {
                if (this.nodeOfTrees[i].getKey().compareTo(k) == 0) {
                    return i;
                }
            }
            return -1;
        };
    }



    // Lookup method used to find a value
    // to a passed Key.
    // Time Complexity: O(logn)
    @Override
    public V lookup(K key) {

        // Search begins from root node.
        Node node = Search(root, key);

        // If the node is null,
        // then return null.
        if(node == null) {
            return null;
        }

        // after finding the node we will perform
        // search operation in the nodeOfTrees array
        // for the index
        int index =  node.Find(key);

        return (V) node.nodeOfTrees[index].getValue();
    }


    private Node root;

    public BTree(int t) {
        T = t;
        this.root = new Node();
        root.n = 0;
        root.leaf = true;
    }


    // Report function returns the change of amount in values
    // from start Date to end Date.
    // Time Complexity: O(nlogn)
    public int Report(K from, K to) throws ParseException {
        List<V> listInV = lookupRange(from, to);

        int sum = 0;

        for(Object V : listInV) {
            int value = (Integer) V;
            sum += value;
        }
        return sum;
    }


    // lookupRange function returns the list of Values i.e., List<V>.
    // It uses Data types like LocalDate and Date.
    //
    // ************* Trick ********************
    // Conversion of Date from LocalDate is done to get range of dates
    // from starting Date to ending date.
    // This includes the dates that were not being added in the Tree.
    // It heavily reduces the time complexity of rather than searching for every node,
    // for the dates.
    // ************** Trick *******************
    public List<V> lookupRange(K startDate, K endDate) throws ParseException {

        // Conversion of Date to LocalDate with a way of creating an Instance Object.
        LocalDate start = Instant.ofEpochMilli(((Date) startDate).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = Instant.ofEpochMilli(((Date) endDate).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        // The List<String> allDates will contain the dates between the ranges.
        List<String> allDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            allDates.add(start.toString());
            start = start.plusDays(1);
        }

        // The result List.
        List<V> values = new ArrayList<>();

        int sum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < allDates.size(); i++) {
            Date date = sdf.parse(allDates.get(i));
            V v = findValue(root, (K) date);
            if(v == null) {
                continue;
            }
            else {
                values.add(v);
            }
        }

        return values;
    }

    // Finding the value of the Given Node.
    // Time Complexity: O(logn)
    private V findValue(Node x, K key) {
        int i = 0;

        if (x == null)
            return null;

        for (i = 0; i < x.n; i++) {

            // Dates default compareTo method is used to compare
            // two dates which will result in an integer value
            // which will be compared using an another integer value
            // which will give us a boolean.

            if (key.compareTo((K) x.nodeOfTrees[i].getKey()) < 0) {
                break;
            }
            if (key.compareTo((K) x.nodeOfTrees[i].getKey()) == 0) {
                return (V) x.nodeOfTrees[i].getValue();
            }
        }

        if (x.leaf) {
            return null;
        } else {
            return findValue(x.child[i], key);
        }
    }

    // Search key
    // Time Complexity: O(logn)
    private Node Search(Node x, K key) {
        int i = 0;
        if (x == null)
            return x;
        for (i = 0; i < x.n; i++) {
            // System.out.println(x.nodeOfTrees[i].getValue());
            if (key.compareTo((K) x.nodeOfTrees[i].getKey()) < 0) {
                break;
            }
            if (key.compareTo((K) x.nodeOfTrees[i].getKey()) == 0) {
                return x;
            }
        }
        if (x.leaf) {
            return null;
        } else {
            return Search(x.child[i], key);
        }
    }

    // Splitting the node
    // It takes O(logn) time.
    private void Split(Node x, int pos, Node y) {
        Node z = new Node();
        z.leaf = y.leaf;
        z.n = T - 1;
        if (T - 1 >= 0) System.arraycopy(y.nodeOfTrees, T, z.nodeOfTrees, 0, T - 1);
        if (!y.leaf) {
            if (T >= 0) System.arraycopy(y.child, T, z.child, 0, T);
        }
        y.n = T - 1;
        if (x.n + 1 - (pos + 1) >= 0) System.arraycopy(x.child, pos + 1, x.child, pos + 1 + 1, x.n + 1 - (pos + 1));
        x.child[pos + 1] = z;

        if (x.n - pos >= 0) System.arraycopy(x.nodeOfTrees, pos, x.nodeOfTrees, pos + 1, x.n - pos);
        x.nodeOfTrees[pos] = y.nodeOfTrees[T - 1];
        x.n = x.n + 1;
    }

    /* When you add a node, if that causes a split
     * you insert a key into the node above. If that
     * causes as split, then you do the same thing
     * one level up, etc., until you get to the root.
     * So the complexity is O(logN).
     * */
    @Override
    public void add(K key, V value) {


        Node node = Search(root, key);


        if(node != null) {


            int index =  node.Find(key);


            node.nodeOfTrees[index].addValue(value);

        }
        else {


            NodeOfTree newNode = new NodeOfTree(key, value);


            Node r = root;


            if (r.n == 2 * T - 1) {



                Node s = new Node();



                root = s;



                s.leaf = false;



                s.n = 0;



                s.child[0] = r;



                Split(s, 0, r);



                insertValue(s, newNode);

            } else {
                insertValue(r, newNode);
            }
        }
    }

    // Insert the node
    private void insertValue(Node x, NodeOfTree newNode) {

        if (x.leaf) {
            int i = 0;
            for (i = x.n - 1; i >= 0 && newNode.compareTo(x.nodeOfTrees[i]) < 0; i--) {
                x.nodeOfTrees[i + 1] = x.nodeOfTrees[i];
            }
            x.nodeOfTrees[i + 1] = newNode;
            x.n = x.n + 1;
            this.size++;
        } else {
            int i = 0;
            for (i = x.n - 1; i >= 0 && newNode.compareTo(x.nodeOfTrees[i]) < 0; i--) {
            }
            ;
            i++;
            Node tmp = x.child[i];
            if (tmp.n == 2 * T - 1) {
                Split(x, i, tmp);
                if (newNode.compareTo(x.nodeOfTrees[i]) > 0) {
                    i++;
                }
            }
            insertValue(x.child[i], newNode);
            this.size++;
        }

    }
}