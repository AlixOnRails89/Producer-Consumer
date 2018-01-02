import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/*
 * 
 * Class is the improved Reporter class that extends JComponent.
 * 
 * JTextArea removed.
 * 
 */

public class Animation extends JComponent {

	private int shapeObjectID;
	private int copyOfShapeObjectID;
	private int nextProducerID;
	private int nextConsumerID;
	private int nextBufferID;
	private JTextPane textArea;
	private JSlider producerSlider;
	private JSlider consumerSlider;
	private JSlider bufferSlider;
	private static final long serialVersionUID = -1328357299610186430L;
	private Shape producerShapeOne; 
	private Map<Integer, Rectangle2D.Double> hashMap = new HashMap<Integer, Rectangle2D.Double>();
	private Map<Integer, Rectangle2D.Double> hashMapProducedItems = new HashMap<Integer, Rectangle2D.Double>();
	private Map<Integer, Rectangle2D.Double> hashMapConsumedItems = new HashMap<Integer, Rectangle2D.Double>();
	private Shape consumerShapeThree;
	private Shape bufferSlotOne;
	private Shape bufferSlotTwo;
	private Shape bufferSlotThree;
	private Shape lockProducer;
	private Shape lockConsumer;
	private Boolean displayLockProducer;
	private Boolean displayLockConsumer;
	private Boolean displayWaitProducer;
	private Boolean displayWaitConsumer;
	private Boolean pauseAnimation;

	public Animation(JTextPane textArea, JSlider producerSlider, JSlider consumerSlider, JSlider bufferSlider) {
		this.shapeObjectID = 1;
		this.copyOfShapeObjectID = 1;
		this.nextProducerID = 1;
		this.nextConsumerID = 1;
		this.nextBufferID = 1;
		this.textArea = textArea;
		this.producerSlider = producerSlider;
		this.consumerSlider = consumerSlider;
		this.bufferSlider = bufferSlider;
		this.lockProducer = new Arc2D.Double(125,15,100,120,0,180,1); // x,y,w,h
		this.lockConsumer = new Arc2D.Double(965,11,100,120,0,180,1);
		this.displayLockProducer = false;
		this.displayLockConsumer = false;
		this.displayWaitProducer = false;
		this.displayWaitConsumer = false;
		this.pauseAnimation = true;
	}


	public synchronized void stopAnimation()
	{
		this.pauseAnimation = true;
	}

	public synchronized void startAnimation()
	{
		this.pauseAnimation = false;
		notifyAll();
	}

