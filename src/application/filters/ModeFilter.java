package application.filters;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ModeFilter {
	private int maskSize;

	public ModeFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		
		
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getModeColor(i, j, image) );
				
			}
			
		}
		return this;
	}
	private Color getModeColor( int i, int j, WritableImage img ) {
		PixelReader pr = img.getPixelReader();
		
		int m, n;
		int minus = (int)(maskSize/2.0);
		
		m = i - minus; m = m < 0 ? 0 : m;
		n = j - minus; n = n < 0 ? 0 : n;
		
		List<Integer> mRa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mGa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mBa = new ArrayList<>( this.maskSize*maskSize );
		
		Color c;
		
		for( ; m <= i + minus && m < img.getWidth(); m++ ) {	

			for( int k = n; k <= j + minus && k < img.getHeight(); k++ ) {

				c = pr.getColor(m, k);
				
				mRa.add( (int)(c.getRed()  *255) );
				mGa.add( (int)(c.getGreen()*255) );
				mBa.add( (int)(c.getBlue() *255) );
				
				
			}
			
		}
		
		int modeR = getMode( mRa );
		int modeG = getMode( mGa );
		int modeB = getMode( mBa );
		
		
		return Color.rgb( modeR, modeG, modeB );
	
	}
	
	private int getMode( List<Integer> m ) {
		int maxOccurs = 0;
		
		int currentValue = 0, aux,
		     countForAux = 0;
		
		for( int i = 0; i < m.size(); i++ ) {
			aux = m.get(i);
			countForAux = 1;
			if( currentValue == aux ) continue;
			
			for( int j = i+1; j < m.size(); j++ ) {
				if( aux == m.get(j) ) {
					countForAux++;
				}
			}
			if( countForAux > maxOccurs ) {
				maxOccurs = countForAux;
				currentValue = aux;
			}
		}
		return currentValue;
	}
	public ModeFilter setOptions( int windowSize ) {
		this.maskSize = windowSize;
		return this;
	}
	
}
