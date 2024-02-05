package consumables;

public abstract class ConsumableAbstract extends Thread {
	
	public int x;
	public int y;
	
	public abstract boolean checkConsumed();
	
	public void delay(int s) {
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
