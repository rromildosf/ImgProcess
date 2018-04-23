package application;

import java.awt.image.BufferedImage;
import java.io.IOException;

import application.explorer.FileUtils;
import application.filters.MonoFilter;
import application.filters.NegativeFilter;
import application.filters.Norm;
import application.filters.RGBFilter;
import application.filters.YIQFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

public class SGUtils {
	private ImagePane imagePane;
	private ImageUtils imageUtils;
	private static SGUtils instance;
	
	private String imageName;	
	
	private SGUtils() {}
	
	public static SGUtils getInstance() {
		if( SGUtils.instance == null ) {
			SGUtils.instance = new SGUtils();
		}
		return SGUtils.instance;
	}
	
	public ImagePane getImagePane() {
		return imagePane;
	}

	public void setImagePane(ImagePane imagePane) {
		this.imagePane = imagePane;
	}

	
	

	public void loadImages() {
		BufferedImage image = FileUtils.openImage( null, null );
		if( image != null ) {			
			this.imageUtils = new ImageUtils( image );
			this.imagePane.setImageUtils(this.imageUtils);
		}
	}
	
	public void convertToMono( int type ) {
		MonoFilter mono = new MonoFilter();
		mono.setChannel( type )
			.apply( (WritableImage)this.imageUtils.getImage() );
		this.imagePane.update();
	}
	
	public void setChannels( int type ) {
		new RGBFilter()
			.setOptions( type )
			.apply( (WritableImage)this.imageUtils.getImage() );
		this.imagePane.update();
	}
	
	public void setLuminosite( int type, double value ) {
		new YIQFilter()
			.setOptions( type )
			.setValue( value )
			.apply( (WritableImage)this.imageUtils.getImage() );
		this.imagePane.update();
	}
	
	public void undo() {
		this.imagePane.undo();
	}
	
	public void redo() {
		this.imagePane.redo();
	}
	
	public void negative( boolean isRGB ) {
		NegativeFilter f = new NegativeFilter();
		if( isRGB ) {
			f.apply( (WritableImage)this.imageUtils.getImage() );	
		}
		else {
			f.apply( (WritableImage)this.imageUtils.getImage(), true );
		}
		this.imagePane.update();
	}
	
	
	/* Norm */
	public void applyNorm( boolean type ) {
		
		new Norm().apply( (WritableImage)this.imageUtils.getImage(), type );	
		this.imagePane.update();
	}
	
	/* Zoom */
	public void zoomChange( double zoom ) {
		this.imagePane.setCanvasZoom(zoom);
	}
	
	/* --- File Options --- */
	public void saveImage() {
		try {
			FileUtils.saveImage( ImageFXUtils.fromFxImage(imageUtils.getImage()) );
			
			System.out.println(
			imageUtils.getImage().getPixelReader().getPixelFormat().getType());
			
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
		
		
	}
	
	public void saveAsImage() {
		
	}
}
