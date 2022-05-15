import java.util.*;

public class Login extends State {

    public Login(App app) {
        super(app);
    }

    @Override
    public void back(GUI gui) {
        // Application closes after pressing 
        // the back button in the Login State.
        System.exit(0);
    }

    @Override
    public void login(GUI gui) {
        // Application in the login state
        // so do nothing.        
    }

    @Override
    public void signup(GUI gui) {
        gui.signupButton.doClick();
        app.changeState(new Signup(app));   
    }

    @Override
    public void start(GUI gui) {
        gui.startButton.doClick();
        app.changeState(new Start(app));
        
    }

    @Override
    public void suggestions(GUI gui) {
        // App is in login state so do nothing. 
        
    }

    @Override
    public void map(GUI gui) {
        // App is in login state so do nothing.
    }

    @Override
    public void book(GUI gui) {
        // App is in Login state so do nothing.
        
    }

    @Override
    public void concretePark(GUI gui) {
        // App is in Login state so do nothing.
        
    }

    @Override
    public void myBook(GUI gui) {
        // App is in LoginState, so do nothing.
        
    }
    
}