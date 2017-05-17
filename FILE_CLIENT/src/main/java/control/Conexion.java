package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;

import javax.net.ssl.SSLSocketFactory;

public class Conexion extends Thread{
	
	private Socket   conexion;
	private BufferedReader br;  
	private PrintWriter pr;
	private ObjectOutputStream oos;  //Escribir objetos
	private ObjectInputStream ois;
	public static final int PUERTO=5005;
	public String HOST="localhost";
	private DiffieHellman dh;
	private FileClient fc;

	public Conexion(DiffieHellman dh, FileClient fc) {
		
		try{
			this.fc=fc;
			conexion=new Socket(HOST, PUERTO);
			br=new BufferedReader(new InputStreamReader(conexion.getInputStream(),"UTF-8"));
			pr=new PrintWriter(new OutputStreamWriter(conexion.getOutputStream(), "UTF-8"),true);
			oos=new ObjectOutputStream(conexion.getOutputStream());
			ois=new ObjectInputStream(conexion.getInputStream());
			this.dh=dh;
			
		}catch(Exception e)
		{
			
		}
	
	}
	
	
	public void run()
	{
		while(true)
		{
			escribirMensaje(Protocolo.SALUDO);
			if(recibirMensaje().equals(Protocolo.ACK))
			{
				escribirMensaje(Protocolo.PK);
				
				escribirObjeto(dh.getPublicKey());
				if(recibirMensaje().equals(Protocolo.PK))
				{
					dh.asignarLlaveP((PublicKey) recibirObjeto());
					dh.generateCommonSecretKey();
					fc.cifrarArchivo("ak.txt", dh.darClaveEnComun());
					escribirMensaje(Protocolo.FILE);
					File n=new File("ak.protect");
					byte[] bytesArch=new byte[(int) n.length()];
					FileInputStream fis;
					try {
						fis = new FileInputStream(n);
						fis.read(bytesArch);
						fis.close();
						escribirObjeto(bytesArch);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			break;
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

}
