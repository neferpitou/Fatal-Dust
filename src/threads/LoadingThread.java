package threads;

/**
 * A dedicated thread class that determines what to load and how to load it
 * at the beginning of the game. 
 * 
 * @author Marcos Davila
 * @date 1/29/2014
 */
public class LoadingThread implements Runnable {
	
	Thread t;
	public LoadingThread(){
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Load Materials
		System.out.println("Thread!");
	}

}
