/*
 * Consumer Class extends Thread.
 * 
 * Consumer is a single thread. We extended the thread class.
 * 
 */

public class Consumer extends Thread {

	private Storage storage;
	private int consumerID;
	private Animation animation;

	public Consumer(Storage s, Animation myAnimation) {
		this.storage = s;
		this.consumerID = myAnimation.creatingConsumer();
		this.animation = myAnimation;
	}
	
	public void run() {
		while(true) {
			animation.attemptingToAcquireLock();
			Fruit fruit = storage.retrieveFruit(consumerID);
			fruit.iAmBeingConsumed();
		}
	}
}
