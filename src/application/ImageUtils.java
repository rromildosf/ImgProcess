package application;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class ImageUtils {
//	private WritableImage originalImage;
	private WritableImage image;
	private int [][][] editImage;
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
	protected void loadPixels( ) {
		Color c = null;
		
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				c = new Color( image.getPixelReader().getArgb(i, j) );
				this.editImage[i][j] = new int[] {c.getRed(),c.getRed(), c.getBlue()};				
			}
		}
	}
	
	public Image getImage() {
		return this.image;
	}
	public void setImage( Image image ) {
		this.image = (WritableImage)image;
	}
}
