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
import application.filters.BorderDetectFilter;
import application.filters.CombineFilter;
import application.filters.GaussianFilter;
import application.filters.MeanFilter;
import application.filters.MedianFilter;
import application.filters.ModeFilter;
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
	public int filterType = -1; 
	
	private List<ImagePane> imagesToCombine;
	private ImagePane editImagePane;
	
	private MainModel main;
	
	private static SGUtils instance;
	
	/**
	 * Image name to use in saves */
	private String imageName;	
	
	private SGUtils() {
		imagesToCombine = new ArrayList<>();
	}
	
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

	/**
	 * Open images to combine with the main image, and replace existing opened images */
	public void loadImagesToCombine( boolean replace ) { 
		List<BufferedImage> images = FileUtils.openImages( null );
		if( replace ) { // fist remove all images, except main image
			for( int i =  1; i < imagesToCombine.size(); i++ ) {
				imagesToCombine.remove(i);
			}
		}
		if( images != null && images.size() > 0 ) {	
			images.forEach( image -> {
				addImagePane( image );
			});	
		}
	}
	
	/**
	 * Open main image, replacing existing main image */
	public void loadMainImage() {
		BufferedImage image = FileUtils.openImage( null, null );
		if( image != null ) {
			setMainImage(  SwingFXUtils.toFXImage(image, null)  );
		}
	}
	
	/**
	 * Set main image
	 */
	private void setMainImage( WritableImage image ) {
		if( this.editImagePane != null ) {
			this.editImagePane.setImageUtils( new ImageUtils( image ) );
			this.imagesToCombine.get(0).setImageUtils( new ImageUtils(image) );
		}
		else {
			this.editImagePane = new ImagePane( image );
			main.getMainImagePane().getChildren().add( editImagePane.getCanvas() );
			addImagePane( image, 0);
		}
	}
	
	
	
	/** 
	 * Add images to combine, without replacing opened images */
	public void addImagesToCombine() {
		this.loadImagesToCombine(false);
	}
	
	/**
	 * Add @param image to left pane and images to combine
	 * @param image
	 * @param index
	 */
	public void addImagePane( WritableImage image, int index ) {
		if( imagesToCombine == null ) {
			this.imagesToCombine = new ArrayList<>();
		}
		ImagePane pane = new ImagePane( image );
		imagesToCombine.add( index, pane );
		
		// add image to left pane
		main.getImagesBox()
			.getChildren()
			.add( index, pane.getCanvas() );
	}
	public void addImagePane( BufferedImage image ) {
		this.addImagePane(  SwingFXUtils.toFXImage(image, null), imagesToCombine.size() );
	}
	
	/* **** Image  **** */
	public void combineImages( boolean maintainAspectRatio ) {
		if( imagesToCombine == null || imagesToCombine.size() == 0 ) return;
		
		CombineFilter cf = new CombineFilter();
		WritableImage combineImage = (WritableImage)imagesToCombine.get(0).getImageUtils().getImage();
		
		int i = 0;
		if( editImagePane != null ) {
			combineImage = (WritableImage)editImagePane.getImageUtils().getImage();
			i= 1;
		}
		
		for( ; i < imagesToCombine.size(); i++ ) {
			cf.combine( (WritableImage)imagesToCombine.get(i).getImageUtils().getImage(),
					combineImage, maintainAspectRatio );
		}

		if( editImagePane == null ) {
			editImagePane = new ImagePane( combineImage );
			main.getMainImagePane().getChildren().add( editImagePane.getCanvas() );
		}
		else
			this.editImagePane.update();
		this.setHasUnsavedChanges(true);		
	}
	
	public void removeImagePane( ImagePane pane ) {
		
		
		if( pane == editImagePane ) {
			this.editImagePane = null;
			main.getMainImagePane().getChildren().remove(0);
			main.getImagesBox().getChildren().remove(0);
			imagesToCombine.remove(0);
			return;
		}
		//if( imagesToCombine == null ) return; //only for safe
		
		for( int i = 0; i < imagesToCombine.size(); i++ ) {
			if( pane == imagesToCombine.get(i) ) {
				
				if( i == 0 ) { // removing original editing image
					this.editImagePane = null;
					main.getMainImagePane().getChildren().remove(0);
				}
				
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
		if( editImagePane == null ) return;
		new RGBFilter()
			.setOptions( type )
			.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		this.editImagePane.update();
		
		this.setHasUnsavedChanges(true);
	}
	
	public void setLightness( int type, double value ) {
		if( editImagePane == null ) return;
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
		
		WritableImage img  = new Norm().transform( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.setImageUtils( new ImageUtils( img ) );
		
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
	
	/* Mean Filter */
	public void applyMean( int windowSize ) {
		new MeanFilter()
		.setOptions( windowSize )
		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}
	
	
	/* Median Filter */
	public void applyMedian( int windowSize ) {
		new MedianFilter()
		.setOptions( windowSize )
		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}
	
	/* Mode Filter */
	public void applyMode( int windowSize ) {
		new ModeFilter()
		.setOptions( windowSize )
		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}
	
	public void applyGaussian() {
		new GaussianFilter()
		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}
	
	
	public void applyEdgeFilter( int type ) {
		new BorderDetectFilter()
		.apply( (WritableImage)editImagePane.getImageUtils().getImage(), type );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}
	/*public void applyMode( int windowSize ) {
		new GaussianFilter()
//		.setOptions( windowSize )
		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
		
		this.editImagePane.update();
		this.setHasUnsavedChanges(true);
	}*/
	
	/* */
	
//	public void applyMode( int windowSize ) {
//		new BorderDetectFilter()
//		.apply( (WritableImage)editImagePane.getImageUtils().getImage() );
//		
//		this.editImagePane.update();
//		this.setHasUnsavedChanges(true);
//	}
	
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
