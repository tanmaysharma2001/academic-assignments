public class Suggestions extends State {

    public Suggestions(App app) {
        super(app);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void back(GUI gui) {
        gui.startButton.doClick();
        app.changeState(new Start(app));        
    }

    @Override
    public void start(GUI gui) {
        gui.startButton.doClick();
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
        // App is in the suggestions state.
        // So do nothing.
        
    }

    @Override
    public void map(GUI gui) {
        gui.mapButton.doClick();
        app.changeState(new Map(app));
        
    }

    @Override
    public void book(GUI gui) {
        gui.bookButton.doClick();
        app.changeState(new Book(app));
    }

    @Override
    public void concretePark(GUI gui) {
        // App is in the Suggestions state,
        // so do nothing.
        
    }

    @Override
    public void myBook(GUI gui) {
        gui.myBookButton.doClick();
        app.changeState(new MyBook(app));   
    }
    
}