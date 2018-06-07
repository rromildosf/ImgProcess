package application.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MedianFilter {

	
private int maskSize;
	
	public MedianFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
				
		for( int i = 0; i < image.getWidth(); i++ ) {
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getMedian( i, j, image.getWidth(), image.getHeight(), pr ) );
				
			}
		}
		return this;
	}
	
	private Color getMedian( int i, int j, double w, double h, PixelReader pr ) {
		int mR = 0, mG = 0, mB = 0;
		
		List<Integer> mRa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mGa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mBa = new ArrayList<>( this.maskSize*maskSize );
		
		Color colors[] = Utils.getSubImage( i, j, maskSize, w, h, pr );
		
		for( int n = 0;  n < colors.length; n++ ) {
			mRa.add( (int)( colors[n].getRed()   * 255) );
			mGa.add( (int)( colors[n].getGreen() * 255) );
			mBa.add( (int)( colors[n].getBlue()  * 255) );
		}
		
		Collections.sort(mRa);
		Collections.sort(mGa);
		Collections.sort(mBa);
		
		int mid = (int)( (mRa.size() + 1) / 2.0 ) -1 ;
		if( mRa.size() % 2 == 0 ) {
			mR = (int)((mRa.get(mid) + mRa.get(mid+1)) / 2);
			mG = (int)((mGa.get(mid) + mGa.get(mid+1)) / 2);
			mB = (int)((mBa.get(mid) + mBa.get(mid+1)) / 2);
		}
		else {
			mR = mRa.get(mid);
			mG = mGa.get(mid);
			mB = mBa.get(mid);
		}
			
		return Color.rgb( mR, mG, mB );
	
	}
	
	
	public MedianFilter setOptions( int maskSize ) {
		this.maskSize = maskSize;
		return this;
	}
	
}
