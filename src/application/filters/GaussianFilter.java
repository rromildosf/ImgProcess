package application.filters;



import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GaussianFilter {
	private float[] gsFilter = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f, 0.0625f, 0.125f, 0.0625f };
	
	public GaussianFilter apply( WritableImage image ) {
		PixelWriter pw = image.getPixelWriter();
		double w = image.getWidth(),
				h = image.getHeight();
		WritableImage aux = new WritableImage(image.getPixelReader(), (int)w, (int)h);
		PixelReader pr = aux.getPixelReader();
		
//		Utils.printImage(image, 1);
		
		for( int i = 0; i < image.getWidth(); i++ ) {
			
			for( int j = 0; j < image.getHeight(); j++ ) {
				
				pw.setColor(i, j, getColor( i, j, w, h, pr ) );
				
			}
			
		}
		return this;
	}
	
	private Color getColor( int i, int j, double w, double h, PixelReader pr ) {
		Color []colors = Utils.getSubImage(i, j, 3, w, h, pr);

		double sumR = 0, sumG = 0, sumB = 0;
		for( int n = 0; n < colors.length; n++ ) {
			sumR += gsFilter[n] * colors[n].getRed();
			sumG += gsFilter[n] * colors[n].getGreen();
			sumB += gsFilter[n] * colors[n].getBlue();
		}
		
		return new Color( sumR, sumG, sumB, 1 );
		
	}
}
