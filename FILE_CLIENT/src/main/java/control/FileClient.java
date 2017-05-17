package control;

import vista.VistaPrincipal;

public class FileClient {

	public FileClient() {
		// TODO Auto-generated constructor stub
		new Conexion().start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FileClient();
		new VistaPrincipal();
	}

}
