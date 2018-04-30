package application.dialogs;

import application.SGUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CombineDialogController {

    @FXML
    private Button applyBtn;

    @FXML
    private CheckBox aspectRatioCheck;

    @FXML
    private Button cancelBtn;

    @FXML
    void applyBtnAction() {
    	SGUtils.getInstance().combineImages( aspectRatioCheck.isSelected() );
    }

    @FXML 
    private DialogPane dialog;

    
    @FXML
    void cancelAction() {
    	cancelBtn.getScene().getWindow().hide();
    }
    
    

}
