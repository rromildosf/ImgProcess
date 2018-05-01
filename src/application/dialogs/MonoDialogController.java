package application.dialogs;

import application.SGUtils;
import application.filters.MonoFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MonoDialogController {

	 @FXML
    private RadioButton blueRadio;

    @FXML
    private ToggleGroup tg;

    @FXML
    private RadioButton redRadio;

    @FXML
    private RadioButton rgbRadio;

    @FXML
    private RadioButton greenRadio;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    @FXML
    void onCancel() {
    	okBtn.getScene().getWindow().hide();
    }

    @FXML
    void onOk() {
    	SGUtils.getInstance().convertToMono(
    		rgbRadio.isSelected() ? MonoFilter.M : 
    			redRadio.isSelected() ? MonoFilter.R :
    				greenRadio.isSelected() ? MonoFilter.G :
    					MonoFilter.B
    		
    	);
    }

}
