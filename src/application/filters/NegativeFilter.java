package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class NegativeFilter {
	private boolean isRGB = true;
	
	public NegativeFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		Color c;
		int r, g, b;
		double y, i, q;
		
		YIQFilter yFilter = new YIQFilter();
		
		for( int n =0; n < image.getWidth(); n++ ) {
//			System.out.println();
			for( int j = 0; j < image.getHeight(); j++ ) {
				c = pr.getColor(n, j);
				
				if( isRGB ) {
					r = 255 - ((int)(c.getRed()*255));
					g = 255 - ((int)(c.getGreen()*255));
					b = 255 - ((int)(c.getBlue()*255));	
					
					
//					System.out.print("["+r+", "+g+", "+b+"]");
					pw.setColor( n, j, Color.rgb(r, g, b) );
				}
				else {
					y = 255 - yFilter.getY( c.getRed()*255, c.getGreen()*255, c.getBlue()*255 );
					i = yFilter.getI( c.getRed()*255, c.getGreen()*255, c.getBlue()*255 );
					q = yFilter.getQ( c.getRed()*255, c.getGreen()*255, c.getBlue()*255 );
					
					
//					System.out.print("["+y+", "+i+", "+q+"]");
//					System.out.println("R converted to " + yFilter.getR(y, i, q) );
					
					
					r = Math.abs((int)yFilter.getR(y, i, q));
					g = Math.abs((int)yFilter.getG(y, i, q));
					b = Math.abs((int)yFilter.getB(y, i, q));
					
					r = r > 255 ? 255 : r;
					g = g > 255 ? 255 : g;
					b = b > 255 ? 255 : b;
					
					pw.setColor( n, j, Color.rgb(r, g, b));
				}
				
			}
		}
		
		return this;
	}
	public NegativeFilter apply( WritableImage image, boolean useYIQ ) {
		this.isRGB = false;
		return this.apply(image);
	}
	
//	public NegativeFilter setOptions() {
//		
//	}
}
