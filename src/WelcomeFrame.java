
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class WelcomeFrame extends JFrame {

	private static final long serialVersionUID = 7341592366168640756L;
	JTextPane textPane;
	JScrollPane scroll;
	JLabel label;
	String string;

	public WelcomeFrame()
	{
		super("Welcome");

		textPane = new JTextPane(); 
		textPane.setMargin(new Insets(5, 5, 5, 5));
		textPane.setEditable(true);
		setSize(new Dimension(400,575));
		scroll = new JScrollPane(textPane);
		scroll.setPreferredSize(new Dimension(400,400));

		add(scroll);

		string = "\n"
				+ "Hello!\n"
				+ "\n"
				+ "Welcome to the Producer-Consumer Problem Application.\n"
				+ "\n"
				+ "The application animates the Producer-Consumer Problem "
				+ "using a Single Buffer solution.\n"
				+ "\n"
				+ "Below are the recommended settings to best visualise "
				+ "Mutual Exclusion Synchronisation (represented by P and C turning into a lock).\n"
				+ "\n"
				+ "Producer Speed = MIN\n"
				+ "Lock Delay = MAX\n"
				+ "Consumer Speed = MIN\n"
				+ "\n"
				+ "And these are recommended settings for the user to visualise Condition "
				+ "Synchronisation (represented by P and C turning Red "
				+ "whilst waiting for a condition to be satisfied).\n"
				+ "\n"
				+ "Producer waiting:\n"
				+ "\n"
				+ "Producer Speed = MAX\n"
				+ "Lock Delay = MIN\n"
				+ "Consumer Speed = MIN\n"
				+ "\n"
				+ "Consumer waiting:\n"
				+ "\n"
				+ "Producer Speed = MIN\n"
				+ "Lock Delay = MIN\n"
				+ "Consumer Speed = MAX\n"
				+ "\n"
				+ "For more information click the \"Help\" button.";

		textPane.setText(string);
		textPane.setEditable(false);
		setVisible(true);
	}
}
