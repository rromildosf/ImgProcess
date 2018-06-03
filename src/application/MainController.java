package application;

import static java.lang.System.out;

import java.net.URL;
import java.util.ResourceBundle;

import application.dialogs.MDialog;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    private VBox rightPane;

    @FXML
    private AnchorPane statusPane;

    @FXML
    private Canvas canvas;
    
    @FXML
    private MenuItem openImagesItem;
    
    @FXML 
    private VBox imageBox;
    
    @FXML 
    private VBox rootBox;
    
    @FXML
    void initialize() {
    	utils = SGUtils.getInstance();
        main = new MainModel();
        utils.setMainModel(main);
        setComponentToModel();

        zoomSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
        	this.zoomChange((double)newValue );        	
        });
        
        buttonAddImageLabel.prefWidthProperty().bind( vboxImagesContainer.widthProperty().subtract(32) );
        
        rootBox.sceneProperty().addListener((obs, old, newScene) -> {
        	if( newScene != null ) {
        		newScene.getAccelerators().put(
        			new KeyCodeCombination( KeyCode.A, KeyCombination.CONTROL_DOWN ), 
					new Runnable() { 
    					@Override public void run() {
    						addImageBtn.fire();
    					}
        			}
    			);
        		
        		newScene.getAccelerators().put(
            		new KeyCodeCombination( KeyCode.N, KeyCombination.CONTROL_DOWN ), 
					new Runnable() { 
    					@Override public void run() {
    						SGUtils.getInstance().applyNorm(true);
    					}
        			}
    			);
        	}
        });
    }
    
    private void setComponentToModel() {
    	main.setSavedLabel(savedLabel);
    	main.setImagesBox( imagesBox );
    	main.setMainImagePane( rightPane );		
    }
    
    
    private void zoomChange( double zoom ) {
    	this.utils.zoomChange(zoom);
    }
    
    @FXML
    void openImageAction() {
    	utils.loadMainImage();	
//		this.setAnchor();
    }
    
    @FXML
    void convertToMono() {
    	new MDialog( imageBox.getScene().getWindow(), "MonoDialog.fxml" ).show();
    }
    
    @FXML CheckBox _red, _green, _blue;
    
    @FXML
    void onRGBItemAction() {
    	new MDialog( imageBox.getScene().getWindow(), "RGBDialog.fxml" ).show();    	
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
    void onLightnessAction() {
    	new MDialog( imageBox.getScene().getWindow(), "LightnessDialog.fxml" ).show();	
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
    
    
    @FXML
    private MenuItem meanItem;
    
    @FXML
    void meanAction () {
    	this.utils.applyMean(4);
    }
    
    @FXML
    private MenuItem medianItem;
    
    @FXML
    void medianAction () {
    	this.utils.applyMedian(3);
    }
    
    
    @FXML
    private MenuItem modeItem;
    
    @FXML
    void modeAction () {
    	this.utils.applyMode(3);
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
    	new MDialog( imageBox.getScene().getWindow(), "CombineDialog.fxml" ).show();
    }
    
    /* --- END Image MENU --- */
    
    
    /* *** Containers *** */
    @FXML
    private VBox imagesBox; 
    
    @FXML
    private VBox vboxImagesContainer;
    
    @FXML
    private ScrollPane leftScrollPane;
    
    @FXML
    private ScrollPane rightScrollPane;
    
    
    /* *** Buttons *** */
    
    @FXML private Button addImageBtn;
    
    @FXML void addImageBtnAction() {
    	utils.addImagesToCombine();
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
