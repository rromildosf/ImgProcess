package application.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MDialog extends Stage {

	public MDialog( Window n, String fxml ) {
    	try {
    		initStyle( StageStyle.UTILITY );
    		initModality( Modality.NONE );
			FXMLLoader loader = new FXMLLoader( getClass().getResource(fxml) );
	        Parent root = loader.load();
	       
	        Scene scene = new Scene(root);
	        this.setScene(scene);
	        initOwner( n );
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

