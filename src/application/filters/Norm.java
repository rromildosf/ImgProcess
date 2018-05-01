package application.filters;

import application.ImageUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Norm {
	
	public WritableImage transform ( WritableImage img ) {
		System.out.println("transforming");
		WritableImage image = ImageUtils.getScaledImage(img, null, img.getWidth()*5, img.getHeight()*5, true, 0, 0);
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		int r, g, b, m;
		Color c;
		int t = 22;
		
		int ar = 0, ag = 0 , ab = 0;
		int count = 0;
		for( int n = 0; n < image.getWidth(); n++ ) {

			for( int j = 0; j < image.getHeight(); j++ ) {
				
				c = pr.getColor(n, j);
				
				r = (int)( c.getRed() * 255 );
				g = (int)( c.getGreen() * 255 );
				b = (int)( c.getBlue() * 255 );
				
				Color a = null;
				int q = 0, w = 0, e = 0;
				if( n > 0 ) {
					a = pr.getColor( n - 1, j );
					
					q = (int)(a.getRed()*255);
					w = (int)(a.getRed()*255);
					e = (int)(a.getRed()*255);
				}
				
				if(  ar + t >= r && ar - t < r ) {
					if( a != null ) {
						if(  ar + t >= r && ar - t < r && q + t >= r && q - t < r  ) {
							r = (q+ar)/2;
						}
						else ar = r;
					}
				
				}
				if(  ar + t >= r && ar - t < r ) {
					if( a != null  ) {
						if(  ag + t >= g && ag - t < g && w + t >= g && w - t < g  ) {
							g = (w+ag)/2;
						} else ag = g;
					}
					
				}
				if(  ar + t >= r && ar - t < r ) {
					if( a != null ) {
						if(  ab + t >= b && ab - t < b && e + t >= b && e - t < b  ) { 
							b = (e+ab)/2;
						} else ab = b;
					}
					
				}
				
				pw.setColor(n, j, Color.rgb(r, g, b));
			
			}
		}		
		return image;
	}
	
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
