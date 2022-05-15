public abstract class State {
    protected App app;

    public State(App app) {
        this.app = app;
    }

    public abstract void back(GUI gui);

    public abstract void start(GUI gui);

    public abstract void login(GUI gui);

    public abstract void signup(GUI gui);

    public abstract void suggestions(GUI gui);

    public abstract void map(GUI gui);

    public abstract void book(GUI gui);

    public abstract void concretePark(GUI gui);

    public abstract void myBook(GUI gui);
}
