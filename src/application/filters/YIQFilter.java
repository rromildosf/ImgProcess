package application.filters;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class YIQFilter {
	public static final int MULT = 1; 
	public static final int ADD = 2; 
	
	private double value;
	private int type;
	
	public YIQFilter apply( WritableImage image ) {
		if( image == null ) return this;
		PixelWriter pw = image.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		int r, g, b;
		Color c;
		
		System.out.println("Type "+ type );
		System.out.println("Value "+ value );
		
		
		for( int n = 0; n < image.getWidth(); n++ ) {

			for( int j = 0; j < image.getHeight(); j++ ) {
				c = pr.getColor(n, j);
				r = (int)(c.getRed() * 255);
				g = (int)(c.getGreen() * 255);
				b = (int)(c.getBlue() * 255);
								
				double 	y = this.getY(r, g, b),
						i = this.getI(r, g, b),
						q = this.getQ(r, g, b);
				
				if( type == YIQFilter.ADD ) {
					y += value;
				}
				else y *= value;
				
				r = Math.abs((int)this.getR(y, i, q));
				g = Math.abs((int)this.getG(y, i, q));
				b = Math.abs((int)this.getB(y, i, q));
				
				r = r > 255 ? 255 : r; // correct 255 instead of [r,g,b]%255
				g = g > 255 ? 255 : g;
				b = b > 255 ? 255 : b;
								
				pw.setColor( n, j, Color.rgb( r, g, b ) );
			}
		}
		return this;
	}
	
	public YIQFilter setOptions( int type ) {
		this.type = type;
		return this;
	}
	
	public YIQFilter setValue( double value ) {
		this.value = value;
		System.out.println("new value " + value );
		return this;
	}
   
    
    /* YIQ Methods */
    
    public double getY( double r, double g, double b ){
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }
    
    public double getI( double r, double g, double b ){
    	return 0.596 * r - 0.274 * g - 0.322 * b;
    }
    
    public double getQ( double r, double g, double b ){
    	return 0.211 * r - 0.523 * g + 0.312 * b;
    }
    
    public double[] RGBToYIQ( int r, int g, int b ) {
    	double 	y = getY(r, g, b),
	    		i = getI(r, g, b),
	    		q = getQ(r, g, b);
    	return new double[] { y, i, q };
    }
    
    
    /* RGB Methods */
    
    
    public double getR( double y, double u, double v ){
        return y + 0.956 * u + 0.621 * v ;
    }
    
    public double getG( double y, double u, double v ){
        return y - 0.272 * u - 0.647 * v;
    }
    
    public double getB( double y, double u, double v ){
        return y - 1.106 * u + 1.703 * v ;
    }
    
    public int [] YUVToRGB( double y, double u, double v ) {
    	return new int[] {
    		(int)(Math.round( getR(y, u, v) )%255 ),
    		(int)(Math.round( getG(y, u, v) )%255 ),
    		(int)(Math.round( getB(y, u, v) )%255 )
    	};
    }
    
    


}
