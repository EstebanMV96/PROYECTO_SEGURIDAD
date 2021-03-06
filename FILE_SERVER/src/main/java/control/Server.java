package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

public class Server extends Thread{

	public static final int PUERTO=5005;
	private ServerSocket server;
	private Socket   canal;
	private FileServer fs;
	
	
	public Server(FileServer f) {
		
		try {
			fs=f;
			server=new ServerSocket(PUERTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	public void run()
	{
		while(true)
		{
			try {
				canal=server.accept();
				new Conexion(canal,fs).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
