import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {
    private App app;
    private static JLabel page = new JLabel();
    static JButton askButton = new JButton("Ask");
    static JButton startButton = new JButton("Start");
    static JButton backButton = new JButton("Back");
    static JButton loginButton = new JButton("Log-In");
    static JButton signupButton = new JButton("Sign-Up");
    static JButton myBookButton = new JButton("My Bookings");
    static JButton mapButton = new JButton("Map");
    static JButton suggestionsButton = new JButton("Suggestions");
    static JButton bookButton = new JButton("Book");
    static JButton concreteParkButton = new JButton("Park");

    public GUI(App app) {
        this.app = app;
    }
    

    public void init() {
        JFrame frame = new JFrame("Parking Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel context = new JPanel();
        context.setLayout((LayoutManager) new BoxLayout(context, BoxLayout.Y_AXIS));
        frame.getContentPane().add(context);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        context.add(buttons);


        loginButton.addActionListener(e -> page.setText(app.getState().toString()));
        signupButton.addActionListener(e -> page.setText(app.getState().toString()));
        startButton.addActionListener(e -> page.setText(app.getState().toString()));
        backButton.addActionListener(e -> page.setText(app.getState().toString()));
        myBookButton.addActionListener(e -> page.setText(app.getState().toString()));
        mapButton.addActionListener(e -> page.setText(app.getState().toString()));
        suggestionsButton.addActionListener(e -> page.setText(app.getState().toString()));
        concreteParkButton.addActionListener(e -> page.setText(app.getState().toString()));
        bookButton.addActionListener(e -> page.setText(app.getState().toString()));

        frame.setVisible(true);
        frame.setSize(300, 100);
        buttons.add(loginButton);
        buttons.add(signupButton);
        buttons.add(backButton);
        buttons.add(myBookButton);
        frame.setVisible(true);
        frame.setSize(300, 100);
        buttons.add(mapButton);
        buttons.add(suggestionsButton);
        buttons.add(concreteParkButton);
        buttons.add(bookButton);
    }
}
