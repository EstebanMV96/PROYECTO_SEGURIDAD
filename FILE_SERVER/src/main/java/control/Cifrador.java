package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Cifrador {
	
	
	private  SecretKeySpec key ;
	
	
	public Cifrador() throws IOException
	{
		
		

	}
	
	public void setLLave(String n)
	{
		
		
		key = new SecretKeySpec(n.getBytes(),0,16, "AES");
	}
	
	
	
	public void cifrar(String nomArch)
	{
		 try {
			
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			File archivo=new File(nomArch);
			byte[] bytesArch=new byte[(int) archivo.length()];
			FileInputStream fis=new FileInputStream(archivo);
			fis.read(bytesArch);
			fis.close();
			String[] gu=nomArch.split("\\.");
			byte[] campoCifrado = cipher.doFinal(bytesArch);
			FileOutputStream fos=new FileOutputStream(new File(gu[0]+".protect"));
			fos.write(campoCifrado);
			fos.close();
			System.out.println("ARCHIVO CIFRADO");
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	
	}
	
	public void des(String nomArch)
	{
		try{
			Cipher cipher=Cipher.getInstance("AES");
			String[] f=nomArch.split("\\.");
			File arch=new File(f[0]+".mp4");
			File arch1=new File(nomArch);
			FileInputStream fis=new FileInputStream(arch1);
			byte[] a=new byte[(int)arch1.length()];
			fis.read(a);
			fis.close();
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] datosDecifrados = cipher.doFinal(a);
			FileOutputStream fos=new FileOutputStream(arch);
			
			fos.write(datosDecifrados);
			gethashMD5(arch);
			fos.close();
			
		}catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	
	public void gethashMD5(File arch){

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			
			byte[] bytesArch = new byte[(int) arch.length()];
			FileInputStream fi = new FileInputStream(arch);
			fi.read(bytesArch);
			md.update(Files.readAllBytes(Paths.get(arch.getAbsolutePath())));
			byte[] digest = md.digest();
			String myHash = DatatypeConverter
					.printHexBinary(digest).toUpperCase();
			System.out.println("Mi CheckSum MD5 SERVER   "+ myHash);
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
