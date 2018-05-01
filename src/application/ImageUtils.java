package application;

import java.awt.image.BufferedImage;

import application.filters.CombineFilter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class ImageUtils {
	
	public static final int H_ALIGN_CENTER = 1; // ok
	public static final int H_ALIGN_LEFT = 2;   // ok
	public static final int H_ALIGN_RIGHT = 4;  // ok

	public static final int V_ALIGN_TOP = 7;     // ok
	public static final int V_ALIGN_CENTER = 8;
	public static final int V_ALIGN_BOTTOM = 9; // ok
	
	
	private WritableImage image;
	public int height;
	public int width;
	
	public ImageUtils( BufferedImage img ) {
		this( SwingFXUtils.toFXImage( img, null ) );
		
	}
	public ImageUtils( WritableImage img ) {
		this.height = (int)img.getHeight();
		this.width = (int)img.getWidth();
		this.image = new WritableImage( img.getPixelReader(), width, height );
		
//		this.loadPixels( img );
	}
//	protected void loadPixels( ) {
//		Color c = null;
//		
//		for( int i = 0; i < width; i++ ) {
//			for( int j = 0; j < height; j++ ) {
//				c = new Color( image.getPixelReader().getArgb(i, j) );
//				this.editImage[i][j] = new int[] {c.getRed(),c.getRed(), c.getBlue()};				
//			}
//		}
//	}
	
	public void removeImage() {
		this.image = null;
	}
	
	public Image getImage() {
		return this.image;
	}
	public void setImage( Image image ) {
		this.image = (WritableImage)image;
	}
	
	public static WritableImage getScaledImage( Image img, Image target, 
			double w, double h, boolean preserveAspectRatio,
			int alignX,
			int alignY
		) {
		double diffX = w - img.getWidth();
		double diffY = h - img.getHeight();
		
		double scW = w; // scaled image width
		double scH = h; // scaled image height
		
		if( preserveAspectRatio ) {
			if( diffX > diffY ) {
				scW = img.getWidth() + diffX;
				scH = img.getHeight() + diffX;
	 		} else {
	 			scW = img.getWidth() + diffY;
				scH = img.getHeight() + diffY;
	 		}			
		}
		
		double topGap = 0;
		double leftGap = 0;
		switch ( alignX ) {
		case ImageUtils.H_ALIGN_CENTER: 
			leftGap = ( w - scW )/2;
			break;
		
		case ImageUtils.H_ALIGN_LEFT: 
			break;
		
		case ImageUtils.H_ALIGN_RIGHT: 
			leftGap = ( w - scW ); // to result in negative number
			break;	
		default:	
			break;
		}
		
		switch (alignY) {

		case ImageUtils.V_ALIGN_TOP: 
			break;
		
		case ImageUtils.V_ALIGN_CENTER: 
			topGap = ( h - scH )/2;
			break;
		
		case ImageUtils.V_ALIGN_BOTTOM: 
			topGap = ( h - scH );  // to result in negative number
			break;
		
		default:
			break;
		}
		
		GraphicsContext gc  = new Canvas(w, h).getGraphicsContext2D();
		if( target != null )
			gc.drawImage(target, 0, 0);// used only for draw target image on down
		gc.drawImage(img, leftGap, topGap, scW, scH );
		
		System.out.println("LeftGap "+ leftGap + " topGap " + topGap );
		return gc.getCanvas().snapshot(null, null);
		
	}
}
