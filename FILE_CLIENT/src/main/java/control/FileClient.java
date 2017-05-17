package control;

import java.io.File;
import java.io.IOException;

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
		new Conexion(dh,this).start();
		//inicializarVista();
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

}
