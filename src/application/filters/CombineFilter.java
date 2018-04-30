package application.filters;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CombineFilter {
	private int alignX;
	private int alignY;
	public static final int H_ALIGN_CENTER = 1; // ok
	public static final int H_ALIGN_LEFT = 2;   // ok
	public static final int H_ALIGN_RIGHT = 4;  // ok

	public static final int V_ALIGN_TOP = 7;     // ok
	public static final int V_ALIGN_CENTER = 8;
	public static final int V_ALIGN_BOTTOM = 9; // ok

	public WritableImage combine( WritableImage sumImage, WritableImage sumAndTarget, boolean maintainAspectRatio ) {
		
		if( sumImage == null || sumAndTarget == null ) return null;
		
		int width = (int)sumAndTarget.getWidth();
		int heigth = (int)sumAndTarget.getHeight();
		
		WritableImage formatedSum = getScaledImage( sumImage, sumAndTarget, width, heigth, maintainAspectRatio );
				
		
		System.out.println("combining");

		short r, g, b;
		Color c1, c2;
		
		for( int n = 0; n < width; n++ ) {
//				System.out.println();
			for( int j = 0; j < heigth; j++ ) {
				
				c1 = formatedSum.getPixelReader().getColor(n, j);
				c2 = sumAndTarget.getPixelReader().getColor(n, j);
			
				
				
				r = (short)( ( c1.getRed()*255   + c2.getRed()*255 )   / 2 );
				g = (short)( ( c1.getGreen()*255 + c2.getGreen()*255 ) / 2 );
				b = (short)( ( c1.getBlue()*255  + c2.getBlue()*255 )  / 2 );
				
				sumAndTarget.getPixelWriter().setColor(n, j, Color.rgb(r, g, b));
				
//				System.out.println(c1.getRed()   + c2.getRed());
			}
		}
		
		return sumAndTarget;
	}
	private WritableImage getScaledImage( Image img, Image target, double w, double h, boolean preserveAspectRatio ) {
		double diffX = w - img.getWidth();
		double diffY = h - img.getHeight();
		
		double scW = w; // scaled image width
		double scH = h; // scaled image height
		
		if( preserveAspectRatio ) {
			if( diffX > diffY ) {
				scW = img.getWidth() + diffX;
				scH = img.getHeight() + diffX;
	 		} else {
	 			scW = img.getWidth() + diffY;
				scH = img.getHeight() + diffY;
	 		}			
		}

		alignX = 1;
		alignY = 8;
		
		double topGap = 0;
		double leftGap = 0;
		switch ( alignX ) {
		case CombineFilter.H_ALIGN_CENTER: 
			leftGap = ( w - scW )/2;
			break;
		
		case CombineFilter.H_ALIGN_LEFT: 
			break;
		
		case CombineFilter.H_ALIGN_RIGHT: 
			leftGap = ( w - scW ); // to result in negative number
			break;	
		default:	
			break;
		}
		
		switch (alignY) {

		case CombineFilter.V_ALIGN_TOP: 
			break;
		
		case CombineFilter.V_ALIGN_CENTER: 
			topGap = ( h - scH )/2;
			break;
		
		case CombineFilter.V_ALIGN_BOTTOM: 
			topGap = ( h - scH );  // to result in negative number
			break;
		
		default:
			break;
		}
		
		GraphicsContext gc  = new Canvas(w, h).getGraphicsContext2D();
		gc.drawImage(target, 0, 0);
		gc.drawImage(img, leftGap, topGap, scW, scH );
		
		System.out.println("LeftGap "+ leftGap + " topGap " + topGap );
		return gc.getCanvas().snapshot(null, null);
		
	}
}
