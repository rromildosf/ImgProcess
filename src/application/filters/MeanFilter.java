package application.filters;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MeanFilter {
	private int maskSize;
	
	public MeanFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		
		
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getAverage(i, j, image) );
				
			}
			
		}
		return this;
	}
	
	
	private Color getAverage( int i, int j, WritableImage img ) {
		PixelReader pr = img.getPixelReader();
		
		
		int m, n;
		int minus = (int)(maskSize/2.0);
		
		m = i - minus; m = m < 0 ? 0 : m;
		n = j - minus; n = n < 0 ? 0 : n;
		
		
		Color c;
		double 	sumR = 0,
				sumG = 0,
				sumB = 0;
		int sumP = 0;
		
		
		Color [] colors = Utils.getSubImage(i, j, maskSize, img);
		for( int k = 0; k < colors.length; k++ ) {
			c = colors[k];
			System.out.println(k);
			sumR += c.getRed();
			sumG += c.getGreen();
			sumB += c.getBlue();
			sumP++;
		}
		
//		for( ; m <= i + minus && m < img.getWidth(); m++ ) {	
//
//			for( int k = n; k <= j + minus && k < img.getHeight(); k++ ) {
//
//				c = pr.getColor(m, k);
//				
//				sumR += c.getRed();
//				sumG += c.getGreen();
//				sumB += c.getBlue();
//				sumP++;
//				
//				
//			}
//			
//		}
		
		return new Color( sumR/sumP, sumG/sumP, sumB/sumP, 1);
	
	}
	
	
	public MeanFilter setOptions( int maskSize ) {
		this.maskSize = maskSize;
		return this;
	}
}
