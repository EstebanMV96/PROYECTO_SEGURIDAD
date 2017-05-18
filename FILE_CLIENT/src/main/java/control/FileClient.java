package control;

import java.io.File;
import java.io.IOException;

import org.omg.CORBA.portable.ApplicationException;

import interfaz.Ventana;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class FileClient extends Application{
	
	
	private DiffieHellman dh;
	private Cifrador cifra;
	private Conexion canal;
	


	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("entrooo");
		inicializarTodo();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/ventana.fxml"));
		Parent root = loader.load();
		Ventana vent=loader.getController();
		vent.asignarFc(this);
		primaryStage.setScene(new Scene(root));		
		primaryStage.show();
	}
	
	public void inicializarTodo()
	{
		dh=new DiffieHellman();
		dh.generateKeys();
		try {
			cifra=new Cifrador();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		canal=new Conexion(this);
		canal.start();
	}
	
	
	
	public void enviarArchivoCifrado(String ruta,String nombre)
	{
		cifra.setLLave(dh.darClaveEnComun());
		byte[] arch=cifra.cifrar(ruta);
		
		if(arch!=null)
		canal.enviarArchivoEncriptado(arch, nombre);
		else
			System.out.println("FALLO LA ENCRIPTACION");
	}
	
	public DiffieHellman darDiffi()
	{
		return dh;
	}

}
