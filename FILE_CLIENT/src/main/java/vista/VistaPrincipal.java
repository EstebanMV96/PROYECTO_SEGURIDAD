package vista;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import control.FileClient;

public class VistaPrincipal extends JFrame{
	
	public VistaPrincipal(FileClient cliente){
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(this);
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		      file= fc.getSelectedFile();
		      cliente.setArchivoSeleccionado(file); 
		}else{
			System.out.println("No se seleccionó ningun archivo");
		}
	}

}
