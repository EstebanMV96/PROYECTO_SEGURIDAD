package vista;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class VistaPrincipal extends JFrame{
	
	public VistaPrincipal(){
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(this);
		File file;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            fc.getSelectedFile().getName());
		       file= fc.getSelectedFile();
		    }
	}

}
