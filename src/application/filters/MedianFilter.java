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
		
//		for( int i = 0; i < image.getWidth(); i++ ) {
//			System.out.println();
//			for( int j = 0; j < image.getHeight(); j++ ) {
//				System.out.print( " " + ((int)(pr.getColor(i, j).getRed()*255)) + " " );
//			}
//		}
				
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				
				pw.setColor(i, j, getMedian(i, j, image) );
				
			}
			
		}
		return this;
	}
	
	private Color getMedian( int i, int j, WritableImage img ) {
		PixelReader pr = img.getPixelReader();
		
		double r, g, b;
		

		int minus = (int)(maskSize/2.0);
		int m, n;
		
		m = i - minus; m = m < 0 ? 0 : m;
		n = j - minus; n = n < 0 ? 0 : n;
		
		
		
		Color c;
		int 	mR = 0,
				mG = 0,
				mB = 0;
		int sumP = 0;
		
		List<Integer> mRa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mGa = new ArrayList<>( this.maskSize*maskSize );
		List<Integer> mBa = new ArrayList<>( this.maskSize*maskSize );
		
		
//		System.out.println();
		for( ; m <= i + minus && m < img.getWidth(); m++ ) {	

//			System.out.println();
//			System.out.println("[");
//			System.out.println("i " + i +"  j " + j );
			for( int k = n; k <= j + minus && k < img.getHeight(); k++ ) {
				
				c = pr.getColor(m, k);
				
				mRa.add( (int)(c.getRed()   * 255) );
				mGa.add( (int)(c.getGreen() * 255) );
				mBa.add( (int)(c.getBlue()  * 255) );
				
//				System.out.print( " " + (int)(c.getRed()*255) + " " );
//				
			}
			
		}
		
		Collections.sort(mRa);
		Collections.sort(mGa);
		Collections.sort(mBa);
		
//		System.out.println("R " + mRa);
//		System.out.println("G " + mGa);
//		System.out.println("B " + mBa);
		
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
//		System.out.println("MR " + mR + " MG " + mG + " MB "+ mB + "\n");
//		System.out.println( "\nMedian " + mR );
			
		return Color.rgb( mR, mG, mB );
	
	}
	
	
	public MedianFilter setOptions( int maskSize ) {
		this.maskSize = maskSize;
		return this;
	}
	
}
