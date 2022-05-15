public class Map extends State {

    public Map(App app) {
        super(app);
    }

    @Override
    public void back(GUI gui) {
        gui.backButton.doClick();
        app.changeState(new Start(app));
        
    }

    @Override
    public void start(GUI gui) {
        gui.backButton.doClick();
        app.changeState(new Start(app));
        
    }

    @Override
    public void login(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }

    @Override
    public void signup(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }

    @Override
    public void suggestions(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }

    @Override
    public void map(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }

    @Override
    public void book(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }

    @Override
    public void concretePark(GUI gui) {
        gui.concreteParkButton.doClick();
        app.changeState(new ConcretePark(app));
        
    }

    @Override
    public void myBook(GUI gui) {
        // app is in the map state,
        // so do nothing.
        
    }
    
}
