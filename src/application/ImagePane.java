package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImagePane {
	private Canvas canvas;
	private GraphicsContext gc;
	private ImageUtils imageUtils;
	private StackRedoUndo stack;
	private boolean updateStack = true;
	
	/* Fixed value don't use to get canvas width or height */
	private double CANVAS_WIDTH = 400;
	private double CANVAS_HEIGHT = 300;
	
	public ImagePane( Canvas canvas ) {
		this.setCanvas(canvas);
		
		this.gc = canvas.getGraphicsContext2D();
		stack = new StackRedoUndo();
	}
	
	public ImagePane( WritableImage image ) {
		this( new Canvas() );
		this.setImageUtils( new ImageUtils( image ) );
	}
	
	public ImageUtils getImageUtils() {
		return this.imageUtils;
	}
	
	public ImagePane setImageUtils( ImageUtils utils ) {
		this.imageUtils = utils;
		
		double prop = CANVAS_WIDTH / imageUtils.width;
		CANVAS_HEIGHT = imageUtils.height * prop;		
		
		this.setCanvasZoom(1.0);
		return this;
	}
	public void update() {
		
		gc.setFill( Color.BLUE );
		gc.fillRoundRect(10, 10, canvas.getWidth(), canvas.getHeight(), 0, 0);
		
		if( imageUtils == null || imageUtils.width == 0 ) return;
		
		double h;
		h = imageUtils.height * canvas.getWidth()/imageUtils.width;
		
		WritableImage image = (WritableImage)imageUtils.getImage();
		gc.drawImage( image, 0.0, 0.0, canvas.getWidth(), h );
				
		if(updateStack) {
			stack.setNew( new WritableImage( image.getPixelReader(), 
					(int)image.getWidth(), (int)image.getHeight() ) );
		}
		updateStack = true;
	}
	public ImagePane setImage( Image image ) {
		gc.drawImage(image, 0.0, 0.0);
		return this;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public ImagePane setCanvasZoom( double zoom ) {
		this.canvas.setWidth( this.CANVAS_WIDTH * zoom );
		this.canvas.setHeight( this.CANVAS_HEIGHT * zoom );
		this.update();
		return this;
	}

	public boolean hasImage() {
		return this.imageUtils != null && imageUtils.getImage() != null;
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public void undo() {
		this.imageUtils.setImage( this.stack.undo() );
		updateStack = false;
		this.update();
	}
	public void redo() {
		this.imageUtils.setImage( this.stack.redo() );
		updateStack = false;
		this.update();
	}


}
