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
		double w = image.getWidth(),
				h = image.getHeight();
		WritableImage aux = new WritableImage(image.getPixelReader(), (int)w, (int)h);
		PixelReader pr = aux.getPixelReader();
		
//		Utils.printImage(image, 1);
		
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getModeColor( i, j, w, h, pr ) );
				
			}
			
		}
		return this;
	}
	private Color getModeColor( int i, int j, double w, double h, PixelReader pr ) {
		List<Integer> mRa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mGa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mBa = new ArrayList<>( this.maskSize*maskSize );
		
		Color colors [] = Utils.getSubImage(i, j, maskSize, w, h, pr );
		for( int n = 0; n < colors.length; n++ ) {
			mRa.add( (int)( colors[n].getRed()  *255) );
			mGa.add( (int)( colors[n].getGreen()*255) );
			mBa.add( (int)( colors[n].getBlue() *255) );
		}
		
		int modeR = getMode( mRa );
		int modeG = getMode( mGa );
		int modeB = getMode( mBa );
//		
//		System.out.println(modeR);
//		System.out.println(mRa+"\n");
		
		
		
		
		return Color.rgb( modeR, modeG, modeB );
	
	}
	
	private int getMode( List<Integer> m ) {
		int occurrencesMax = 0,
			maxRepeated = -1, 
		    occurrencesAux = 0,
			aux;
		
		for( int i = 0; i < m.size(); i++ ) {
			aux = m.get(i);
			occurrencesAux = 1;
			if( maxRepeated == aux ) continue; //has already been counted.
			
			for( int j = i+1; j < m.size(); j++ ) {
				if( aux == m.get(j) ) {
					occurrencesAux++;
				}
			}
			if( occurrencesAux >= occurrencesMax ) {
				occurrencesMax = occurrencesAux;
				maxRepeated = aux;
				if( occurrencesMax >= m.size()/2 )break;
			}
		}
		
		return maxRepeated;
	}
	public ModeFilter setOptions( int windowSize ) {
		this.maskSize = windowSize;
		return this;
	}
	
}
