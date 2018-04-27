package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class BinaryFilter {
	private int threshold = 128;
	
	public BinaryFilter apply(WritableImage image) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		int r, g, b, m;
		Color c;
		for( int n = 0; n < image.getWidth(); n++ ) {

			for( int j = 0; j < image.getHeight(); j++ ) {
				
				c = pr.getColor(n, j);
				r = (int)(c.getRed() * 255);
				g = (int)(c.getGreen() * 255);
				b = (int)(c.getBlue() * 255);
				
				if ( r > g && r > b ) 		m = r;
				else if( g > b && g < r ) 	m = g;
				else 						m = b;
				
				if( m < threshold ) {
					m = 0;
				}
				else {
					m = 255;
				}
				
				pw.setColor(n, j, Color.rgb(m, m, m));
			}
		}
		
		return this;
	}
	
	public BinaryFilter setOptions( int threshold ) {
		this.threshold = threshold;
		return this;
	}
}
