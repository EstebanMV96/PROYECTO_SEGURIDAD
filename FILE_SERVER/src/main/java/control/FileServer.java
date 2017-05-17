package control;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class FileServer {
	
	
	private Server servidor;

	public FileServer() {
		
		servidor=new Server();
		servidor.start();
		
	}

	public static void main(String[] args) {
		
		
		new FileServer();

	}
	

	 
	

}
