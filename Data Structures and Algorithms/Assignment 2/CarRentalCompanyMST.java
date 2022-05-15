import java.util.*;

/* DSA Assignment 2 Task D : Car Rental Company (MST)
 * Problem Description: https://codeforces.com/group/M5kRwzPJlU/contest/376242/problem/D
 * Tanmay Sharma
 * BS2107
 *
 * Minimum Spanning Tree with Prim's Algorithm using implementation
 * of priority queue.
* */

public class CarRentalCompanyMST {
    public static void main(String[] args) {
        Graph<NodeOfGraph, Double> graph1 = new Graph<>();

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        sc.nextLine();

        for (int i = 0; i < n; i++) {
            String[] inputs = (sc.nextLine()).split(" ");

            if (inputs[0].equals("ADD")) {
                NodeOfGraph newNode = new NodeOfGraph(inputs[1], Integer.parseInt(inputs[2]));
                graph1.insertVertex(newNode);
            } else if (inputs[0].equals("CONNECT")) {
                graph1.insertEdge(inputs[1], inputs[2], Integer.parseInt(inputs[3]));
            } else if (inputs[0].equals("PRINT_MIN")) {
                graph1.prims();
            }
        }

        sc.close();
    }
}

interface IGraph<V,E> {

    // inserting the vertex
    V insertVertex(V v);

    // inserting the edge
    double insertEdge(String from, String to, E w);

    // removing the vertex.
    void removeVertex(V v);


    // removing the edge
    void removeEdge(Edge e);

    // checking if two vertexes are adjacent
    boolean areAdjacent(V v, V u);

    // returning the degree of a vertex
    int degree(String v);
}


// Node of graph
class NodeOfGraph {
    String key;
    int value;

    // index of a vertex in the vertices List.
    int index = 0;

    NodeOfGraph (String key, int value) {
        this.key = key;
        this.value = value;
    }

    NodeOfGraph() {
        this("", 0);
    }

    String getKey() {
        return key;
    }

    int getValue() {
        return value;
    }

    public String toString() {
        return key;
    }
}

class Edge {

    NodeOfGraph from;
    NodeOfGraph to;

    int fromIndex;
    int toIndex;

    boolean connected = false;

    double weight;

    public Edge(NodeOfGraph fromVertex, NodeOfGraph toVertex, int distance) {

        // from vertex in the edge
        this.from = fromVertex;

        // to vertex in the edge
        this.to = toVertex;

        // index of the from vertex
        fromIndex = fromVertex.index;

        // index of the to vertex
        toIndex = toVertex.index;

        // converting the values to double for better calculation
        double des = distance;

        double pe1 = this.from.getValue();

        double pe2 = this.to.getValue();


        // final weight of the edge.
        this.weight = (des / (pe1 + pe2));

        connected = true;
    }
}

class Graph<V, E> {

    // The adjacency Matrix which contains the weight of two vertexes.
    private double[][] adjacencyMatrix = new double[3000][3000];

    // ***Trick*** edgesIndex for faster access for the vertices
    // that are actually connected to another vertex.
    private List<Integer> edgesIndex = new ArrayList<>();

    // degree of each vertex in the graph.
    private int[] degrees = new int[3000];


    // list of vertices connected
    private List<NodeOfGraph> vertices;

    public Graph() {
        vertices = new ArrayList<>();
    }

    public V insertVertex(V vertex) {

        // the vertex which needs to be inserted.
        NodeOfGraph newVertex = (NodeOfGraph) vertex;


        // index of a particular vertex in the vertices
        // list.
        newVertex.index = vertices.size();

        this.vertices.add(newVertex);

        return vertex;
    }

    public double insertEdge(String from, String to, int distance) {
        int fromIndex = 0;
        int toIndex = 0;

        NodeOfGraph fromVertex = new NodeOfGraph();
        NodeOfGraph toVertex = new NodeOfGraph();

        for(int i = 0; i < vertices.size(); i++) {
            if(from.equals(vertices.get(i).getKey())) {
                fromIndex = i;
                fromVertex = vertices.get(i);
                break;
            }
        }

        for(int i = 0; i < vertices.size(); i++) {
            if(to.equals(vertices.get(i).getKey())) {
                toIndex = i;
                toVertex = vertices.get(i);
                break;
            }
        }


        // inserting the edge between two vertexes.
        Edge e = new Edge(fromVertex, toVertex, distance);
        edgesIndex.add(fromIndex);
        edgesIndex.add(toIndex);


        // adding the degree of each vertex.
        degrees[fromIndex] = degrees[fromIndex] + 1;
        degrees[toIndex] = degrees[toIndex] + 1;


        // adding the respected vertexes in the matrix too.
        adjacencyMatrix[fromIndex][toIndex] = e.weight;
        adjacencyMatrix[toIndex][fromIndex] = e.weight;

        return e.weight;
    }

    public void removeVertex(String v) {
        NodeOfGraph node = new NodeOfGraph();

        for (NodeOfGraph vertex : vertices) {
            if (v.equals(vertex.getKey())) {
                node = vertex;
                break;
            }
        }

        vertices.remove(node.index);
        degrees[node.index] = 0;
        edgesIndex.remove(node.index);

        for(int i = 0; i < vertices.size(); i++) {
            adjacencyMatrix[i][node.index] = 0.0;
            adjacencyMatrix[i][node.index] = 0.0;
        }

    }

