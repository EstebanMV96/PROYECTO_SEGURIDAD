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

public class Cifrador {
	
	
	private  SecretKeySpec key ;
	
	public void setLLave(String n)
	{
		
		key = new SecretKeySpec(n.getBytes(),0,16, "AES");
		
	}
	
	
	public Cifrador() throws IOException
	{
		
	}
	

	
	
	
	public byte[] cifrar(String nomArch)
	{
		 try {
			
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);	
			File archivo=new File(nomArch);
			byte[] bytesArch=new byte[(int) archivo.length()];
			FileInputStream fis=new FileInputStream(archivo);
			fis.read(bytesArch);
			fis.close();
			//String[] gu=nomArch.split("\\.");
			byte[] campoCifrado = cipher.doFinal(bytesArch);
			System.out.println("ARCHIVO CIFRADO");
			return campoCifrado;
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 return null;
	
	}
	
	public void des(String nomArch)
	{
		try{
			Cipher cipher=Cipher.getInstance("AES");
			String[] f=nomArch.split("\\.");
			File arch=new File(f[0]+".backup");
			File arch1=new File(nomArch);
			FileInputStream fis=new FileInputStream(arch1);
			byte[] a=new byte[(int)arch1.length()];
			fis.read(a);
			fis.close();
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] datosDecifrados = cipher.doFinal(a);
			FileOutputStream fos=new FileOutputStream(arch);
			fos.write(datosDecifrados);
			fos.close();
			
		}catch (Exception e) {
		   e.printStackTrace();
		  }
	}

 

}
