package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MeanFilter {
	private int maskSize;
	
	public MeanFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		
		long t = System.currentTimeMillis();
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getAverage(i, j, image.getWidth(), image.getHeight(), image.getPixelReader() ) );
				
			}
			
		}
		System.out.println( System.currentTimeMillis() - t);
		return this;
	}
	
	
	private Color getAverage(int i, int j, double width, double height, PixelReader pr ) {
		
		int m, n;
		int minus = (int)(maskSize/2.0);
		
		m = i - minus; m = m < 0 ? 0 : m;
		n = j - minus; n = n < 0 ? 0 : n;
		
		
		Color c;
		double 	sumR = 0,
				sumG = 0,
				sumB = 0;
		int sumP = 0;
		
		
		Color [] colors = Utils.getSubImage(i, j, maskSize, width, height, pr );
		for( int k = 0; k < colors.length; k++ ) {
			c = colors[k];
			sumR += c.getRed();
			sumG += c.getGreen();
			sumB += c.getBlue();
			sumP++;
		}
		
		return new Color( sumR/sumP, sumG/sumP, sumB/sumP, 1);
	
	}
	
	
	public MeanFilter setOptions( int maskSize ) {
		this.maskSize = maskSize;
		return this;
	}
}
