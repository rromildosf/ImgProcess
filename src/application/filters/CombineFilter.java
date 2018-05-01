package application.filters;

import application.ImageUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CombineFilter {
	private int alignX;
	private int alignY;

	public WritableImage combine( WritableImage sumImage, WritableImage sumAndTarget, boolean maintainAspectRatio ) {
		
		if( sumImage == null || sumAndTarget == null ) return null;
		
		int width = (int)sumAndTarget.getWidth();
		int heigth = (int)sumAndTarget.getHeight();
		
		WritableImage formatedSum = ImageUtils.getScaledImage( sumImage, sumAndTarget, width, heigth, maintainAspectRatio, alignX, alignY );
				
		
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
	
}
