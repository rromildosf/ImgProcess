package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class StackRedoUndo {
	private List<Image> images;
	private int pointer = -1;
	
	public StackRedoUndo() {
		this.images = new ArrayList<>();
	}
	
	public void setNew( Image image ) {
		
		if( pointer < images.size()-1 ) {
			for( int i = pointer+1; i < images.size(); i++ ) {
				images.remove(i);
			}
		}
		
		this.images.add(++pointer, image);
		
		if( this.images.size() > 30 ) {
			this.images.remove(0);
		}
	}
	
	public Image redo() {
		if( pointer != -1 ) {
			return images.get( pointer < images.size()-1 ? ++pointer : pointer );
		}
		return null;
	}
	
	public Image undo() {		
		return this.images.get( pointer == 0 ? 0 : --pointer );
	}	
}
