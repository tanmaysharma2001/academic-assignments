public class Signup extends State {

    public Signup(App app) {
        super(app);
    }

    @Override
    public void back(GUI gui) {
        gui.backButton.doClick();
        app.changeState(new Map(app));
        
    }

    @Override
    public void start(GUI gui) {
        // App is in the Signup State,
        // so do nothing.        
    }

    @Override
    public void login(GUI gui) {
        gui.loginButton.doClick();
        app.changeState(new Login(app));
        
    }

    @Override
    public void signup(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
        
    }

    @Override
    public void suggestions(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
    }

    @Override
    public void map(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
    }

    @Override
    public void book(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
    }

    @Override
    public void concretePark(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
    }

    @Override
    public void myBook(GUI gui) {
        // App is in the Signup State,
        // so do nothing.
    }

    
    
}
