import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class MainFrame extends JFrame implements ActionListener  {

	private static final long serialVersionUID = -7821124202230340655L;
	private JTextPane textArea;
	private Animation myAnimation;
	private JSlider producerSlider;
	private JSlider bufferSlider;
	private JSlider consumerSlider;
	private JToggleButton button;
	private JTextPane informationColorCoding;
	private JTextPane keyMutualExclusion;
	private JTextPane keyConditionSynchronization;
	private JButton helpButton;
	
	public MainFrame() 
	{
		super("Producer-Consumer Animator"); 

		setLayout(new BorderLayout()); 

		JPanel jpanelRight = new JPanel();
		JPanel jpanelLeft = new JPanel();
		JPanel topOfScreen = new JPanel();
		JPanel bottomOfScreen = new JPanel();

		// START OF NORTH SECTION	

		topOfScreen.setPreferredSize(new Dimension(80,80));
		topOfScreen.setLayout(new GridLayout(1, 4, 4, 4));
		add(topOfScreen, BorderLayout.NORTH);

		producerSlider = new JSlider(JSlider.HORIZONTAL, 1, 95, 1); //minimum value, maximum value, starting value
		bufferSlider = new JSlider(JSlider.HORIZONTAL, 1, 95, 46);
		consumerSlider = new JSlider(JSlider.HORIZONTAL, 1, 95, 1);

		producerSlider.setPreferredSize(new Dimension(500,100));
		bufferSlider.setPreferredSize(new Dimension(250,10));
		consumerSlider.setPreferredSize(new Dimension(500,100));

		int fontSize = 15;
		Font font = new Font("Arial Black", Font.BOLD, fontSize); 

		button = new JToggleButton("Start Animation");
		button.addActionListener(this);

		button.setSize(10,10);
		producerSlider.setBorder(BorderFactory.createTitledBorder(null, "Producer Speed", 2,0, font, Color.blue));
		producerSlider.setMajorTickSpacing(10);
		producerSlider.setPaintTicks(true);

		bufferSlider.setBorder(BorderFactory.createTitledBorder(null, "Lock Delay", 2,0, font, Color.black));
		bufferSlider.setMajorTickSpacing(10);
		bufferSlider.setPaintTicks(true);

		consumerSlider.setBorder(BorderFactory.createTitledBorder(null,"Consumer Speed",2,0,font, Color.magenta));
		consumerSlider.setMajorTickSpacing(10);
		consumerSlider.setPaintTicks(true);

		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));

		Hashtable<Integer, JLabel> tableLabelProducerSlider = new Hashtable<Integer, JLabel>();
		tableLabelProducerSlider.put(1, new JLabel("Min"));
		tableLabelProducerSlider.put(95, new JLabel("Max"));

		producerSlider.setLabelTable(tableLabelProducerSlider);
		producerSlider.setPaintLabels(true);

		Hashtable<Integer, JLabel> tableLabelConsumerSlider = new Hashtable<Integer, JLabel>();
		tableLabelConsumerSlider.put(1, new JLabel("Min"));
		tableLabelConsumerSlider.put(95, new JLabel("Max"));

		consumerSlider.setLabelTable(tableLabelConsumerSlider);
		consumerSlider.setPaintLabels(true);

		Hashtable<Integer, JLabel> tableLabelBufferSlider = new Hashtable<Integer, JLabel>();
		tableLabelBufferSlider.put(1, new JLabel("Min"));
		tableLabelBufferSlider.put(95, new JLabel("Max"));

		bufferSlider.setLabelTable(tableLabelBufferSlider);
		bufferSlider.setPaintLabels(true);

		producerSlider.setToolTipText("<html> Producer Slider - controls the speed of the Fruit from Producer to the Buffer <br>  Min = Slow Motion <br>   Max = Extra Fast <html>");
		bufferSlider.setToolTipText("<html> Lock Delay - Controls the amount of time the Producer and Consumer hold the Lock");
		consumerSlider.setToolTipText("<html> Consumer Slider - controls the speed of the Fruit from Buffer to the Consumer <br>  Min =   Slow Motion <br>   Max =  Extra Fast <html>");

		topOfScreen.add(producerSlider);
		topOfScreen.add(bufferSlider);
		topOfScreen.add(consumerSlider);
		topOfScreen.add(button);


		// END OF NORTH SECTION

		// START OF CENTRE SECTION

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jpanelLeft, jpanelRight);
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);	

		// START OF CENTRE WEST SECTION

		this.textArea = new JTextPane(); 

		textArea.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300,645));
		jpanelRight.add(scrollPane, BorderLayout.WEST);

		// START OF CENTRE EAST SECTION

		this.myAnimation = new Animation(textArea, producerSlider, consumerSlider, bufferSlider);
		myAnimation.setPreferredSize(new Dimension(1200, 2000));
		myAnimation.setToolTipText("<html> Animation of the Producer Consumer Problem <br>"
				+ " P = Producer - Generates Fruit Objects <br>"
				+ " C = Consumer - Consumes Fruit Objects <br>"
				+ " Producer has the Lock - Lock Appears Above the Producer <br>"
				+ " Consumer has the Lock - Lock Appears Above the Consumer <br>"
				+ " P turns Red = Producer is waiting for space in the Storage to become available <br>"
				+ " C turns Red = Consumer is waiting for a Fruit to be placed in Storage <html>");
		JScrollPane scrollPaneAnimation = new JScrollPane(myAnimation);
		scrollPaneAnimation.setPreferredSize(new Dimension(1100,645));
		jpanelLeft.add(scrollPaneAnimation, BorderLayout.EAST);

		// START OF SOUTH SECTION
		informationColorCoding = new JTextPane();
		keyMutualExclusion= new JTextPane();
		// keyMutualExclusion.setEditable(false);

		keyConditionSynchronization = new JTextPane();

		helpButton = new JButton("Help");
		helpButton.addActionListener(this);

		informationColorCoding.setBackground(Color.black);

		//JTextPanel - Color Coding
		informationColorCoding.setBorder(BorderFactory.createTitledBorder(null, "Colour Coding", 0,0, font, Color.white));

		Font fontTextPanePC = new Font("Verdana", Font.PLAIN, 14);
		informationColorCoding.setFont(fontTextPanePC);

		//appendToPane(informationColorCoding, "Producer: Generates Fruit\n", Color.blue);
		// appendToPane(informationColorCoding, "Consumer: Consumes Fruit\n", Color.magenta);
		appendToPane(informationColorCoding, "\n", Color.blue);
		appendToPane(informationColorCoding, "Large blue square is the Producer\n", Color.blue);
		appendToPane(informationColorCoding, "Stack of small blue shapes represent all generated Fruit\n", Color.blue);
		appendToPane(informationColorCoding, "\n", Color.blue);
		appendToPane(informationColorCoding, "Small white shapes represent a moving Fruit\n", Color.white);
		appendToPane(informationColorCoding, "\n", Color.blue);
		//appendToPane(informationColorCoding, "\n", Color.blue);
		appendToPane(informationColorCoding, "Large white open square is the Storage\n", Color.white);
		appendToPane(informationColorCoding, "\n", Color.blue);
		appendToPane(informationColorCoding, "Large magenta square is the Consumer\n", Color.magenta);
		appendToPane(informationColorCoding, "Stack of small magenta shapes represent all consumed Fruit\n", Color.magenta);
		informationColorCoding.setCaretPosition(0);

		keyMutualExclusion.setBorder(BorderFactory.createTitledBorder(null, "Mutual Exclusion Synchronization", 0,0, font, Color.black));

		appendToPane(keyMutualExclusion, "Producer", Color.blue);
		appendToPane(keyMutualExclusion, " appears as Padlock when it has acquired the Lock.\n", Color.black);
		appendToPane(keyMutualExclusion, "\n", Color.blue);
		appendToPane(keyMutualExclusion, "Consumer", Color.magenta);
		appendToPane(keyMutualExclusion, " appears as Padlock when it has acquired the Lock.\n", Color.black);

		keyConditionSynchronization.setBorder(BorderFactory.createTitledBorder(null, "Condition Synchronization", 0,0, font, Color.black));

		appendToPane(keyConditionSynchronization, "P (turns Red) ", Color.red);
		appendToPane(keyConditionSynchronization, "Producer ", Color.blue);
		appendToPane(keyConditionSynchronization, "is waiting for the Consumer to create space in Storage.\n", Color.black);
		appendToPane(keyConditionSynchronization, "\n", Color.blue);
		appendToPane(keyConditionSynchronization, "C (turns Red) ", Color.red);
		appendToPane(keyConditionSynchronization, "Consumer ", Color.magenta);
		appendToPane(keyConditionSynchronization, "is waiting for a Fruit to be placed in Storage.\n ", Color.black);

		JScrollPane scrollPaneInformationColorCoding = new JScrollPane(informationColorCoding);
		JScrollPane scrollPaneInformationArea = new JScrollPane(keyMutualExclusion);
		scrollPaneInformationArea.setPreferredSize(new Dimension(1000,1000));

		keyConditionSynchronization.setEditable(false);
		keyMutualExclusion.setEditable(false);

		bottomOfScreen.setPreferredSize(new Dimension(110,110));
		bottomOfScreen.setLayout(new GridLayout(1, 4, 10, 10));
		bottomOfScreen.add(scrollPaneInformationColorCoding);
		bottomOfScreen.add(keyMutualExclusion);
		bottomOfScreen.add(keyConditionSynchronization);
		bottomOfScreen.add(helpButton);
		add(bottomOfScreen, BorderLayout.SOUTH);

		// END OF SOUTH SECTION

		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the JFrame when closed. Does not keep running

		Storage store = new Storage(myAnimation);
		Producer p1 = new Producer(store, myAnimation);
		Consumer c1 = new Consumer(store, myAnimation);

		p1.start();
		c1.start();
	}

	private void appendToPane(JTextPane tp, String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == button)
		{
			if(myAnimation.getValuePauseAnimation())
			{
				button.setText("Pause");
				myAnimation.startAnimation();
			}
			else
			{
				button.setText("Resume");
				myAnimation.stopAnimation();		   
			}
		}

		else if(e.getSource() == helpButton)	
		{
			new HelpFrame();
		}
	}
}

