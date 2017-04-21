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
	private LoginViewController lvc;
	private RegisterViewController rvc;
	
	public void setOnConnected(Runnable onConnected) {
		this.onConnected = onConnected;
	}

	public Client(String ip, int port, LoginViewController lvc) {
		this.ip = ip;
		this.port = port;
		this.lvc = lvc;
	}
	
	public Client(String ip, int port, RegisterViewController rvc) {
		this.ip = ip;
		this.port = port;
		this.rvc = rvc;
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			if(onConnected != null) {
				onConnected.run();
			}
			while (true) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof Login) {
						String loginStatus = ((Login) obj).getLoginStatus();
						lvc.setLoginStatus(loginStatus);
					}
					else if(obj instanceof Register) {
						String registerStatus = ((Register) obj).getRegisterStatus();
						rvc.setRegisterStatus(registerStatus);
					}
					else if(obj instanceof Recipe){
						
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToServer(Object obj) {
		try {
			if(obj instanceof Recipe){
				Recipe recipe = (Recipe)obj;
				System.out.println(recipe.getTitle());

			}
			
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}