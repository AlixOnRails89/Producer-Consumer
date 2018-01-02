import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		// The main reason for launching your application in this way is that Swing
		// components are not thread-safe so you need to guarantee which thread your
		// GUI will start from: the one called the Event Dispatching Thread (EDT). 
		// Without doing this, you can't be sure what thread it will start in.

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				new MainFrame();
				new WelcomeFrame();		
			}
		});
	}
}
