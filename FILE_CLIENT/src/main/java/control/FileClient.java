package control;

import java.io.File;

import vista.VistaPrincipal;

public class FileClient {
	
	private File archivo;
	private DiffieHellman dh;
	public FileClient() {
		// TODO Auto-generated constructor stub
		dh=new DiffieHellman();
		dh.generateKeys();
		new Conexion(dh).start();
		inicializarVista();
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

}
