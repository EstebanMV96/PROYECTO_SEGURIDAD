package interfaz;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.File;

import control.FileClient;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class Ventana {

    @FXML // fx:id="txtNombreRuta"
    private TextField txtNombreRuta; // Value injected by FXMLLoader

    private FileClient fc;
    File file;
    
    @FXML
    void enviar(ActionEvent event) {
    	
    	if(file!=null)
    	{
    	
    		fc.enviarArchivoCifrado(file.getAbsolutePath(),file.getName());
    	}

    }

    @FXML
    void seleccionarRuta(ActionEvent event) {
    	
    	FileChooser fileChooser = new FileChooser();
    	Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	file = fileChooser.showOpenDialog(appStage);
    	if(file!=null)
    	txtNombreRuta.setText(file.getAbsolutePath());
    }
    
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
 
    	txtNombreRuta.setEditable(false);

    }
    
    public void asignarFc(FileClient f)
    {
    	fc=f;
    }

}
