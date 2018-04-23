package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Norm {
	
	public Norm apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		System.out.println("Applying");
		
		int r, g, b;
		Color c;
		for( int n = 0; n < image.getWidth(); n++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				c = pr.getColor(n, j);
				r = (int)(c.getRed() * 255);
				g = (int)(c.getGreen() * 255);
				b = (int)(c.getBlue() * 255);
				
//				System.out.println("["+r+" " +g + " " + b +"]");
				
				r = r-r%4;
				g = g-g%4;
				b = b-b%4;
				
//				System.out.println("["+r+" " +g + " " + b +"] \n");
				
				pw.setColor(n, j, Color.rgb(r, g, b));
				
			}
		}
		return this;
	}
	
	public void apply( WritableImage image, boolean t ) {
		
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		int r, g, b;
		Color c;
		double mR = 0, mG = 0, mB = 0;
		int i = 1;
		
		for( int n = 0; n < image.getWidth(); n++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
		
				c = pr.getColor(n, j);
				r = (int)(c.getRed() * 255);
				g = (int)(c.getGreen() * 255);
				b = (int)(c.getBlue() * 255);

				mR += ( r - mR ) / i; 
				mG += ( g - mG ) / i; 
				mB += ( b - mB ) / i;
				i++;
					
//				if( t ) {
//					r = (r + (int)mR ) > 255 ? 255 : r + (int)mR; 
//					g = (g + (int)mG ) > 255 ? 255 : g + (int)mG; 
//					b = (b + (int)mB ) > 255 ? 255 : b + (int)mB; 
//				}
//				else {
//					r = Math.abs( (int)mR - r ); 
//					g = Math.abs( (int)mG - g ); 
//					b = Math.abs( (int)mB - b );
//				}
//				pw.setColor(n, j, Color.rgb(
//					r, g, b
//				));
//				
			}
		}	
		for( int n = 0; n < image.getWidth(); n++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				
				c = pr.getColor(n, j);
				r = (int)(c.getRed() * 255);
				g = (int)(c.getGreen() * 255);
				b = (int)(c.getBlue() * 255);
				
				if( t ) {
					r = (r + (int)mR ) > 255 ? 255 : r + (int)mR; 
					g = (g + (int)mG ) > 255 ? 255 : g + (int)mG; 
					b = (b + (int)mB ) > 255 ? 255 : b + (int)mB; 
				}
				else {
					r = Math.abs( (int)mR - r ); 
					g = Math.abs( (int)mG - g ); 
					b = Math.abs( (int)mB - b );
				}
				pw.setColor(n, j, Color.rgb(
						r, g, b
						));
				
			}
		}	
	}
}
