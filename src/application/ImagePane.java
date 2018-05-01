package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ImagePane {
	private Canvas canvas;
	private GraphicsContext gc;
	private ImageUtils imageUtils;
	private StackRedoUndo<Image> stack;
	private boolean updateStack = true;
	
	/* Fixed value don't use to get canvas width or height */
	private double CANVAS_WIDTH = 400;
	private double CANVAS_HEIGHT = 300;
	
	public ImagePane( Canvas canvas ) {
		this.setCanvas(canvas);
		this.gc = canvas.getGraphicsContext2D();
		stack = new StackRedoUndo<>();
		
		
		canvas.addEventHandler( MouseEvent.MOUSE_CLICKED, e -> {
			if( e.getButton() == MouseButton.SECONDARY ) {
				MenuItem undo = new MenuItem("Undo");
				MenuItem redo = new MenuItem("Redo");
				MenuItem remove = new MenuItem("Remove Image");
				
				undo.setOnAction( u -> this.undo() );
				redo.setOnAction( u -> this.redo() );
				
				remove.setOnAction( r -> {
					r.consume();
					SGUtils.getInstance().removeImagePane(this);
				});
				
				ContextMenu ctm = new ContextMenu(undo, redo, remove);
				ctm.show( canvas.getScene().getWindow(), e.getSceneX(), e.getSceneY());
			}
		});
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
		this.stack.clear();
		
		double prop = CANVAS_WIDTH / imageUtils.width;
		CANVAS_HEIGHT = imageUtils.height * prop;		
		
		this.addImageOnStack( this.imageUtils.getImage() );
		this.setCanvasZoom(1.0);
		return this;
	}
	public void update() {
		
		if( imageUtils == null || imageUtils.width == 0 ) return;
		
		double h;
		h = imageUtils.height * canvas.getWidth()/imageUtils.width;
		
		WritableImage image = (WritableImage)imageUtils.getImage();
		gc.drawImage( image, 0.0, 0.0, canvas.getWidth(), h );
				
		addImageOnStack(image);
		updateStack = true;
	}
	
	private void addImageOnStack( Image image ) {
		System.out.println(image);
		if( updateStack && image != null ) {
			stack.setNew( new WritableImage( image.getPixelReader(), 
					(int)image.getWidth(), (int)image.getHeight() ) );
		}
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
		this.updateStack = false;
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
		if( stack.canUndo()  ) {
			this.imageUtils.setImage( this.stack.undo() );
			updateStack = false;
			this.update();
		}
	}
	public void redo() {
		if( stack.canRedo() ) {
			this.imageUtils.setImage( this.stack.redo() );
			updateStack = false;
			this.update();	
		}
	}


}
