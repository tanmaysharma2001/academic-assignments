/* Tanmay Sharma
 * t.sharma@innopolis.university
 * BS21-07
 * CodeForces Problem B - Command Shells without rollbacks.
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/368875/problem/B
 * 20/2/2022
 *
* */

import java.util.Scanner;

public class CommandShellWithoutRollBacks {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n;

        n = sc.nextInt();

        sc.nextLine();

        DoubleHashSet<String> set = new DoubleHashSet<>();

        String inputLine;

        for(int i = 0; i < n; i++) {


            inputLine = sc.nextLine();


            String[] inputs = inputLine.split(" ");


            if(inputs[0].equals("NEW")) {

                if(inputs[1].charAt(inputs[1].length() - 1) == '/') {

                    String altered = inputs[1].substring(0, inputs[1].length() - 1);

                    if (set.contains(inputs[1]) || set.contains(altered)) {

                        System.out.println("ERROR: cannot execute NEW " + inputs[1]);

                    } else {

                        set.add(inputs[1]);

                    }
                }
                else {

                    String altered = inputs[1] + "/";

                    if (set.contains(inputs[1]) || set.contains(altered))
                        System.out.println("ERROR: cannot execute NEW " + inputs[1]);
                    else {

                        set.add(inputs[1]);

                    }
                }
            }
            else if(inputs[0].equals("REMOVE")){
                if (!set.contains(inputs[1])) {
                    System.out.println("ERROR: cannot execute REMOVE " + inputs[1]);
                } else {
                    set.remove(inputs[1]);
                }
            }
            else if(inputs[0].equals("LIST")) {
                set.traversal();
            }
        }



    }
}

interface ISet<T> {

    // Add items(Keys) to the set.
    // If an item is already present in the set,
    // then it is ignored.
    // Time Complexity is: O(1) in best case,
    // O(1) in average case, O(n) in worst case.
    void add(T item);




    // Removes the element from the hash set
    // If an element is not present in the set
    // then returns nothing.
    // Time Complexity is O(1) in best case,
    // O(n) in worst case.
    void remove(T item);




    // If the provided element is present in the
    // set then returns true otherwise not.
    // Time Complexity is O(1) in best case,
    // O(n) in worst case.
    boolean contains(T item);




    // Returns the number of unique elements in the set.
    // Returns zero if no element is present in the set.
    int size();


    // Returns whether there are any elements present in the
    // hash set. Returns true if no element are present, and
    // false if some element is present.
    boolean isEmpty();


}


class DoubleHashSet<T> implements ISet<T> {



    // The number of elements present in the hashset.
    // Will be increased or decreased as per the addition
    // or removal of elements from the hashset.
    private int size = 0;



    // The Capacity of the HashTable.
    // Also a prime Number which allows easy allocation
    // of elements in the HashTable array.
    private final int capacity = 3100;





    // A prime Number used in hashCode to generate a
    // Unique index for the element in the hashTable.
    private final int totalPrimeSize = 3089;


    // The hashTable of the generic Object Type
    // which can store any Object.
    // Will be initialized in the constructor.
    private T[] hashTable;


    private int[] status = new int[capacity];



    // Node class for the Doubly Linked List that will be used in
    // storing the elements of the hashTable for faster access and storing their
    // indexes in the hashtable.
    // Reduces the time complexity by a huge fraction.
    private class Node<T> {

        // Generic Element in the
        // Node of the Linked List.
        T data;


        // Index of the element stored in
        // the hashTable.
        int index;



        // Reference to the previous Node.
        Node<T> prev = null;

        // Reference to the next Node.
        Node<T> next = null;


        // Constructor for the Node to initialize
        // a general Node in the Linked List.
        // Takes in two parameters: data and index.
        Node(T data, int index) {
            this.data = data;
            this.index = index;
        }
    }


    private int appeared[] = new int[capacity];




    private int removed[] = new int[capacity];


    // Head Node of the Doubly Linked List.
    private Node<T> head = null;




    // Tail Node of the Doubly Linked List.
    private Node<T> tail = null;




    // HashFunction1() method which takes the hashCode of the item
    // passed in by using the default hashCode method to generate a hashIndex
    // for the element.
    // Java's hashCode() method helps in generating a unique hashCode for the
    // element.
    private int hashFunction1(T givenItem) {

        // Java's Default HashCode() method.
        int indexOfElement = givenItem.hashCode() % capacity;


        // Checks whether the generated index is less than 0,
        // if it is, then it is made positive by adding the capacity
        // to it.
        if(!(indexOfElement >= 0)){
            indexOfElement += capacity;
        }

        // Returns the Modulo of the unique index
        // by capacity to generate an index within
        // the capacity of the HashTable.
        indexOfElement %= capacity;

        return indexOfElement;

    }


