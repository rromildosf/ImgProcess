package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
			this.editImagePane.setImageUtils( new ImageUtils( image ) );
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
	public void combineImages() {
		CombineFilter cf = new CombineFilter();
		
		WritableImage combineImage = null;
		int index = 0; // index to FOR, important in case of image == null and images to combine > 1
		if( editImagePane.hasImage() ) {
			combineImage = (WritableImage)editImagePane.getImageUtils().getImage();
		}
		else if( imagesToCombine.size() > 0 ) {
			combineImage = (WritableImage)imagesToCombine.get(0).getImageUtils().getImage();
			index++;
		}
		
		for( ; index < imagesToCombine.size(); index++ ) {
			System.out.println(imagesToCombine.get(index).getImageUtils().getImage());
			cf.combine( (WritableImage)imagesToCombine.get(index).getImageUtils().getImage(), combineImage );
		}
		
		if( !editImagePane.hasImage() ) {
			editImagePane.setImageUtils( new ImageUtils(combineImage) );
		}
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);

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
		this.editImagePane.undo();
		
		this.setHasUnsavedChanges(true);
	}
	
	public void redo() {
		this.editImagePane.redo();
		
		this.setHasUnsavedChanges(true);
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
