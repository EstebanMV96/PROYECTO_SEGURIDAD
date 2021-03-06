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
	
	private FileClient fc;

	public Conexion(FileClient fc) {
		
		try{
			this.fc=fc;
			conexion=new Socket(HOST, PUERTO);
			br=new BufferedReader(new InputStreamReader(conexion.getInputStream(),"UTF-16"));
			pr=new PrintWriter(new OutputStreamWriter(conexion.getOutputStream(), "UTF-16"),true);
			oos=new ObjectOutputStream(conexion.getOutputStream());
			ois=new ObjectInputStream(conexion.getInputStream());
			negociarClaves();
		}catch(Exception e)
		{
			
		}
	
	}
	
	
	public void enviarArchivoEncriptado(byte[] arch,String nomArch)
	{
		escribirMensaje(Protocolo.FILE);
		escribirMensaje(nomArch);
		escribirObjeto(arch);
		System.out.println("MANDO EL ARCHIVO EL CLIENTE");
//		fc.cifrarArchivo("MyLocation.txt", dh.darClaveEnComun());
//		escribirMensaje(Protocolo.FILE);
//		File n=new File("MyLocation.protect");
//		byte[] bytesArch=new byte[(int) n.length()];
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(n);
//			fis.read(bytesArch);
//			fis.close();
//			escribirObjeto(bytesArch);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
	public void run()
	{
		while(true)
		{
			String r=recibirMensaje();
			
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
	
	
	public void negociarClaves()
	{
		escribirMensaje(Protocolo.SALUDO);
		if(recibirMensaje().equals(Protocolo.ACK))
		{
			escribirMensaje(Protocolo.PK);
			
			escribirObjeto(fc.darDiffi().getPublicKey());
			if(recibirMensaje().equals(Protocolo.PK))
			{
				fc.darDiffi().asignarLlaveP((PublicKey) recibirObjeto());
				fc.darDiffi().generateCommonSecretKey();
			
				
			}
		}
	}

}
