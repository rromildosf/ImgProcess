package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;

import application.explorer.FileUtils;
import application.filters.RGBFilter;
import application.filters.YIQFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController {
	
	private MainModel main;
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
        main = new MainModel();
        utils.setMainModel(main);
        setComponentToModel();
        
        
        rightPane.widthProperty()
        .addListener( (obs, oldValue, newValue ) -> {
        	System.out.println(rightPane.getWidth());
        	this.setAnchor();
        });
        
        
        _slider.valueProperty().addListener( 
        		(obs, oldValue, newValue) -> this.onChange((double)newValue) );
        
        zoomSlider.valueProperty().addListener((obs, oldValue, newValue) -> this.zoomChange((double)newValue ) );
        
        /* Images Pane settings */
//        buttonAddImageLabel.prefWidthProperty()
//			.bind( imagesBox.widthProperty().subtract(5) );
//        imagesBox.prefWidthProperty().bind( leftScrollPane.widthProperty().subtract(5) );
//        imagesBox.prefHeightProperty().bind( leftScrollPane.heightProperty().subtract(50) );
    }
    
    private void setComponentToModel() {
    	main.setSavedLabel(savedLabel);
    	main.setImagesBox( imagesBox );
    }
    
    
    private void zoomChange( double zoom ) {
    	System.out.println(zoom * 100);
    	this.utils.zoomChange(zoom);
    	
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
    
    @FXML
    private Slider zoomSlider;
    
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
    
    /* ***  Filters  *** */

    
    /* Negative */
    
    @FXML
    private MenuItem negativeInRGB;
    
    @FXML
    void onNegative() {
    	utils.negative( true );
    }
    
    @FXML
    private MenuItem negativeInY;
    
    @FXML
    void onNegativeY() {
    	utils.negative( false );
    }
    /* Negative -- END */
    
    
    /* Apply norm */
    @FXML
    private Button normBtn;
    
    @FXML
    void applyNorm() {
    	utils.applyNorm(true);
    }
    @FXML
    private Button notNormBtn;
    
    @FXML
    void notNorm() {
    	utils.applyNorm(false);
    }
    
    
    @FXML
    void binarize () {
    	this.utils.binarize( 200 );
    }
    
    
    /* ***  END Filters  *** */
    
    /* --- File MENU --- */
    
    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;
    
    
    @FXML
    void save() {
    	System.out.println("Save");
    	this.utils.saveImage();
    }

    @FXML
    void saveAs() {
    	this.utils.saveAsImage();
    }
    /* --- END File MENU --- */
    
    
    /* --- Image MENU --- */
    
    @FXML 
    void combineImagesMenuItemOnAction() {
    	utils.combineImages();
    }
    
    /* --- END Image MENU --- */
    
    
    /* *** Containers *** */
    @FXML
    private VBox imagesBox; 
    
    @FXML
    private ScrollPane leftScrollPane;
    
    @FXML
    private ScrollPane rightScrollPane;
    
    
    /* *** Buttons *** */
    
    @FXML private Button addImageBtn;
    @FXML void addImageBtnAction() {
    	utils.addImageToCombine();
    }
    
    /* *** END Buttons *** */

    
    /* *** Labels *** */
    @FXML
    private Label buttonAddImageLabel;
    
    @FXML 
    private Label savedLabel;
    
    public void updateSavedLabel( String message ) {
    	this.savedLabel.setText( message != null ? message : "Saved" );
    }
    
    /* *** END Labels *** */
    
    
    
    
}
