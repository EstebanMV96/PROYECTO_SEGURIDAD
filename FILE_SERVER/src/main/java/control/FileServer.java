package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class FileServer {
	
	
	private Server servidor;
	private Cifrador cifra;
	public FileServer() {
		
		try {
			cifra=new Cifrador();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		servidor=new Server(this);
		servidor.start();
		
	}

	public static void main(String[] args) {
		
		
		new FileServer();

	}
	
	
	public void des(String nomArch,String clave)
	{
		cifra.setLLave(clave);
		cifra.des(nomArch);
	}

	 
	

}