	public synchronized void waitForResume() 
	{
		while (pauseAnimation)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean getValuePauseAnimation()
	{
		return pauseAnimation;
	}

	public JSlider getBufferSlider() {
		return bufferSlider;
	}

	public int randomWidthShape()
	{
		int n = 0;
		int max = 30;
		int min = 10;
		Random rand = new Random();
		n = rand.nextInt(max) + min;

		return n;	
	}

	public int randomHeightShape()
	{
		int n = 0;	
		int max = 30;
		int min = 10;
		Random rand = new Random();

		n = rand.nextInt(max) + min;

		return n;
	}


	public int creatingProducer()
	{
		int producerID = 0;

		this.producerShapeOne = new Rectangle2D.Double(100, 75, 150, 150);
		producerID = nextProducerID++;

		return producerID;			
	}

	public int creatingConsumer()
	{		
		int consumerID = 0;	
		this.consumerShapeThree = new Rectangle2D.Double(940, 70, 150, 150);
		consumerID = nextConsumerID++;

		return consumerID;
	}

	public  String creatingBuffer(String name)
	{
		this.bufferSlotOne = new Rectangle2D.Double(465, 190, 75, 75);
		this.bufferSlotTwo = new Rectangle2D.Double(545, 190, 75, 75);
		this.bufferSlotThree = new Rectangle2D.Double(625, 190, 75, 75);
		StringBuilder sb = new StringBuilder();

		sb.append("Buffer");
		sb.append(nextBufferID);
		this.nextBufferID++;
		String nextNameOfBuffer = sb.toString();

		return nextNameOfBuffer;

	}

	public int creatingFruit(int producerID)
	{
		int width = randomWidthShape();
		int height = randomHeightShape();
		Rectangle2D.Double shapeObject =  new Rectangle2D.Double(150, 175, width, height);
		Rectangle2D.Double copyOfShapeObject = new Rectangle2D.Double(shapeObject.getX(), shapeObject.getY(),
				shapeObject.getWidth(), shapeObject.getHeight());

		waitForResume();
		repaint();
		int shapeID = shapeObjectID++;
		copyOfShapeObjectID = shapeID;

		synchronized(hashMap) 
		{
			hashMap.put(shapeID, shapeObject);
		}

		synchronized(hashMapProducedItems)
		{
			hashMapProducedItems.put(copyOfShapeObjectID, copyOfShapeObject);
		}
		appendToPane(textArea, "-- Producer has produced a Fruit\n", Color.BLACK);

		return shapeID;
	}

	public void copyOfProducedItems(int ID)
	{
		Rectangle2D.Double copyOfProducedObject;

		synchronized(hashMapProducedItems)
		{
			copyOfProducedObject = hashMapProducedItems.get(ID);
		}

		copyOfProducedObject.x = 50;
		copyOfProducedObject.y = 200 + (ID * 50);
		repaint();
	}

	public void headingToStorage(int fruitID)
	{
		Rectangle2D.Double object; 

		synchronized(hashMap) {
			object = hashMap.get(fruitID); 
		}
		while(object.x < 410)
		{
			waitForResume();
			object.x += (410-150)*producerSlider.getValue()/1000.0;
			object.y += (205-175)*producerSlider.getValue()/1000.0;

			if(object.x > 410)
			{	
				object.x = 410;
				object.y = 205;	
			}

			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}

		appendToPane(textArea,"P.gettingLock...\n", Color.BLUE);	
	}

	public void producerFindsSpace()
	{
		appendToPane(textArea, "-- Yes there is space \n", Color.BLACK);
		appendToPane(textArea, "P.storeFruit\n", Color.BLUE);
		appendToPane(textArea, "-- Producer Lock delay...\n", Color.black);
	}

	public void sittingInStorage(int slot, int id, boolean amISignalling)
	{
		Rectangle2D.Double fruitFromHashMap;

		waitForResume();		

		if (amISignalling == true)
		{
			appendToPane(textArea, "P.signal // that there is Fruit\n", Color.BLUE);
		}

		synchronized(hashMap)
		{
			fruitFromHashMap = hashMap.get(id);
			fruitFromHashMap.x = 415 + 80 * slot; 
			fruitFromHashMap.y = 205;
		}
		repaint();	
	}

	public void leavingStorage(int id, int idConsumer)
	{
		Rectangle2D.Double fruitFromHashMap;

		waitForResume();
		synchronized(hashMap)
		{
			fruitFromHashMap = hashMap.get(id);
			fruitFromHashMap.x = 730;
			fruitFromHashMap.y = 205;
		}

		appendToPane(textArea, "-- Yes there is Fruit\n", Color.BLACK);
		appendToPane(textArea, "C.removeFruit\n", Color.MAGENTA); 
		appendToPane(textArea, "C.signal // that Fruit has been removed\n", Color.MAGENTA); 
		repaint(); 
	}

	public void beingConsumed(int ID) 
	{
		Rectangle2D.Double object;
		Rectangle2D.Double objectToBeConsumed;

		synchronized(hashMap)
		{
			object = hashMap.get(ID); 
		}

		while(object.x < 990)
		{
			waitForResume();
			object.x += (990-730)*consumerSlider.getValue()/1000.0;
			object.y += (170-205)*consumerSlider.getValue()/1000.0;

			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			repaint();
		}

		synchronized(hashMap)
		{
			objectToBeConsumed = hashMap.get(ID);
		}

		Rectangle2D.Double copyOfShapeObjectConsumed = new Rectangle2D.Double(objectToBeConsumed.getX(),objectToBeConsumed.getY(), objectToBeConsumed.getWidth(), objectToBeConsumed.getHeight());

		synchronized(hashMap)
		{
			hashMapConsumedItems.put(ID, copyOfShapeObjectConsumed);
		}

		waitForResume();

		Rectangle2D.Double objectConsumed;

		synchronized(hashMapConsumedItems)
		{
			objectConsumed = hashMapConsumedItems.get(ID);
			objectConsumed.x = 900;
			objectConsumed.y = 200 + (ID * 50);
		}

		synchronized(hashMap)
		{
			hashMap.remove(ID);
		}

		repaint(); 
	}



	public void showLockProducer()
	{
		this.displayLockProducer = true;
		appendToPane(textArea, "-- Producer gets Lock on Storage\n", Color.black); 
		appendToPane(textArea, "P.isThereSpaceInStorage?\n", Color.BLUE);
		repaint();	
	}

	public void hideLockProducer()
	{
		this.displayLockProducer = false;
		repaint();
	}

	public void givesUpLockProducer()
	{
		appendToPane(textArea, "P.releaseLock\n", Color.BLUE);
	}

	public void givesUpLockConsumer()
	{
		appendToPane(textArea, "C.releaseLock\n", Color.MAGENTA);
	}

	public void showLockConsumer()
	{
		this.displayLockConsumer = true;
		appendToPane(textArea, "-- Consumer gets Lock on Storage\n", Color.BLACK); 
		appendToPane(textArea, "C.isThereFruitInStorage?\n", Color.MAGENTA);		
		appendToPane(textArea, "-- Consumer Lock delay...\n", Color.black); 
		repaint();
	}

	public void hideLockConsumer()
	{
		this.displayLockConsumer = false;
		repaint();	
	}

	public void showWaitProducer()
	{
		this.displayWaitProducer = true;
		appendToPane(textArea, "-- No space in Storage\n", Color.BLACK); 
		appendToPane(textArea, "P.releaseLock\n", Color.BLUE); 
		appendToPane(textArea, "P.wait // for Storage space\n", Color.BLUE); 
		repaint();	
	}

	public void hideWaitProducer()
	{
		this.displayWaitProducer = false;
		repaint();
	}

	public void showWaitConsumer()
	{
		this.displayWaitConsumer = true;
		appendToPane(textArea, "-- No Fruit in Storage\n", Color.BLACK); 
		appendToPane(textArea, "C.releaseLock\n", Color.MAGENTA); 
		appendToPane(textArea, "C.wait // for Fruit\n", Color.MAGENTA); 
		repaint();	
	}

	public void hideWaitConsumer()
	{
		this.displayWaitConsumer = false;
		repaint();
	}

	public void attemptingToAcquireLock()
	{
		appendToPane(textArea, "C.gettingLock...\n", Color.MAGENTA);
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
	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.setColor(Color.BLUE);
		g2.fill(producerShapeOne);

		int fontSize = 80;
		g2.setFont(new Font("Arial Black", Font.BOLD, fontSize)); 
		g2.setColor(Color.white);
		g2.drawString(" P ", 130, 150);

		g2.setColor(Color.MAGENTA);
		g2.fill(consumerShapeThree);

		g2.setFont(new Font("Arial Black", Font.BOLD, fontSize)); 
		g2.setColor(Color.white);
		g2.drawString(" C ", 955, 150);

		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(10));
		g2.draw(bufferSlotOne);

		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(10));
		g2.draw(bufferSlotTwo);

		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(10));
		g2.draw(bufferSlotThree);

		if (displayLockProducer) 
		{
			g2.setColor(Color.blue);
			g2.setStroke(new BasicStroke(10));
			g2.draw(lockProducer);
		}
		else 
		{
			g2.setColor(Color.BLACK);
			g2.fill(lockProducer);
		}

		if (displayLockConsumer) 
		{
			g2.setColor(Color.magenta);
			g2.setStroke(new BasicStroke(10));
			g2.draw(lockConsumer);
		}

		else {
			g2.setColor(Color.BLACK);
			g2.fill(lockConsumer);
		}

		if (displayWaitProducer) 
		{
			g2.setColor(Color.red);
			g2.drawString(" P ", 130, 150);
		}
		else 
		{
			g2.setColor(Color.white);
			g2.drawString(" P ", 130, 150);
		}

		if (displayWaitConsumer) 
		{
			g2.setColor(Color.red);
			g2.drawString(" C ", 955, 150);
		}

		else 
		{
			g2.setColor(Color.white);
			g2.drawString(" C ", 955, 150);
		}

		synchronized(hashMapProducedItems)
		{
			g2.setColor(Color.BLUE);
			{
				for ( Integer key : hashMapProducedItems.keySet() ) 
				{
					g2.fill(hashMapProducedItems.get(key));
				}
			}
		}

		synchronized(hashMap)
		{
			g2.setColor(Color.white);
			for ( Integer key : hashMap.keySet() ) 
			{
				g2.fill(hashMap.get(key));
			}
		}

		synchronized(hashMapConsumedItems)
		{
			g2.setColor(Color.MAGENTA);
			for ( Integer key : hashMapConsumedItems.keySet() ) 
			{
				g2.fill(hashMapConsumedItems.get(key));
			}
		}

	}

	public void update()
	{
	}
}
