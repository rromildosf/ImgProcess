package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Utils {

	public static Color[] getSubImage( int i, int j, int maskSize, double width, double height, PixelReader pr ){
		int minus = (int)(maskSize/2.0),
			m = i - minus,
			n = j - minus,
			w = i + minus, 
			h = j + minus;
		
		m = m < 0 ? 0 : m;
		n = n < 0 ? 0 : n;
		w = w >= (int)width  ? (int)width -1 : w;
		h = h >= (int)height ? (int)height-1 : h;
		
		
		Color [] colors = new Color[ (w-m+1) * (h-n+1) ];
		int count = 0;
		
		
		for( ; m <= w; m++ )
			for( int k = n; k <= h; k++ )
				colors[count++] = pr.getColor(m, k);
		return colors;
	}
	
	public static Color[] getSubImage( int i, int j, int maskSize, double width, double height, PixelReader pr, boolean r ){
		int minus = (int)(maskSize/2.0),
			m = i - minus,
			n = j - minus,
			w = m + maskSize -1, 
			h = n + maskSize -1;
		
		
		Color [] colors = new Color[ maskSize * maskSize ];
		int count = 0;
//		System.out.println("Colors len " + colors.length );
		for( ; m <= w; m++ )
			for( int k = n; k <= h; k++ ) {
//				System.out.println("count "+ count + " m " + m +" k "+ k);
				if( m < 0 || k < 0 || m >= width || k >= height ) {
					colors[count++] = new Color( 0, 0, 0, 1);
				}
				else colors[count++] = pr.getColor(m, k);
			}
		return colors;
	}
	
	
	public static void print( Color[] cls ) {
		for( int i = 0; i < cls.length; i++ ) {
			System.out.print(" " + (int)(cls[i].getRed()*255)  );
		}
		System.out.println();
	}
	public static void printImage( WritableImage img, int channel  ) {
		for( int i = 0; i < img.getWidth(); i++ ) {
			System.out.println();
			for( int k = 0; k < img.getHeight(); k++ )
				System.out.printf( " %3d ", (int)( img.getPixelReader().getColor(i, k).getRed() *255 ) );
		}
		System.out.println();
	}
}
