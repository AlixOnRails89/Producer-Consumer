

/**
 * Returns a producer object which extends t
 * @author alexmatthews
 *
 */
public class Producer extends Thread {

	private Storage storage;
	private Animation myAnimation;
	private int producerID;

	public Producer(Storage s, Animation myAnimation) 
	{
		this.storage = s;
		this.producerID = myAnimation.creatingProducer();
		this.myAnimation = myAnimation;
	}

	public void run() 
	{
		while (true)
		{				
			Fruit newFruit = new Fruit(myAnimation, producerID);

			newFruit.storingProducedItems();
			newFruit.iAmOnMyWayToStorage(); // Positions Fruit outside of Storage
			storage.storeFruit(newFruit);
		}
	}
}








