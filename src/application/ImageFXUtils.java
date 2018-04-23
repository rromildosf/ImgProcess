package application;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.scene.image.PixelFormat.Type;

import com.sun.prism.PixelFormat;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class ImageFXUtils {
	public static BufferedImage fromFxImage( Image image ) {
		int type = BufferedImage.TYPE_INT_RGB;
				
				
		BufferedImage buff = new BufferedImage( (int)image.getWidth(), (int)image.getHeight(), type );
		PixelReader pr = image.getPixelReader();
		Color c = null;
		for( int i = 0; i < image.getWidth(); i++ ) {
			for( int j = 0; j < image.getHeight(); j++ ) {
				int r = (int)(pr.getColor(i, j).getRed() * 255);
				int g = (int)(pr.getColor(i, j).getGreen() * 255);
				int b = (int)(pr.getColor(i, j).getBlue() * 255);
				
				r = r > 255 ? 255 : r;
				g = g > 255 ? 255 : g;
				b = b > 255 ? 255 : b;
				
				
				c = new Color( r, g, b );
				buff.setRGB( i, j, c.getRGB() );
			}
		}
		return buff;
	}
}
