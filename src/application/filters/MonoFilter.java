package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MonoFilter {
	public static int R = 0;
	public static int G = 1;
	public static int B = 2;
	public static int M = 3;
	private int channel;
	
	public void apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		for( int i = 0; i < image.getWidth(); i++ ) {

			for( int j = 0; j < image.getHeight(); j++ ) {
				Color c = pr.getColor(i, j);
				double v = (channel == 0 ? c.getRed() : 
					channel == 1 ? c.getGreen() : 
					channel == 2 ? c.getBlue() : 
					(c.getRed()+ c.getGreen()+ c.getBlue() )/3); 
				
				if( channel == 4 ) {
					int l = this.getFourType(c);
					pw.setColor(i, j, Color.rgb(l, l, l));
				}
				pw.setColor(i, j, Color.color(v, v, v));
			}
		}
		
	}
	
	private int getFourType(Color c) {
		int	r = (int)(c.getRed()*   255),
			g = (int)(c.getGreen()* 255),
			b = (int)(c.getBlue()*  255);
		double v=0;
		
		if( r > g && r > b ) {
			double inc, diff;
			if( g > b ) {
				inc = b/2;
				diff = (r-g)/2;
				v = (g+inc) < 255 ? g+inc : 255;
			}
			else {
				inc = g/2;
				diff = (r-b)/2;
				v = (b+inc) < 255 ? b+inc : 255;
			}

			v = (v+diff) > 0 ? v+diff : 0;
		}
		
		
		else if( g > r && g > b ) {
			double inc, diff;
			if( r > b ) {
				inc = b/2;
				diff = (g-r)/2;
				v = (r+inc) < 255 ? r+inc : 255;
			}
			else {
				inc = r/2;
				diff = (g-b)/2;
				v = (b+inc) < 255 ? b+inc : 255;
			}

			v = (v+diff) > 0 ? v+diff : 0;
		}
		
		else {
			double inc, diff;
			if( r > g ) {
				inc = g/2;
				diff = (b-r)/2;
				v = (r+inc) < 255 ? r+inc : 255;
			}
			else {
				inc = r/2;
				diff = (b-g)/2;
				v = (g+inc) < 255 ? g+inc : 255;
			}

			v = (v+diff) > 0 ? v+diff : 0;
		}
		return ((int)v)%255;
	}
	
	public MonoFilter setChannel( int channel ) {
		this.channel = channel;
		return this;
	}
}
