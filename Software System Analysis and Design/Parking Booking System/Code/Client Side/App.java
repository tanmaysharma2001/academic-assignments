public class App {
    private static State state;
    private GUI gui;

    public App() {
        this.state = new Start(this);


        gui = new GUI(this);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    // methods which change states
    public void back(GUI gui) {
        state.back(gui);
    }

    public void login(GUI gui) {
        state.login(gui);
    }

    public void signup(GUI gui) {
        state.signup(gui);
    }

    public void suggestions(GUI gui) {
        state.suggestions(gui);
    }

    public void map(GUI gui) {
        state.map(gui);
    }

    public void book(GUI gui) {
        state.book(gui);
    }

    public void concretePark(GUI gui) {
        state.concretePark(gui);
    }

    public void myBook(GUI gui) {
        state.myBook(gui);
    }

    // A state may call some service methods on the context.
    public void ask() {

    }

}