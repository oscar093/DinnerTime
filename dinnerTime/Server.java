package dinnerTime;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class Server extends Thread{
	private int port;
	private Pool pool;
	
	public Server(int port, int nbrOfThreads){
		this.port = port;
		pool = new Pool(nbrOfThreads);
		pool.start();
	}
	
	public void run(){
		while(true){
			try(ServerSocket ss = new ServerSocket(port)){
				Socket socket = ss.accept();
				pool.addRunnable(new ClientHandler(socket));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private class ClientHandler implements Runnable{
		Socket socket;
		
		public ClientHandler(Socket socket){
			this.socket = socket;
		}
		
		public void run(){
			//hämtar / skickar
		}
	}
	
	public static void main(String[] args){
		Server server = new Server(3250, 5);
		server.start();
	}
}
