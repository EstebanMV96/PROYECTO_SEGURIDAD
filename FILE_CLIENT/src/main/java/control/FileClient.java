package control;

import java.io.File;
import java.io.IOException;

import vista.VistaPrincipal;

public class FileClient {
	
	private File archivo;
	private DiffieHellman dh;
	private Cifrador cifra;
	private String rutaCifrado;
	
	
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
	
	public void setArchivoSeleccionado(File f)
	{
		archivo=f;
		System.out.println("Archivo seleccionado: "+f.getAbsolutePath());
	}
	
	public void inicializarVista(){
		new VistaPrincipal(this);
	}
	
	public void cifrarArchivo(String ruta, String clave)
	{
		cifra.setLLave(clave);
		File cifrado= cifra.cifrar(ruta);
		rutaCifrado= cifrado.getAbsolutePath();
		 
	}
	
	public String getRutaCifrado(){
		System.out.println(rutaCifrado);
		return rutaCifrado;
	}

	public String getRuta(){
		System.out.println(archivo.getAbsolutePath());
		return archivo.getAbsolutePath();
	}

}
