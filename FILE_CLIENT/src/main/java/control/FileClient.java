package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import vista.VistaPrincipal;

public class FileClient {
	
	private File archivo;
	private DiffieHellman dh;
	private Cifrador cifra;
	
	
	public FileClient() {
		// TODO Auto-generated constructor stub
		dh=new DiffieHellman();
		dh.generateKeys();
		try {
			cifra=new Cifrador();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializarVista();
		new Conexion(dh,this).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FileClient();
	}
	
	public void setArchivoSeleccionado(File f){
		archivo=f;
		
		System.out.println("Archivo seleccionado: "+f.getAbsolutePath());
	}
	
	public void inicializarVista(){
		new VistaPrincipal(this);
	}
	
	public void cifrarArchivo(String ruta,String clave)
	{
		cifra.setLLave(clave);
		cifra.cifrar(ruta);
	}
	
	public File getFile(){
		
		return archivo;
	}
	
	public String getRutaArchivoCifrado(){
		String gu =archivo.getAbsolutePath().substring(0, archivo.getAbsolutePath().length()-3);
		System.out.println(gu+"protect");
		return gu+"protect";
		
	}
	
	
	public void gethashMD5(){
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		byte[] bytesArch = new byte[(int) archivo.length()];
		FileInputStream fi = new FileInputStream(archivo);
		fi.read(bytesArch);
		md.update(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));
		byte[] digest = md.digest();
		String myChecksum = DatatypeConverter
				.printHexBinary(digest).toUpperCase();
		System.out.println("Mi Hash MD5 CLIENT  "+ myChecksum);
		fi.close();
		md.reset();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
