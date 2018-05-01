package application.dialogs;

import application.SGUtils;
import application.filters.RGBFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class RGBDialogController {

    @FXML
    private CheckBox redChannel;

    @FXML
    private CheckBox greenChannel;

    @FXML
    private CheckBox blueChannel;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    @FXML
    void onCancel(ActionEvent event) {
    	cancelBtn.getScene().getWindow().hide();
    }

    @FXML
    void onOk() {
    	System.out.println("Apply");
    	int type = 
    			(redChannel.isSelected() ? RGBFilter.R : 0) +
    			(greenChannel.isSelected() ? RGBFilter.G : 0) +
    			(blueChannel.isSelected() ? RGBFilter.B : 0);
    	System.out.println(type);
    	SGUtils.getInstance().setChannels(type);
    }
}

