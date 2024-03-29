package dinnerTime;

import java.io.*;
import java.net.*;

public class Client extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean OKToLogIn;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			while (true) {// lyssnar från servern
				try {
					Object obj = ois.readObject();

					if (obj instanceof String) {
						String str = obj.toString();
						if (str.startsWith("recipe")) {
							str = str.substring(6);
							System.out.println(str);
						}
					}
					
					if(obj instanceof Boolean){
						boolean value = (boolean)obj;
						OKToLogIn = value;
					}

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
	
	public boolean OKToLogIn(){
		return OKToLogIn;
	}
	
	public void setToFalse(){
		OKToLogIn = false;
	}
}