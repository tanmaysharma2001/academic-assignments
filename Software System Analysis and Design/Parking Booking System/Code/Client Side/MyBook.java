public class MyBook extends State {

    public MyBook(App app) {
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
        gui.loginButton.doClick();
        app.changeState(new Login(app));        
    }

    @Override
    public void signup(GUI gui) {
        gui.signupButton.doClick();
        app.changeState(new Signup(app));
        
    }

    @Override
    public void suggestions(GUI gui) {
        // Looking for suggestions.
        gui.suggestionsButton.doClick();
        app.changeState(new Suggestions(app));
    }

    @Override
    public void map(GUI gui) {
        // App is in the MyBook state,
        // so do nothing.
        
    }

    @Override
    public void book(GUI gui) {
        // again booking the same place.
        gui.bookButton.doClick();
        app.changeState(new Book(app));
        
    }

    @Override
    public void concretePark(GUI gui) {
        // App is in the MyBook state,
        // so do nothing.
    }

    @Override
    public void myBook(GUI gui) {
        // App is in the MyBook state, so do nothing.
    }
    
}
