package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

/**
 * The ThreadPool class is used to manage threads within the program. Incoming
 * Runnable objects are assigned to currently available Threads, which then 
 * execute the Runnable.
 * 
 * @author Marcos Davila
 *
 */
public class ThreadPool extends ThreadGroup {

	private boolean isAlive;
	private LinkedList<Runnable> taskQueue;
	private int threadID;
	private static int threadPoolID;

	/**
	 * Creates a new ThreadPool.
	 * 
	 * @param numThreads
	 *            The number of threads in the pool
	 */
	public ThreadPool(int numThreads) {
		super("ThreadPool-" + (threadPoolID++));
		setDaemon(true);

		isAlive = true;

		taskQueue = new LinkedList<Runnable>();
		
		for (int i = 0; i < numThreads; i++) {
			new PooledThread().start();
		}
	}

	/**
	 * Requests a new task to run. This method returns immediately, and the task
	 * executes on the next available idle thread in this ThreadPool.
	 * <p>
	 * Tasks start execution in the order they are received
	 * 
	 * @param task
	 *            The task to run. If null, no action is taken.
	 * @throws IllegalStateException
	 *             if this ThreadPool is already closed
	 */

	public synchronized void runTask(Runnable task) {
		if (!isAlive) {
			throw new IllegalStateException();
		}
		if (task != null) {
			taskQueue.add(task);
			notify();
		}
	}
	
	/**
	 * Removes a specified task from the list. Should be used only when the
	 * reference to the specific task can be supplied as a parameter.
	 * 
	 * @param task the task to be removed from the thread pool
	 */
	protected synchronized void removeTask( Runnable task ){
		for (Runnable v : taskQueue.stream().filter(t -> t == task).collect(Collectors.toList())){
			taskQueue.remove(v);
		}
	}

	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskQueue.size() == 0) {
			if (!isAlive) {
				return null;
			}

			wait();
		}

		return (Runnable) taskQueue.removeFirst();
	}

	/**
	 * Closes this ThreadPool and returns immediately. All threads are stopped
	 * and any waiting tasks are not executed. Once a ThreadPool is closed, no
	 * more tasks can be run on this ThreadPool
	 */

	public synchronized void close() {
		if (isAlive) {
			isAlive = false;
			taskQueue.clear();
			interrupt();
		}
	}

	/**
	 * Closes this ThreadPool and waits for all running threads to finish. Any
	 * waiting tasks are executed
	 */
	public void join() {
		// notify all waiting threads that this ThreadPool is no longer alive
		synchronized (this) {
			isAlive = false;
			notifyAll();
		}

		// wait for all threads to finish
		
		Thread[] threads = new Thread[activeCount()];
		enumerate(threads);
		
		for (Thread t : threads) {
			try {
				t.join();
			} 
			catch (InterruptedException ex) { }
		}
	}

	/**
	 * A PooledThread is a Thread in the ThreadPool group, designed to run tasks
	 */
	private class PooledThread extends Thread {

		public PooledThread() {
			super(ThreadPool.this, "PooledThread-" + (threadID++));
		}

		public void run() {
			while (!isInterrupted()) {
				// get a task to run
				Runnable task = null;
				try {
					task = getTask();
				} catch (InterruptedException ex) {

				} finally {

					// if getTask() returned null or was interrupted,
					// close this thread by returning
					if (task == null) {
						return;
					}
				}

				// run the task, and eat any exceptions it throws
				try {
					task.run();
				} catch (Throwable t) {
					uncaughtException(this, t);
				}

			}
		}
	}

	/**
	 * Adds a task to the Swing Event thread
	 */
	public void runEDTTask(Runnable task) {
		SwingUtilities.invokeLater(task);
	}
}
