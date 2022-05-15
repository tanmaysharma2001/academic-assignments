public class Start extends State {

    public Start(App app) {
        super(app);
    }

    @Override
    public void back(GUI gui) {
        // as the app was started after logging-in
        // clicking the back button in the 'Start' state
        // will assume user wants to login again (perhaps this time using a different account).
        gui.backButton.doClick();
        app.changeState(new Login(app));
        
    }

    @Override
    public void signup(GUI gui) {
        gui.signupButton.doClick();
        app.changeState(new Signup(app));
    }

    @Override
    public void start(GUI gui) {
        // Application is in the Start state.
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
        // App is in Start State, so do nothing.
        
    }

    @Override
    public void concretePark(GUI gui) {
        // App is in Start State, so do nothing.
        
    }

    @Override
    public void myBook(GUI gui) {
        // App is in Start State, so do nothing.
        
    }

    @Override
    public void login(GUI gui) {
        gui.loginButton.doClick();
        app.changeState(new Login(app));
    }
    
}
