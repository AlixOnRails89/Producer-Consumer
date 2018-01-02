import java.util.concurrent.TimeUnit;

import javax.swing.JSlider;

public class Fruit {
	private String name;
	private Animation myAnimation;
	private int ID;

	public Fruit(Animation myAnimation, int producerID) 
	{
		this.myAnimation = myAnimation;

		String[] fruits = new String[6];
		fruits[0] = "Apple";
		fruits[1] = "Orange";
		fruits[2] = "Papaya";
		fruits[3] = "Banana";
		fruits[4] = "Watermelon";
		fruits[5] = "Grapes";

		this.name = fruits[(int) (Math.random() * 6)];
		this.ID = myAnimation.creatingFruit(producerID);
	}

	public void storingProducedItems()
	{	
		myAnimation.copyOfProducedItems(ID);
	}

	public void iAmOnMyWayToStorage()
	{	
		myAnimation.headingToStorage(ID);
	}

	public int sliderValue()
	{
		JSlider slider = myAnimation.getBufferSlider();
		int value = (100*slider.getValue());
		return value;
	}

	public void iAmInStorageSlotOne(boolean amISignalling)
	{
		int value = sliderValue();

		try {
			TimeUnit.MILLISECONDS.sleep(value);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}

		myAnimation.sittingInStorageSlotOne(ID, amISignalling);	
	}

	public void iAmInStorageSlotTwo(boolean amISignalling)
	{
		int value = sliderValue();

		try {
			TimeUnit.MILLISECONDS.sleep(value);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}

		myAnimation.sittingInStorageSlotTwo(ID, amISignalling);	
	}

	public void iAmInStorageSlotThree(boolean amISignalling)
	{
		int value = sliderValue();

		try {
			TimeUnit.MILLISECONDS.sleep(value);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}

		myAnimation.sittingInStorageSlotThree(ID, amISignalling);	
	}



	public void iAmLeavingStorage(int consumerID)
	{
		int value = sliderValue();

		try {
			TimeUnit.MILLISECONDS.sleep(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		myAnimation.leavingStorage(ID, consumerID);
	}

	public void iAmBeingConsumed()
	{
		myAnimation.beingConsumed(ID);
	}

	@Override
	public String toString() {
		return name;
	}

	public Animation getReport() {
		return myAnimation;
	}

	public void setReport(Animation report) {
		this.myAnimation = report;
	}
}
