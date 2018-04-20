package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;

import application.filters.RGBFilter;
import application.filters.YIQFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController {
	
	private SGUtils utils;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar mainWindow;

    @FXML
    private SplitPane mainSplit;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private AnchorPane statusPane;

    @FXML
    private Canvas canvas;
    
    @FXML
    private MenuItem openImagesItem;
    
    @FXML 
    private VBox imageBox;
    
    @FXML
    void initialize() {
    	utils = SGUtils.getInstance();
        utils.setImagePane( new ImagePane( canvas ) );
        
        rightPane.widthProperty()
        .addListener( (obs, oldValue, newValue ) -> {
        	System.out.println(rightPane.getWidth());
        	this.setAnchor();
        });
        
        
        _slider.valueProperty().addListener( 
        		(obs, oldValue, newValue) -> this.onChange((double)newValue) );
    }
    
    private void setAnchor() {
    	double rl = (rightPane.getWidth() - canvas.getWidth())/2;
    	double tb = (rightPane.getHeight() - canvas.getHeight() )/2;
    	AnchorPane.setLeftAnchor( canvas, rl );
    	AnchorPane.setTopAnchor( canvas, tb );
    }
    
    @FXML
    void openImageAction() {
    	utils.loadImages();
        this.setAnchor();
    }
    
    @FXML
    void convertToMono() {
    	System.out.println("called");
    	this.utils.convertToMono(4);
    }
    
    @FXML CheckBox _red, _green, _blue;
    
    @FXML
    void onRGBItemAction() {
    	System.out.println("called RGB channels");
    	utils.setChannels( 
        		(_red.isSelected() ? RGBFilter.R : 0 ) +
        		(_green.isSelected() ? RGBFilter.G : 0 ) +
        		(_blue.isSelected() ? RGBFilter.B : 0 )
    	);    	
    }
    
    /* REDO and UNDO */
    
    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem undoMenuItem;
    
    @FXML
    void onRedo() {
    	utils.redo();
    }

    @FXML
    void onUndo() {
    	utils.undo();
    }
    
    /* REDO and UNDO -- END*/
    
    @FXML
    private Slider _slider;
    
    @FXML
    void onChange( double value ) {
    	System.out.println(value);
    	utils.setLuminosite( YIQFilter.ADD, value );
    }
    
    /* Negative */
    
    @FXML
    private MenuItem negativeMenuItem;
    
    @FXML
    void onNegative() {
    	System.out.println("on negative");
    	utils.negative();
    }
    /* Negative -- END */
    
}
