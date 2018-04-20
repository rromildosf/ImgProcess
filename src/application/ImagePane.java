package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImagePane {
	private Canvas canvas;
	private GraphicsContext gc;
	private ImageUtils utils;
	private StackRedoUndo stack;
	private boolean updateStack = true;
	
	public ImagePane( Canvas canvas ) {
		this.setCanvas(canvas);
		
		this.gc = canvas.getGraphicsContext2D();
		gc.setFill( Color.BLUE );
		gc.fillRoundRect(10, 10, canvas.getWidth(), canvas.getHeight(), 0, 0);
		stack = new StackRedoUndo();
			
	}
	public void setImageUtils( ImageUtils utils ) {
		this.utils = utils;
		this.update();
	}
	public void update() {
		
		double h;
		h = utils.height * canvas.getWidth()/utils.width;
		
		WritableImage image = (WritableImage)utils.getImage();
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

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public void undo() {
		this.utils.setImage( this.stack.undo() );
		updateStack = false;
		this.update();
	}
	public void redo() {
		this.utils.setImage( this.stack.redo() );
		updateStack = false;
		this.update();
	}


}
