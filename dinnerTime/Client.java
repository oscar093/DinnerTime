package dinnerTime;

import java.io.*;
import java.net.*;

public class Client extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Runnable onConnected;
	
	public void setOnConnected(Runnable onConnected) {
		this.onConnected = onConnected;
	}

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			
			if(onConnected != null) {
				onConnected.run();
			}
			
			ois = new ObjectInputStream(socket.getInputStream());

			while (true) {// lyssnar från servern
				try {
					Object obj = ois.readObject();

					if (obj instanceof Recipe) {
						
					}

					// fler if-satser

				} catch (IOException | ClassNotFoundException e) {
				}
			}
		} catch (IOException e) {
		}
	}

	public void sendToServer(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
		}
	}
}