public class Book extends State {

    public Book(App app) {
        super(app);
    }

    @Override
    public void back(GUI gui) {
        gui.concreteParkButton.doClick();
        app.changeState(new ConcretePark(app));
        
    }

    @Override
    public void start(GUI gui) {
        gui.startButton.doClick();
        
    }

    @Override
    public void login(GUI gui) {
        // App is in the Map state,
        // so do nothing.
        
    }

    @Override
    public void signup(GUI gui) {
        // App is in the book state,
        // so do nothing.
        
    }

    @Override
    public void suggestions(GUI gui) {
        gui.suggestionsButton.doClick();
        app.changeState(new Suggestions(app));
        
    }

    @Override
    public void map(GUI gui) {
        gui.mapButton.doClick();
        app.changeState(new Map(app));
        
    }

    @Override
    public void book(GUI gui) {
        // App is in the Book State, so do nothing.
        
    }

    @Override
    public void concretePark(GUI gui) {
        gui.concreteParkButton.doClick();
        app.changeState(new ConcretePark(app));
        
    }

    @Override
    public void myBook(GUI gui) {
        // App is in the book state, so do nothing.
    }
    
}
