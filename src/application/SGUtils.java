package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.TransferHandler;

import application.explorer.FileUtils;
import application.filters.BinaryFilter;
import application.filters.CombineFilter;
import application.filters.MonoFilter;
import application.filters.NegativeFilter;
import application.filters.Norm;
import application.filters.RGBFilter;
import application.filters.YIQFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;

public class SGUtils {
	private List<ImagePane> imagesToCombine;
	private ImagePane editImagePane;
	
	private MainModel main;
	
	private static SGUtils instance;
	
	/**
	 * Image name to use in saves */
	private String imageName;	
	
	private SGUtils() {}
	
	public void setMainModel( MainModel model ) {
		this.main = model;
	}
	
	public static SGUtils getInstance() {
		if( SGUtils.instance == null ) {
			SGUtils.instance = new SGUtils();
		}
		return SGUtils.instance;
	}
	
	public ImagePane getImagePane() {
		return editImagePane;
	}

	public void setImagePane(ImagePane imagePane) {
		this.editImagePane = imagePane;
	}

	public void loadImages() {
		BufferedImage image = FileUtils.openImage( null, null );
		if( image != null ) {
			if( this.editImagePane != null )
				this.editImagePane.setImageUtils( new ImageUtils( image ) );
			else {
				this.editImagePane = new ImagePane( SwingFXUtils.toFXImage(image, null) );
				main.getMainImagePane().getChildren().add( editImagePane.getCanvas() );
			}
		}
	}
	
	public void addImageToCombine() {
		BufferedImage image = FileUtils.openImage(null, null);
		if( image == null ) return;
		ImagePane pane = new ImagePane( SwingFXUtils.toFXImage( image, null) );
		addImagePane( pane );
		
		main.getImagesBox().getChildren()
			.add( pane.getCanvas() );
	}
	public void addImagePane( ImagePane pane ) {
		if( imagesToCombine == null ) {
			this.imagesToCombine = new ArrayList<>();
		}
		imagesToCombine.add( pane );
	}
	
	/* **** Image  **** */
	public void combineImages( boolean maintainAspectRatio ) {
		if( imagesToCombine == null || imagesToCombine.size() == 0 ) return;
		
		CombineFilter cf = new CombineFilter();
		WritableImage combineImage = null;
		int index = 0; // index to FOR, important in case of image == null and images to combine > 1
		
		if( editImagePane != null ) {
			combineImage = (WritableImage)editImagePane.getImageUtils().getImage();
		} else {
			combineImage = (WritableImage)imagesToCombine.get( index++ ).getImageUtils().getImage();
		}
		
		for( ; index < imagesToCombine.size(); index++ ) {
			cf.combine( (WritableImage)imagesToCombine.get(index).getImageUtils().getImage(),
					combineImage, maintainAspectRatio );
		}
		
		if( editImagePane == null ) {
			this.editImagePane = new ImagePane( combineImage );
			main.getMainImagePane().getChildren().add( editImagePane.getCanvas() );
		}
		else {
			this.editImagePane.update();
			this.setHasUnsavedChanges(true);	
		}
		
	}
	
	public void removeImagePane( ImagePane pane ) {
		
		
		if( pane == editImagePane ) {
			this.editImagePane = null;
			main.getMainImagePane().getChildren().remove(0);
			return;
		}
		if( imagesToCombine == null ) return; //only for safe
		
		for( int i = 0; i < imagesToCombine.size(); i++ ) {
			if( pane == imagesToCombine.get(i) ) {
				main.getImagesBox().getChildren().remove(i);
				imagesToCombine.remove(i);
				break;
			}	
		}
	}
	
	
	/* **** Filters  **** */
	public void convertToMono( int type ) {
		MonoFilter mono = new MonoFilter();
		mono.setChannel( type )
			.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	public void setChannels( int type ) {
		new RGBFilter()
			.setOptions( type )
			.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	public void setLuminosite( int type, double value ) {
		new YIQFilter()
			.setOptions( type )
			.setValue( value )
			.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	
	public void negative( boolean isRGB ) {
		NegativeFilter f = new NegativeFilter();
		if( isRGB ) {
			f.apply( (WritableImage)editImagePane.getImageUtils().getImage() );	
		}
		else {
			f.apply( (WritableImage)editImagePane.getImageUtils().getImage(), true );
		}
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	/* Norm */
	public void applyNorm( boolean type ) {
		
		new Norm().apply( (WritableImage)editImagePane.getImageUtils().getImage(), type );	
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	
	/* Binary filter */
	public void binarize( int threshold ) {
		new BinaryFilter()
			.setOptions( threshold )
			.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	/* **** Filters  **** */
	
	public void undo() {
		if( editImagePane != null ) {
			this.editImagePane.undo();
			this.setHasUnsavedChanges(true);
		}
	}
	
	public void redo() {
		if( editImagePane != null ) {
			this.editImagePane.redo();
			this.setHasUnsavedChanges(true);
		}
	}
	
	
	
	/* Zoom */
	public void zoomChange( double zoom ) {
		this.editImagePane.setCanvasZoom(zoom);
	}
	
	/* --- File Options --- */
	public void saveImage() {
		try {
			if( imageName == null ) {
				this.imageName = FileUtils
					.saveImage( ImageFXUtils.fromFxImage(editImagePane.getImageUtils().getImage()) )
					.getAbsolutePath();
			}
			else {
				FileUtils.saveImage(ImageFXUtils.fromFxImage(editImagePane.getImageUtils().getImage()), new File(imageName) );
				
			}
			this.setHasUnsavedChanges(false);
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
		
		
	}
	
	public void saveAsImage() {
		this.imageName = null;
		this.saveImage();
	}
	
	
	/* ** Private  ** */
	private void updateSavedLabel( String message ) {
		main.getSavedLabel().setText( message != null ? message : "Saved" );
	}
	private void setHasUnsavedChanges( boolean has ) {
		if( has )
			this.updateSavedLabel( "*" );
		else 
			this.updateSavedLabel( "Saved" );
	}
}
