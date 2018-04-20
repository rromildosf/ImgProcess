package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class StackRedoUndo {
	private List<Image> images;
	private int currentIndex = -1;
	
	public StackRedoUndo() {
		this.images = new ArrayList<>();
	}
	
	public void setNew( Image image ) {
		this.images.add(image);
//		System.out.println("setting new");
		
		if( this.images.size() > 30 ) {
			this.images.remove(0);
		}
		this.currentIndex = this.images.size()-1;
	}
	
	public Image redo() {
		if( currentIndex == this.images.size() - 1 ) {
			return this.images.get( currentIndex );
		}
		return images.get( ++currentIndex );
	}
	
	public Image undo() {		
		if( currentIndex == 0 ){
//			System.out.println("Index "+ currentIndex + " size: "+ images.get(0) );
			return this.images.get(0);
		}
//		System.out.println("Index "+ currentIndex + " size: "+ images.get(currentIndex-1) );
		return this.images.get(--currentIndex);
	}	
}
