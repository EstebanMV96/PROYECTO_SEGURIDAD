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
	
	public void des(byte[] info,String rutaDes)
	{
		try{
			Cipher cipher=Cipher.getInstance("AES");
			File arch=new File(rutaDes);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] datosDecifrados = cipher.doFinal(info);
			FileOutputStream fos=new FileOutputStream(arch);
			fos.write(datosDecifrados);
			fos.close();
			
		}catch (Exception e) {
		   e.printStackTrace();
		  }
	}

 

}