    public void removeEdge(Edge e) {
        adjacencyMatrix[e.fromIndex][e.toIndex] = 0.0;
        adjacencyMatrix[e.toIndex][e.toIndex] = 0.0;
    }

    public boolean areAdjacent(String v, String u) {
        int fromIndex = 0;
        int toIndex = 0;

        NodeOfGraph fromVertex = new NodeOfGraph();
        NodeOfGraph toVertex = new NodeOfGraph();

        for(int i = 0; i < vertices.size(); i++) {
            if(v.equals(vertices.get(i).getKey())) {
                fromIndex = i;
                fromVertex = vertices.get(i);
                break;
            }
        }

        for(int i = 0; i < vertices.size(); i++) {
            if(u.equals(vertices.get(i).getKey())) {
                toIndex = i;
                toVertex = vertices.get(i);
                break;
            }
        }

        return adjacencyMatrix[fromIndex][toIndex] != 0.0;
    }

    public int degree(String v) {

        NodeOfGraph node = new NodeOfGraph();

        for (NodeOfGraph vertex : vertices) {
            if (v.equals(vertex.getKey())) {
                node = vertex;
                break;
            }
        }

        return degrees[node.index];
    }


    // Time Complexity: O(e*logV)
    public void prims() {


        // vertices indexes that are already in the MST.
        boolean[] inMST = new boolean[vertices.size()];


        // keys of the vertexes.
        List<Double> key = new ArrayList<>(vertices.size());

        for (int i = 0; i < vertices.size(); i++) {

            // For every other vertex,
            // set it's key to infinity.
            key.add(Double.MAX_VALUE);
        }

        // parents of the vertexes.
        List<Integer> parent = new ArrayList<>(vertices.size());

        for (int i = 0; i < vertices.size(); i++) {
            parent.add(-1);
        }

        for (int counter = 0; counter < edgesIndex.size(); counter++) {

            // checking if the source is in the MST or not.
            if (!inMST[edgesIndex.get(counter)]) {

                // initializing the priority queue.
                priorityQueue<Double, Integer> pq = new priorityQueue<>();

                int src = edgesIndex.get(counter);



                // Initial values in the MST.
                pq.insertItem(0.0, src);
                key.set(src, 0.0);



                while (pq.size() != 0) {

                    // While queue is not empty, remove the vertex u
                    // with minimum key, and put it in MST

                    int u = (int) (pq.extractMin()).getValue();

                    if (inMST[u]) {
                        continue;
                    }





                    // printing the current vertexes in the mst
                    // using the parent array.
                    if (parent.get(u) != -1) {
                        System.out.print(vertices.get(parent.get(u)) + ":" + vertices.get(u) + " ");
                    }

                    inMST[u] = true;


                    // degree variable which is used to keep a track
                    // of the degree of the current source vertex
                    // for faster access rather than going throuh every vertex
                    // in the matrix.
                    int currentDegree = 0;


                    // Follow each outgoing edge to every adjacent vertex v of u
                    for (int j = 0; j < vertices.size(); j++) {


                        // checking if the vertex are adjacent or not.
                        if (adjacencyMatrix[u][j] != 0.0) {
                            currentDegree++;
                        }


                        // replace it's key key[v] with the weight(u, v), only if
                        // it is less than the key.
                        if (adjacencyMatrix[u][j] != 0.0 && !inMST[j]
                                && key.get(j) > adjacencyMatrix[u][j]) {

                            double weight = adjacencyMatrix[u][j];

                            key.set(j, weight);
                            pq.insertItem(weight, j);
                            parent.set(j, u);
                        }

                        if (currentDegree == degrees[u]) {
                            // if the degree variable reach the current value
                            // then the loop breaks.
                            break;
                        }
                    }
                }
            }
        }

        System.out.println();
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
    private int parent(int i) {
        if(i==0) {
            return 0;
        }

        return (i-1)/2;
    }





    // Gets the index of the left child in the heap
    // array.
    private int leftChild(int i) {
        return (2*i + 1);
    }




    // Gets the index of the right child in the heap
    // array.
    private int rightChild(int i) {
        return (2*i + 2);
    }






    // Swapping two indexes in the heap
    // array.
    void swap(int x, int y) {
        Node temp = Heap.get(x);
        Heap.set(x, Heap.get(y));
        Heap.set(y, temp);
    }




    // Returns the size of the heap
    // at a particular instance.
    public int size() {
        return Heap.size();
    }





    // Heapify Down method.
    //
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
    @Override
    public void decreaseKey(Node item, K newKey) {
        if((item.getKey().compareTo(newKey) < 0)) {
            item.setKey(newKey);
        }
        else {
            System.out.println("The provided key is greater than the current key.");
        }
    }



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
    @Override
    public void union(priorityQueue anotherQueue) {

        int size = anotherQueue.size();
        for(int i = 0; i < size; i++) {
            insert(anotherQueue.extractMin());
        }

    }
}