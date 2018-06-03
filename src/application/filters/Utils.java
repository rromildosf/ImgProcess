package application.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Utils {

	public static Color[] getSubImage( int i, int j, int maskSize, Image img ){
		PixelReader pr = img.getPixelReader();
		
		int minus = (int)(maskSize/2.0);
		
		int m = i - minus; m = m < 0 ? 0 : m;
		int n = j - minus; n = n < 0 ? 0 : n;
		int w = i + minus;
		int h = j + minus;
		
		
		
		Color [] colors = new Color[ (w)*(h) ];
		int count = 0;
		System.out.println(w + " " + h);
		
		
		for( ; m <= w && m < img.getWidth(); m++ ) {	

			for( int k = n; k <= h && k < img.getHeight(); k++ ) {
				Color c = pr.getColor(m, k);
				colors[count++] = c;
			}
			
		}
		return colors;
	}
}
