package dinnerTime;

import java.util.LinkedList;

public class Pool {
	private Buffer<Runnable> buffer = new Buffer<Runnable>();
	private LinkedList<Worker> workers;
	private int nbrOfThreads;

	public Pool(int nbrOfThreads) {
		this.nbrOfThreads = nbrOfThreads;
	}

	public void start() {
		Worker worker;
		if (workers == null) {
			workers = new LinkedList<Worker>();
			for (int i = 0; i < nbrOfThreads; i++) {
				worker = new Worker();
				worker.start();
				workers.add(worker);
			}
		}
	}

	public void addRunnable(Runnable r) {
		buffer.put(r);
	}

	private class Worker extends Thread {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					buffer.get().run();
				} catch (InterruptedException e) {
					System.out.println(e);
					break;
				}
			}
		}
	}

	private class Buffer<T> {
		private LinkedList<T> buffer = new LinkedList<T>();

		public synchronized void put(T obj) {
			buffer.addLast(obj);
			notifyAll();
		}

		public synchronized T get() throws InterruptedException {
			while (buffer.isEmpty()) {
				wait();
			}
			return buffer.removeFirst();
		}

		public synchronized void clear() {
			buffer.clear();
		}

		public int size() {
			return buffer.size();
		}
	}
}