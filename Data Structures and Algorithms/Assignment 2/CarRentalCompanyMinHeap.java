import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/* Data Structures and Algorithms Assignment 2 Task C
 * Car Rental Company (min-heap test).
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/376242/problem/C
 *
 * Tanmay Sharma
 * BS2107
 *
 * Priority Queue is implemented using min binary heap.
 * The program that takes a sequence of instructions to
 * manipulate a set of unordered branches represented
 * by their names and penalty value. It outputs the
 * name of the branch with the minimum penalty every
 * time it is required.
 *
 * Priority Queue is used for the above mentioned problem.
 *
 *  */
public class CarRentalCompanyMinHeap {

    public static void main(String[] args) {

        priorityQueue<Integer, String> queue = new priorityQueue<>();

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        sc.nextLine();

        for(int i = 0; i < n; i++) {
            String[] inputs = (sc.nextLine()).split(" ");
            if(inputs[0].equals("ADD")) {
                String branch = inputs[1];
                int penalty = Integer.parseInt(inputs[2]);
                queue.insertItem(penalty, branch);
            }
            else if(inputs[0].equals("PRINT_MIN")) {
                System.out.println((queue.extractMin()).toString());
            }
        }

    }
}

interface IPriorityQueue<K,V> {
    void insert(Node item);
    Node findMin();
    Node extractMin();
    void decreaseKey(Node item, K newKey);
    void delete(Node item);
    void union(priorityQueue anotherQueue);
}

// Node class to store the Key-Value pair.
class Node<K extends Comparable, V> {
    K key;
    V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return this.value;
    }

    public K getKey() {
        return this.key;
    }

    public void setKey(K newKey) {
        this.key = newKey;
    }

    public int compareTo(Node anotherNode) {
        return (this.key).compareTo(anotherNode.getKey());
    }

    public String toString() {
        return getValue().toString();
    }
}



class priorityQueue<K extends Comparable<? super K>, V> implements IPriorityQueue<K, V> {

    // Heap Array or List.
    private List<Node> Heap;


    private int capacity;
    private int size = 0;

    public priorityQueue(int capacity) {
        this.capacity = capacity;
        Heap = new ArrayList<>(capacity);
    }



    public priorityQueue() {
        Heap = new ArrayList<>(capacity);
    }





    // Gets the parent index of a child.
    // Time Complexity: O(1)
    private int parent(int i) {
        if(i==0) {
            return 0;
        }

        return (i-1)/2;
    }





    // Gets the index of the left child in the heap
    // array.
    // Time Complexity: O(1)
    private int leftChild(int i) {
        return (2*i + 1);
    }




    // Gets the index of the right child in the heap
    // array.
    // Time Complexity: O(1)
    private int rightChild(int i) {
        return (2*i + 2);
    }






    // Swapping two indexes in the heap
    // array.
    // Time Complexity: O(1)
    void swap(int x, int y) {
        Node temp = Heap.get(x);
        Heap.set(x, Heap.get(y));
        Heap.set(y, temp);
    }




    // Returns the size of the heap
    // at a particular instance.
    // Time Complexity: O(1)
    public int size() {
        return Heap.size();
    }





    // Heapify Down method.
    // It swaps a node that is too small with its
    // largest child (thereby moving it down) until
    // it is at least as large as both nodes below it.
    // Time Complexity: O(n)
    private void heapify_down(int i)
    {
        // get left and right child of node at
        // the index `i`
        int leftchildren = leftChild(i);
        int rightchildren = rightChild(i);




        // The value by which swapping is done.
        int smallestChild = i;



        // A[i] gets compared with its left and right child
        // and the smalles value is found.


        if(leftchildren < size()) {
            if(Heap.get(leftchildren).compareTo(Heap.get(i)) < 0) {
                smallestChild = leftchildren;
            }
        }


        if(rightchildren < size()) {
            if(Heap.get(rightchildren).compareTo(Heap.get(smallestChild)) < 0) {
                smallestChild = rightchildren;
            }
        }


        if (smallestChild == i) {
            return;
        }
        swap(i, smallestChild);
        // Swapped with a child which has lesser value.

        heapify_down(smallestChild);
        // then the heapify-down method is called


    }





    // Heapify-up procedure done recursively.
    // It swaps a node that is too large with
    // its parent (thereby moving it up) until
    // it is no larger than the node above it.
    // Time Complexity: O(nlogn)
    private void heapify_up(int i)
    {
        // Checking if the node present at index 'i' and the parent
        // of it doesn't follow the heap property
        if (i <= 0 || Heap.get(parent(i)).compareTo(Heap.get(i)) <= 0) {
            return;
        }

        // swapping the parent and the child
        // heap property is not followed.
        swap(i, parent(i));

        // Heapify-up method is called on the parent.
        heapify_up(parent(i));
    }


    // Inserting a key-value pair.
    // by forming a node.
    // Requires Heapify-up, so time complexity is O(nlogn)
    public void insertItem(K key, V value) {
        Node node = new Node(key, value);
        insert(node);
    }


    // Inserting the Node in the heap
    // array.
    @Override
    public void insert(Node item) {
        Heap.add(item);
        int index = size() - 1;
        heapify_up(index);
    }


    // Finding the minimum value in the
    // array.
    // Time Complexity is O(1)
    @Override
    public Node findMin() {
        try {
            if(size() == 0) {
                throw new Exception("Index is out of range (Heap underflow)");
            }

            return Heap.get(0);
        }
        catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }


    // Extracting the minimum Value from the Heap array.
    // Also removes it from the array.
    // Requires heapify-down, so time complexity is O(n)
    @Override
    public Node extractMin() {
        try {
            if(size() == 0) {
                throw new Exception("Index is out of range (Heap underflow)");
            }

            Node root = Heap.get(0);

            Heap.set(0, Heap.get(Heap.size() - 1));

            Heap.remove(Heap.size() - 1);

            // Heapifying down as the value
            // in the root is the smallest in
            // the array.
            heapify_down(0);

            return root;
        }
        catch(Exception ex) {
            System.out.println(ex);
            return null;
        }
    }


    // Decrease the key of a given Node.
    // Conditions apply that the new key must be
    // less than the present key (min heap).
    // Time Complexity: O(1)
    @Override
    public void decreaseKey(Node item, K newKey) {
        if((item.getKey().compareTo(newKey) < 0)) {
            item.setKey(newKey);
        }
        else {
            System.out.println("The provided key is greater than the current key.");
        }
    }



    
    // Delete method.
    // Depending on the value, the time complexity can be:
    // either O(n) or O(nlogn)
    @Override
    public void delete(Node item) {


        int index = Heap.indexOf(item);

        // The item get's replaced with the element placed farthest.
        Heap.set(index, Heap.get(Heap.size() - 1));

        // We remove the farthest element from Heap List
        Heap.remove(Heap.size() - 1);

        // value in replacement node < its parent node
        // we heapify_up otherwise heapify_down.
        if(Heap.get(index).compareTo(Heap.get(parent(index))) < 0) {
            heapify_up(index);
        }
        else {
            heapify_down(index);
        }
    }


    
    
    // Union of two priority queues will be just adding the values one by one
    // as they automatically gets arranged.
    // Time Complexity: O(n)
    @Override
    public void union(priorityQueue anotherQueue) {

        int size = anotherQueue.size();
        for(int i = 0; i < size; i++) {
            insert(anotherQueue.extractMin());
        }

    }
}