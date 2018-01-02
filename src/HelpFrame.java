import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class HelpFrame extends JFrame {

	private static final long serialVersionUID = 7929370838154134163L;

	JTextPane textPane;
	JScrollPane scroll;
	JLabel label;
	String string;

	public HelpFrame()
	{
		super("Help");

		textPane = new JTextPane(); 
		textPane.setMargin(new Insets(5, 5, 5, 5));
		textPane.setEditable(true);
		setSize(new Dimension(660,660));
		scroll = new JScrollPane(textPane);
		scroll.setPreferredSize(new Dimension(660,660));
		add(scroll);

		string = "\n"
				+ "The application provides an animation of the classical producer-consumer problem in computer science.\n"
				+ "\n"
				+ "The producer-consumer problem is a classic example of a multiple thread synchronisation problem.\n"
				+ "The problem describes two threads, the Producer and the Consumer, who share a single slot in an object "
				+ "called Storage. The Producer’s job is to create a Fruit object and store it in Storage’s single slot. "
				+ "At the same time the Consumer is removing the Fruit object from Storage’s single slot. The problem is "
				+ "to make sure the Producer doesn’t try to add a Fruit to a full slot andthe Consumer doesn’t try to remove"
				+ "a Fruit from an empty slot.\n"
				+ "\n"
				+ "\n"
				+ "The animation will illustrate two forms of synchronisation used in solving this version of the "
				+ "producer-consumer problem. The two forms of synchronisation are mutual exclusion synchronisation and "
				+ "conditional synchronisation. Mutual exclusion is the guarantee that access to a single shared resource"
				+ "(the single slot in the Storage object) is achieved on mutually exclusive basis. To ensure the Producer "
				+ "and Consumer can only access the Storage object on exclusive basis, access was controlled by a single lock."
				+ "The lock is declared by synchronizing on the Storage object. The Producer and Consumer must acquire the"
				+ "lock before being able to change the state of the Storage object.\n"
				+ "\n"
				+ "\n"
				+ "Condition synchronisation is a higher level of control which occurs after a thread has been granted exclusive "
				+ "access. It forces the threads to wait for a condition to become true before being allowed to change the state "
				+ "of the Storage (i.e. Producer inserting a Fruit, Consumer removing a Fruit). This is achieved by the Producer"
				+ " and Consumer threads communicating with each other using the methods wait() and notify().\n"
				+ "\n"
				+ "\n"
				+ "If the condition is false then the thread executes wait(), suspends its sequence of execution and gives up the "
				+ "lock. If the condition is true then a thread can continue its sequence of execution, change the state of the "
				+ "Storage and signal to the other using notify().\n "
				+ "\n"
				+ "\n"
				+ "The two forms of synchronisation are animated in the application:"
				+ "\n"
				+ "\n"
				+ "Below are recommended settings for the user to best visualise Mutual Exclusion Synchronisation (represented by a Lock).\n"
				+ "\n"
				+ "Producer Speed = MIN\n"
				+ "Lock Delay = MAX\n"
				+ "Consumer Speed = MIN\n"
				+ "\n"
				+ "These settings offer the best visualisation of Producer and Consumer acquiring the Lock. The appearance of the Producer"
				+ "or Consumer as a Lock represents the current thread with exclusive access.\n"
				+ "\n"
				+ "Below are recommended settings for the user to visualise Condition Synchronisation (represented by P and C turning Red "
				+ "whilst waiting for a condition to be satisfied).\n"
				+ "\n"
				+ "Producer waiting:\n"
				+ "\n"
				+ "Producer Speed = MAX\n"
				+ "Lock Delay= MIN\n"
				+ "Consumer Speed = MIN\n"
				+ "\n"
				+ "Consumer waiting:\n"
				+ "\n"
				+ "Producer Speed = MIN\n"
				+ "Lock Delay = MIN\n"
				+ "Consumer Speed = MAX\n"
				+ "\n"
				+ "These settings offer the best visualisation of the Consumer waiting* for a signal** from the Producer that there is Fruit "
				+ "available in Storage.\n"
				+ "\n"
				+ "*By calling wait()\n"
				+ "**By calling notifyAll()\n";

		textPane.setText(string);		
		textPane.setEditable(false);
		textPane.setCaretPosition(0);
		setVisible(true);
	}
}
