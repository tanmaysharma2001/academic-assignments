/* Tanmay Sharma
 * t.sharma@innopolis.university
 * Codeforces Problem A - Bounded Commands Queue
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/368875/problem/A
 * BS21-07
* */

import java.util.Scanner;

public class BoundedCommandsQueue {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int n, k;

        n = sc.nextInt();
        k = sc.nextInt();

        sc.nextLine();


        DoublyLinkedCircularBoundedQueue<String> queue = new DoublyLinkedCircularBoundedQueue<>(k);


        String inputLine;


        for(int i = 0; i < n; i++) {
            inputLine = sc.nextLine();
//            if(inputLine.charAt(inputLine.length() - 1) == '/') {
//                continue;
//            }
            queue.offer(inputLine);
        }

        while(!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}

interface ICircularBoundedQueue<T> {
    // inserts an element to the back of the queue
    // follows the queue principal FIFO (first-in-first-out)
    // shift the queue when capacity is exceeded to store
    // the new element.
    void offer(T value);



    // removes an element from the front of the queue
    // Time Complexity is O(1) of average case, worst case and amortized case.
    T poll();



    // Returns the top most element in the queue.
    // Time Complexity is O(1) of average case, worst case and amortized case.
    T peek();


    // Removes all the elements from the queue.
    void flush();


    // Returns whether the queue is empty or not.
    boolean isEmpty();


    // Returns whether the queue is Full or not.
    boolean isFull();


    // Returns the size of the queue i.e., the number
    // of elements present in the queue.
    int size();


    // Returns the capacity of the queue i.e., the number
    // of elements the queue can store.
    int capacity();
}

class DoublyLinkedCircularBoundedQueue<T> implements ICircularBoundedQueue<T> {

    // The number of elements present in the queue.
    // Increased or decreased when an element is added or removed.
    private int size = 0;


    // Initial Capacity of the array.
    // Initialised by the Constructor.
    // If not then take the default Capacity
    // value which is 10.
    private int initialCapacity;


    // The default Capacity of the array
    // which is taken if the initial Capacity
    // is not initialised.
    private final int defaultCapcity = 10;


    // Node class used to create Nodes of Data
    // for the Linked List.
    private static class Node<T> {

        // Value of the Node.
        private T value;

        // Previous Node. Null for the head Node.
        private Node<T> prev;

        // Next Node. Null for the tail Node.
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            // Assigning value and nodes using 'this'.
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this(null, value, null);
        }

        public String toString() {
            return value.toString();
        }
    }

    // Head Node for the Linked List.
    private Node<T> head = null;

    // Tail Node for the Linked List.
    private Node<T> tail = null;


    // Public Constructor of the Queue.
    // Parameters: initialCapacity.
    public DoublyLinkedCircularBoundedQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    // Constructor Overloading. If no parameter is passed
    // in the constructor.
    public DoublyLinkedCircularBoundedQueue() {
        initialCapacity = defaultCapcity;
    }

    public void traversal() {
        Node<T> current = head;
        while(current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }

    // Time Complexity is O(1) in avergae case, worst case or amortized case.
    @Override
    public void offer(T value) {
        Node<T> newNode = new Node<>(value);

        if(head == null) {
            // no element are there in the queue,
            // then the element is inserted at the head.
            head = tail = newNode;

            head.prev = null;

            tail.next = null;

            // size needs to be increased.
            size++;
        }
        else if(isFull()) {
            // insert at rear and remove from front

            // poll function call removes the element at
            // front of the queue.
            poll();

            // setting the next node of tail of the linked list to
            // the new element.
            // setting the previous of the new node to tail
            // and then assigning the new node to the tail
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;

            // now as the new element has become the tail
            // next of the tail is again assigned to null
            tail.next = null;

            // size is increased as a new element is added.
            size++;
        }
        else {
            // the general case where the element is added
            // to the back of the linked list



            // setting the next node of tail of the linked list to
            // the new element.
            // setting the previous of the new node to tail
            // and then assigning the new node to the tail
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;


            // size is increased as a new element is added.
            size++;
        }
    }

    @Override
    public T poll() {

        // removes an element from the front of the queue.

        // the value which is need to be returned.
        T value = head.value;

        // head is set to the next of the head node
        // by this the first element i.e., the previous
        // head will become unavailable and the space
        // acquired by that would be taken care by
        // Java Garbage Collector
        head = head.next;

        // size is decreased as an element is removed
        // from the linked list.
        size--;

        // Returning the value
        return value;
    }


    // Returns the first element of the queue that is
    // the head element.
    @Override
    public T peek() {
        return head.value;
    }



    // removes all the element from the linked list
    // by basically setting the head and tail node to null,
    // so every element becomes inaccessible
    @Override
    public void flush() {
        head = null;
        tail = null;
        size = 0;
    }



    // checks whether the queue is empty or not.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }



    // check whether the queue is full or not.
    @Override
    public boolean isFull() {
        return size == initialCapacity;
    }



    // return the number of elements in the queue.
    @Override
    public int size() {
        return size;
    }


    // returns the capacity of the elements in the queue.
    @Override
    public int capacity() {
        return initialCapacity;
    }

}
