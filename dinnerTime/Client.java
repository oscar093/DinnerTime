package dinnerTime;

import java.io.*;
import java.net.*;

public class Client extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			//hämta / skicka
			
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 3250);
		client.start();
	}
}
