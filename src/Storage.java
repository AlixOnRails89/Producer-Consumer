/*
 * 
 * Class: Storing Numbers.
 * 
 */
public class Storage {


	// the state of the buffer, the default value is false which means the buffer is null.

	private boolean doIHaveSpaceFirstSlot = true;
	private boolean doIHaveSpaceSecondSlot = true;
	private boolean doIHaveSpaceThirdSlot = true;

	private Fruit slotOne;
	private Fruit slotTwo;
	private Fruit slotThree;

	private Animation animation;
	private String nameOfBuffer;

	public Storage(Animation myAnimation) 
	{

		this.animation = myAnimation;
		this.nameOfBuffer = myAnimation.creatingBuffer(nameOfBuffer);	
	}

	public synchronized void storeFruit(Fruit fruit) // If there is space then add a Fruit.
	{
		animation.showLockProducer();

		while ((doIHaveSpaceFirstSlot == false) &&
				(doIHaveSpaceSecondSlot == false) &&
				(doIHaveSpaceThirdSlot == false))  
		{
			//Producer finds there is no space in Storage and gives up Lock and waits for notification.
			animation.hideLockProducer();

			try {
				animation.showWaitProducer();
				wait();
				animation.showLockProducer();
				animation.hideWaitProducer();
			} catch (InterruptedException e) {}
		}

		//Producer finds there is space available in Storage and stores the Fruit.
		animation.producerFindsSpace();

		if(doIHaveSpaceThirdSlot == true)
		{
			slotThree = fruit;
			fruit.iAmInStorageSlotThree(true);
			doIHaveSpaceThirdSlot = false;
		}
		else if(doIHaveSpaceSecondSlot == true)
		{
			slotTwo = fruit;
			fruit.iAmInStorageSlotTwo(true);
			doIHaveSpaceSecondSlot = false;
		}
		else if(doIHaveSpaceFirstSlot == true)
		{
			slotOne = fruit;
			fruit.iAmInStorageSlotOne(true);
			doIHaveSpaceFirstSlot = false;
		}

		//Producer signals to waiting threads that space in Storage has changed.
		notifyAll(); // Wakes up all threads that are wait();
		animation.hideLockProducer();
		animation.givesUpLockProducer();
	}

	/*
	 * 
	 * Synchronized Method - When one thread is executing a synchronized method
	 * for an object, all other threads that invoke synchronized methods for the same 
	 * object block until the first thread is done with the object.
	 * 
	 */
	public synchronized Fruit retrieveFruit(int consumerID) 
	{
		Fruit fruitFromSlotThree = null;

		//Consumer has acquired Lock on Storage
		animation.showLockConsumer();

		//Consumer checks if there is Fruit available in Storage. 
		while (doIHaveSpaceThirdSlot == true)
		{
			//Consumer finds there is no Fruit in Storage and gives up Lock and waits for notification.			
			animation.hideLockConsumer();

			try {
				animation.showWaitConsumer();
				wait(); // Told to wait() until some event has occurred and releases the lock. 
				animation.showLockConsumer();
				animation.hideWaitConsumer();
			} catch (InterruptedException e) {}	
		}

		fruitFromSlotThree = slotThree;
		fruitFromSlotThree.iAmLeavingStorage(consumerID);
		doIHaveSpaceThirdSlot = true;

		if(doIHaveSpaceSecondSlot == false)
		{
			slotThree = slotTwo;
			slotThree.iAmInStorageSlotThree(false);
			doIHaveSpaceThirdSlot = false;
			doIHaveSpaceSecondSlot = true;
		}

		if(doIHaveSpaceFirstSlot == false)
		{
			slotTwo = slotOne;
			slotTwo.iAmInStorageSlotTwo(false);
			doIHaveSpaceSecondSlot = false;
			doIHaveSpaceFirstSlot = true;
		}

		//Consumer signals to waiting threads that space in Storage has changed.
		notifyAll();

		animation.hideLockConsumer();
		animation.givesUpLockConsumer();

		return fruitFromSlotThree;
	}
}
