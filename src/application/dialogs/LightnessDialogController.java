package application.dialogs;

import application.SGUtils;
import application.filters.YIQFilter;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;

public class LightnessDialogController {
	@SuppressWarnings("unused")
	private ToggleGroup tg = new ToggleGroup(); // used on fxml
	
    @FXML
    private RadioButton multipleRadio;

    @FXML
    private RadioButton addRadio;

    @FXML
    private Spinner<Double> valueSpinner;

    @FXML
    void onApplyAction() {
    	int type = multipleRadio.isSelected() ? YIQFilter.MULT : YIQFilter.ADD;
    	SGUtils.getInstance().setLightness(type, valueSpinner.getValue() );
    }

    @FXML
    void onCloseAction() {
    	addRadio.getScene().getWindow().hide();
    }
    
    @FXML
    public void initialize() {
    	SpinnerValueFactory.DoubleSpinnerValueFactory sf = 
    			new SpinnerValueFactory.DoubleSpinnerValueFactory(-255.0, 255.0, 0.0, 0.1 );
    	valueSpinner.setValueFactory( sf );   
    	valueSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
    		  if (!newValue) {
    		    valueSpinner.increment(0); // won't change value, but will commit editor
    		  }
    		});
    }

}