    long hashCode2(T item) {


        // converting the Object item to string using tostring() method.
        String value = item.toString();
        
        
        
        // a prime number
        long hash = 17203;
        
        
        
        for(int i = 0; i < value.length(); i++) {
            
            
            
           
            // increasing th ehash function based on the ascii value of the character
            // of the strings.
            hash = 33 * hash + (int)value.charAt(i);
        
        
        
        
        }
        
        
        

        // returning the absolute value of the hash value.
        return Math.abs(hash);
    
    
    
    }


    // HashFunction2() method is the method which generates an index for the element
    // whenever there is a collision in the hashSet while inserting elements in the list.
    // A manually created hashCode2() is used for generating the hashcode for every element
    // which will be added. Default Java's hashCode method is not used.
    private int hashFunction2(T givenItem) {

        
        // Genreating unique hashindex usign a prime number
        int index = (int) (hashCode2(givenItem) % totalPrimeSize);
        
        
    
    
        // returning the absolute value of the index
        return Math.abs((totalPrimeSize - index) % totalPrimeSize);

    
    
    }



    // The constructor for the HashSet.
    DoubleHashSet() {
        this.hashTable = (T [])new Object[capacity];
        for(int i = 0; i < capacity; i++)
            hashTable[i] = null;
    }


    // The copy constructor.

//    DoubleHashSet(DoubleHashSet hashSet) {
//        this.hashTable = new Object[hashSet.capacity];
//        for(int i = 0; i < hashSet.capacity; i++)
//            this.hashTable[i] = (T) hashSet.hashTable[i];
//    }

    public void traversal() {

        // Traversing through the linked list
        // to extract all the elements' indexes
        // and store them in the indexes array.
        Node<T> current = head;
        // Traversing through the linked list
        // till the end.
        for(int i = 0; current != null; i++) {
            System.out.print((current.data).toString() + " ");
            current = current.next;
        }
    }

    public void add(T item) {

        // HashIndexes of the Item generated by both hashFunctions.
        int hashIndex1 = hashFunction1(item);
        int hashIndex2 = hashFunction2(item);



        int c = 0;



        while(true) {
            // General Principle of Double Hashing.
            // c is increased every time there is a
            // collision while entering the element into the
            // hashtable
            int index = (hashIndex1 + c*hashIndex2) % capacity;


            // Checks if the space element is getting alloted to
            // is free or not.

            if(appeared[index] == 0 || (removed[index] == 1)) {

                removed[index] = 0;


                appeared[index] = 1;


                size++;

                Node<T> newNode = new Node<>(item, index);


                // Checks if there are no elements present in the list.
                if(head == null) {


                    head = newNode;


                    tail = newNode;


                    head.prev = null;


                    tail.next = null;
                }

                else {


                    tail.next = newNode;


                    newNode.prev = tail;


                    tail = newNode;


                    tail.next = null;
                }


                hashTable[index] = item;

                break;
            }

            c++;

        }
    }

    public void remove(T item) {

        if (contains(item)) {
            
            
            // HashIndex1 generated by hashFunction 1
            final int hashIndex1 = hashFunction1(item);
            
            
            
            
            
            
            // HashIndex2 generated by hashFunction2
            final int hashIndex2 = hashFunction2(item);


            
            
            
            int c = 0;

            while (true) {
                int index = (hashIndex1 + c * hashIndex2) % capacity;

                if (appeared[index] == 1 && removed[index] == 0 && hashTable[index].equals(item)) {
                    status[index] = 0;
                    size--;

                    removed[index] = 1;

                    Node<T> current = head;

                    while (current != null) {

                        // comparing objects using equals() method.
                        if (item.equals(current.data)) {

                            if (current == head) {

                                head = head.next;

                            } else {

                                current.prev.next = current.next;

                            }

                            size--;

                            current = current.next;
                        } else {


                            current = current.next;

                        }
                    }

                    break;

                }
                c++;
            }
        } else {
            throw new IllegalArgumentException("Item is not in the set");
        }

    }

    public boolean contains(T item) {



        final int hashIndex1 = hashFunction1(item);


        final int hashIndex2 = hashFunction2(item);


        int c = 0;


        while(true) {
            // generation of index

            int index = (hashIndex1 + c*hashIndex2) % capacity;


            // checking index
            if(appeared[index] == 0) {


                // return false if not available
                return false;


            }
            else if(removed[index] == 0 && hashTable[index].equals(item)) {


                // returns true if it is in the hashtable.
                return true;

            }
            else if(index == hashIndex1 && !(c <= 0)) {

                // returns false if the above conditions is not satisfied.
                return false;

            }
            c++;
        }
    }


    // Returns the size of the hashtable i.e., the
    // number of elements present in the hashtable.
    public int size() {
        return size;
    }



    // Returns the capacity i.e., the amount of
    // elememts that the hashtable can hold.
    public int getCapacity() {
        return capacity;
    }


    // checks whether the hashtable is empty or not.
    public boolean isEmpty() {
        return size == 0;
    }
}
