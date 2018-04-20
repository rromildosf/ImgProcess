package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class NegativeFilter {

	public NegativeFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		Color c;
		int r, g, b;
		for( int i =0; i < image.getWidth(); i++ ) {
//			System.out.println();
			for( int j = 0; j < image.getHeight(); j++ ) {
				c = pr.getColor(i, j);
				
				r = 255 - ((int)(c.getRed()*255))%255;
				g = 255 - ((int)(c.getGreen()*255))%255;
				b = 255 - ((int)(c.getBlue()*255))%255;
				
//				System.out.print("[R,G,B] = ["+ r + ", "+ g + ", "+b +"]  \n");
				
				pw.setColor( i, j, Color.rgb(r, g, b) );
			}
		}
		
		return this;
	}
	
//	public NegativeFilter setOptions() {
//		
//	}
}
