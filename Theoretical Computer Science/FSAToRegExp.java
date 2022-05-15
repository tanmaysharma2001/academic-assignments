import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

class State {
    public String value;
    int FinalValue = 0;
    int index;
    public State(int index, String value) {
        this.index = index;
        this.value = value;
    }
}

class Transition {
    public String from, by, to;
    Transition(String from, String by, String to) {
        this.from = from;
        this.by = by;
        this.to = to;
    }

    String getTransition() {
        return to;
    }
}

class E extends Exception {
    private String msg;
    E(String s) {
        msg = s;
    }
    public String toString() {
        return msg;
    }
}

public class FSAToRegExp {

    static ArrayList<ArrayList<String>> matrix = new ArrayList<>(); // to store fsa
    static HashSet<String> transitions = new HashSet<>();
    static HashMap<String, State> states = new HashMap<>();

    static String recursion(int i, int j, int k, ArrayList<ArrayList<String>> matrix) {
        if(k == -1) {
            return matrix.get(i).get(j);
        }
        else {
            return "(" + recursion(i,k,k-1,matrix) + ")(" + recursion(k, k, k-1, matrix) + ")*(" + recursion(k, j, k-1, matrix) + ")|(" + recursion(i, j, k-1, matrix) + ")";
        }
    }

    static int find(String givenString, char c) {
        int index = givenString.indexOf(c);
        if(index==-1) {
            return givenString.length();
        }
        else {
            return index;
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("input.txt");
        BufferedReader in = new BufferedReader(fr);

        FileWriter fileWriter = new FileWriter("output.txt");
        BufferedWriter out = new BufferedWriter(fileWriter);

        int n = 0;
        try {
            String statesString;

            statesString = in.readLine();

            if(!statesString.substring(0, statesString.indexOf('[')).equals("states=")) {
                throw new E("E0: Input file is malformed");
            }

            statesString = statesString.substring(statesString.indexOf('[') + 1);

            while(statesString.indexOf(',') != -1) {
                states.put(statesString.substring(0, statesString.indexOf(',')), new State(n, statesString.substring(0, statesString.indexOf(','))));
                statesString = statesString.substring(statesString.indexOf(',') + 1);
                n++;
            }

            if(!statesString.substring(0, statesString.indexOf(']')).equals("")) {
                states.put(statesString.substring(0, statesString.indexOf(']')), new State(n, statesString.substring(0, statesString.indexOf(']'))));
            }


            int i = 0;
            for(Map.Entry<String, State> entry : states.entrySet()) {
                ArrayList<String> v = new ArrayList<>();
                matrix.add(v);

                for(int k = 0; k < i; k++) {
                    matrix.get(i).add("");
                }

                for(int l = 0; l < matrix.size(); l++) {
                    matrix.get(l).add("");
                }
                i++;
            }

            String alphaString = in.readLine();

            if(!alphaString.substring(0, alphaString.indexOf('[')).equals("alpha=")) {
                throw new E("E0: Input file is malformed");
            }

            alphaString = alphaString.substring(alphaString.indexOf('[') + 1);

            while(alphaString.indexOf(',') != -1) {
                transitions.add(alphaString.substring(0, alphaString.indexOf(',')));
                alphaString = alphaString.substring(alphaString.indexOf(',') + 1);
            }

            if(!alphaString.substring(0, alphaString.indexOf(']')).equals("")) {
                transitions.add(alphaString.substring(0, alphaString.indexOf(']')));
            }

            String initialString = in.readLine();
            if(!initialString.substring(0, initialString.indexOf('[')).equals("initial=")) {
                throw new E("E0: Input file is malformed");
            }

            if(initialString.equals("initial=[]")) {
                throw new E("E4: Initial state is not defined");
            }

            String initialState;
            initialString = initialString.substring(initialString.indexOf('[') + 1);
            initialState = initialString.substring(0, initialString.indexOf(']'));
            if(!states.containsKey(initialState)) {
                throw new E("E1: A state '" + initialState + "' is not in the set of states");
            }

            State initial = states.get(initialState);

            String acceptingString = in.readLine();
            if(!acceptingString.substring(0, find(acceptingString,'[')).equals("accepting=")) {
                throw new E("E0: Input file is malformed");
            }

            acceptingString = acceptingString.substring(acceptingString.indexOf('[') + 1);
            String finalState;

            while(acceptingString.indexOf(',') != -1) {
                finalState = acceptingString.substring(0, acceptingString.indexOf(','));
                states.get(finalState).FinalValue = 1; // error
                acceptingString = acceptingString.substring(acceptingString.indexOf(',') + 1);
            }

            if(!acceptingString.substring(0, acceptingString.indexOf(']')).equals("")) {
                finalState = acceptingString.substring(0, acceptingString.indexOf(']'));
                states.get(finalState).FinalValue = 1; // out of range error
            }


            HashSet<HashSet<String>> disjoint = new HashSet<>();

            for(Map.Entry<String, State> p : states.entrySet()) {
                HashSet<String> set1 = new HashSet<>();
                set1.add(p.getValue().value);
                disjoint.add(set1);
            }

            HashSet<String> s1 = new HashSet<>();
            HashSet<String> s2 = new HashSet<>();
            HashMap<String, String> determinism = new HashMap<>();

            for(Map.Entry<String, State> p : states.entrySet()) {
                determinism.put(p.getValue().value + "eps", p.getValue().value);
            }

            String transitionString = in.readLine();
            String transition;
            String from, by;
            String to = "";
            if(!transitionString.substring(0, transitionString.indexOf('[')).equals("trans=")) {
                throw new E("E0: Input file is malformed");
            }

            transitionString = transitionString.substring(transitionString.indexOf('[') + 1);

            while(transitionString.indexOf(',') != -1) {
                transition = transitionString.substring(0, transitionString.indexOf(','));
                from = transition.substring(0, transition.indexOf('>'));
                transition = transition.substring(transitionString.indexOf('>') + 1);
                by = transition.substring(0, transition.indexOf('>'));

                if(!transitions.contains(by)) {
                    throw new E("E3: A transition '" + by + "' is not represented in the alphabet");
                }

                if(determinism.containsKey(from+by)) {
                    throw new E("E5: FSA is nondeterministic");
                }

                determinism.put(from+by, transition);
                transition = transition.substring(transition.indexOf('>') + 1);
                if(!from.equals(transition)) {
                    for(HashSet<String> c : disjoint) {
                        if(c.contains(from)) {
                            s1 = c;
                        }
                        if(c.contains(transition)) {
                            s2 = c;
                        }
                    }
                    disjoint.remove(s1);
                    disjoint.remove(s2);
                    for(String x : s2) {
                        s1.add(x);
                    }
                    disjoint.add(s1);
                }

                if(matrix.get(states.get(from).index).get(states.get(transition).index).isEmpty()) {
                    String resultingString = matrix.get(states.get(from).index).get(states.get(transition).index);
                    resultingString += by;
                    matrix.get(states.get(from).index).set(states.get(transition).index, resultingString);
                }
                else {
                    String resultingString = matrix.get(states.get(from).index).get(states.get(transition).index);
                    resultingString += "|" + by;
                    matrix.get(states.get(from).index).set(states.get(transition).index, resultingString);
                }
                transitionString = transitionString.substring(transitionString.indexOf(',') + 1);
            }

            if(!transitionString.substring(0, transitionString.indexOf(']')).equals("")) {
                transition = transitionString.substring(0, find(transitionString, ','));
                from = transition.substring(0, transition.indexOf('>'));
                transition = transition.substring(transitionString.indexOf('>') + 1);
                by = transition.substring(0, transition.indexOf('>'));

                if(!transitions.contains(by)) {
                    throw new E("E3: A transition '" + by + "' is not represented in the alphabet");
                }

                if(determinism.containsKey(from+by)) {
                    throw new E("E5: FSA is nondeterministic");
                }

                determinism.put(from+by, to);
                transition = transition.substring(transition.indexOf('>') + 1);
                to = transition.substring(0, find(transition, ']'));
                if(!from.equals(to)) {
                    for(HashSet<String> c : disjoint) {
                        if(c.contains(from)) {
                            s1 = c;
                        }
                        if(c.contains(to)) {
                            s2 = c;
                        }
                    }
                    disjoint.remove(s1);
                    disjoint.remove(s2);

                    for(String x : s2) {
                        s1.add(x);
                    }
                    disjoint.add(s1);
                }

                if((matrix.get(states.get(from).index).get(states.get(to).index)).isEmpty()) {
                    String resultingString = matrix.get(states.get(from).index).get(states.get(to).index);
                    resultingString += by;
                    matrix.get(states.get(from).index).set(states.get(to).index, resultingString);
                }
                else {
                    String resultingString = matrix.get(states.get(from).index).get(states.get(to).index);
                    resultingString += "|" + by;
                    matrix.get(states.get(from).index).set(states.get(to).index, resultingString);
                }

                if(disjoint.size() != 1) {
                    throw new E("E2: Some states are disjoint");
                }

                for(int j = 0; j <= n; j++) {
                    for(int k = 0; k <= n; k++) {
                        if(j==k) {
                            if(matrix.get(j).get(k).isEmpty()) {
                                matrix.get(j).set(k, "eps");
                            }
                            else {
                                String resultingString = matrix.get(j).get(k);
                                resultingString += "|eps";
                                matrix.get(j).set(k, resultingString);
                            }
                        }
                        else if(matrix.get(j).get(k).isEmpty()) {
                            matrix.get(j).set(k, "{}");
                        }
                    }
                }

                int f = 0;
                for(Map.Entry<String, State> p : states.entrySet()) {
                    if(p.getValue().FinalValue == 1) {
                        if(f != 0) {
                            out.write("|");
                        }
                        out.write(recursion(initial.index, p.getValue().index, n, matrix));
                        f++;
                    }
                }
                if(f==0) {
                    out.write("{}");
                }
            }
            out.close();
            in.close();;

        }
        catch(E e) {
            out.write("Error:");
            out.newLine();
            out.write(e.toString());
            out.close();
            in.close();
            return;
        }

    }
}