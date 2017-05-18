package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;

public class Conexion extends Thread{

	private Socket   conexion;
	private BufferedReader br;  
	private PrintWriter pr;
	private ObjectOutputStream oos;  //Escribir objetos
	private ObjectInputStream ois; 
	
	private FileServer fileServer;
	
	public Conexion(Socket c, FileServer fs) {
		
		conexion=c;
		try{
			fileServer=fs;
			br=new BufferedReader(new InputStreamReader(conexion.getInputStream(),"UTF-16"));
			pr=new PrintWriter(new OutputStreamWriter(conexion.getOutputStream(), "UTF-16"),true);
			oos=new ObjectOutputStream(conexion.getOutputStream());
			ois=new ObjectInputStream(conexion.getInputStream());
			intercambioClaves();
		}catch(Exception e)
		{
			
		}
		
	}
	
	
	public void run()
	{
		while(true)
		{
		
		
				if(recibirMensaje().equals(Protocolo.FILE))
				{
					String ruta=recibirMensaje();
					//String[] gu=ruta.split(File.separator);
					System.out.println("ESTA FUE LA RUTA QUE RECIBI "+ruta);
					byte[] bytesArch=(byte[]) recibirObjeto();
					fileServer.des(bytesArch, ruta);
					
				}
		
			}
			
		}
	
	
	
	
	public void escribirMensaje(String mensaje)
	{
		pr.println(mensaje);
	}
	
	public  String recibirMensaje()
	{
		String info="";
		try {
			String res;
			res = br.readLine();
			if(res!=null)
			{
				info=res;
			}
		} catch (IOException e) {
			
			
		}
		
		return info;
	}
	
	public void escribirObjeto(Object o)
	{
		try {
			oos.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public Object recibirObjeto()
	{
		Object res=null;
		try {
			
			Object recibo=ois.readObject();
			if(recibo!=null)
				res=recibo;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		
		}
		
		return res;
		
	}
	
	
	public void cerrarConexiones()
	{
		try {
			pr.flush(); pr.close(); br.close();
			oos.flush(); oos.close(); ois.close();
			conexion.close();
			
		} catch (Exception e) {
			
			
		}
	}
	
	public void intercambioClaves()
	{
		if(recibirMensaje().equals(Protocolo.SALUDO)){
			escribirMensaje(Protocolo.ACK);
			if(recibirMensaje().equals(Protocolo.PK)){
				fileServer.darDiffi().asignarLlaveP((PublicKey) recibirObjeto());
				fileServer.darDiffi().generateCommonSecretKey();
				escribirMensaje(Protocolo.PK);
				escribirObjeto(fileServer.darDiffi().getPublicKey());
				
			}
		}
		
		
	
	}

}
