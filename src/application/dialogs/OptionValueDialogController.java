package application.dialogs;

import application.SGUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class OptionValueDialogController {

    @FXML
    private TextField inputValue;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void okAction() {
    	if( SGUtils.getInstance().filterType == 1 ) {
    		SGUtils.getInstance().applyMean( Integer.valueOf(inputValue.getText() ));
    	}
    	
    	else if( SGUtils.getInstance().filterType == 2 ) {
    		SGUtils.getInstance().applyMedian( Integer.valueOf(inputValue.getText() ));
    	}
    	
    	else if( SGUtils.getInstance().filterType == 3 ) {
    		SGUtils.getInstance().applyMode( Integer.valueOf(inputValue.getText() ));
		}

    }

    @FXML
    void onCancel() {
    	okBtn.getScene().getWindow().hide();
    }

}
