package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class RGBFilter {
	public static int R = 2;
	public static int G = 3;
	public static int B = 4;
	public static int RG = 5;
	public static int RB = 6;
	public static int GB = 7;
	public static int RGB = 9;
	
	private int type = 6;
	
	public void apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		for( int i =0; i < image.getWidth(); i++ ) {
			for( int j = 0; j < image.getHeight(); j++ ) {
				pw.setColor(i, j, this.getColor( pr.getColor(i, j) ) );
			}
		}
	}
	private Color getColor( Color c ) {
		if( type == RGBFilter.R ) return Color.color( c.getRed(), 0, 0 );
		if( type == RGBFilter.G ) return Color.color( 0, c.getGreen(), 0 );
		if( type == RGBFilter.B ) return Color.color( 0, 0, c.getBlue() );
		if( type == RGBFilter.RG ) return Color.color( c.getRed(), c.getGreen(), 0 );
		if( type == RGBFilter.RB ) return Color.color( c.getRed(), 0, c.getBlue() );
		if( type == RGBFilter.GB ) return Color.color( 0, c.getGreen(), c.getBlue()  );
		
		return c;
	}
	public RGBFilter setOptions( int options ) {
		this.type = options;
		return this;
	}
}
