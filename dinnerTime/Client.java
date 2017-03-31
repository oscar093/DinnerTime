package dinnerTime;

import java.io.*;
import java.net.*;

public class Client extends Thread{
	private String ip;
	private int port;
	private Socket socket;
	
	public Client(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void run(){
		try{
			socket = new Socket(ip, port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(true){
				//hämtar / skickar
			}		
		}catch(IOException e){}
	}
	
	
}
