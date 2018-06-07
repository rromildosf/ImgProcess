package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class BorderDetectFilter {
	private int [] typeOne = { 1, 1, 1, 0, 0, 0, -1, -1, -1};
	private int [] typeTwo = { 1, 0, -1, 1, 0, -1, 1, 0, -1};
	private int [] typeThree = { -1, -1, -1, -1, 8, -1, -1, -1, -1};
	private int type = 1;
	
	public BorderDetectFilter apply( WritableImage image, int type ) {
		this.type = type;
		PixelWriter pw = image.getPixelWriter();
		double w = image.getWidth(),
				h = image.getHeight();
		WritableImage aux = new WritableImage(image.getPixelReader(), (int)w, (int)h);
		PixelReader pr = aux.getPixelReader();
		
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getColor( i, j, w, h, pr ) );
				
			}
			
		}
		return this;
	}
	
	private Color getColor( int i, int j, double w, double h, PixelReader pr ) {
		Color []colors = Utils.getSubImage(i, j, 3, w, h, pr, true);
		int [] vect = null;
		int r = 0, g = 0, b = 0;
		for( int n = 0; n < colors.length; n++ ) {
			if( type == 1 ) {
				vect = this.typeOne;
			}
			else if( type == 2 ) {
				vect = this.typeTwo;
			}
			else {
				vect = this.typeThree;
			}
			r += (int)( vect[n] * colors[n].getRed()   *255 );
			g += (int)( vect[n] * colors[n].getGreen() *255 );
			b += (int)( vect[n] * colors[n].getBlue()  *255 );

		}
		r = r > 255 ? 255 : r;
		g = g > 255 ? 255 : g;
		b = b > 255 ? 255 : b;
		
		r = r < 0 ? 0 : r;
		g = g < 0 ? 0 : g;
		b = b < 0 ? 0 : b;
		
		
		return Color.rgb( r, g, b );
		
	}
}
