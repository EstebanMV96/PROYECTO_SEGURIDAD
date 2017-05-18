package control;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyAgreement;

public class DiffieHellman {

	 private PrivateKey privateKey;
	 private PublicKey  publicKey;
	 private PublicKey  receivedPublicKey;
	 private byte[]     secretKey;
	 private String     secretMessage;
	
	
	  public PublicKey getPublicKey() {

	        return publicKey;
	    }
	  
	  public void generateKeys() {

	        try {
	            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
	            keyPairGenerator.initialize(1024);

	            KeyPair keyPair = keyPairGenerator.generateKeyPair();

	            privateKey = keyPair.getPrivate();
	            publicKey  = keyPair.getPublic();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	  
	  public void generateCommonSecretKey() {

	        try {
	            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
	            keyAgreement.init(privateKey);
	            keyAgreement.doPhase(receivedPublicKey, true);

	            secretKey = shortenSecretKey(keyAgreement.generateSecret());
	            System.out.println("LLAVE EN COMUN SERVER "+new String(secretKey, StandardCharsets.UTF_16));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	  private byte[] shortenSecretKey(final byte[] longKey) {

	        try {

	            // Use 8 bytes (64 bits) for DES, 6 bytes (48 bits) for Blowfish
	            final byte[] shortenedKey = new byte[16];

	            System.arraycopy(longKey, 0, shortenedKey, 0, shortenedKey.length);

	            return shortenedKey;

	            // Below lines can be more secure
	            // final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            // final DESKeySpec       desSpec    = new DESKeySpec(longKey);
	            //
	            // return keyFactory.generateSecret(desSpec).getEncoded();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    }

	  public void asignarLlaveP(PublicKey re)
	  {
		  receivedPublicKey=re;
	  }
	  
	  public String darClaveEnComun()
	  {
		  return new String(secretKey, StandardCharsets.UTF_16);
	  }
	  
}
