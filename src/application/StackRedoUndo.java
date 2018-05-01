package application;

import java.util.Stack;

public class StackRedoUndo<T> {
	private Stack<T> redoStack;
	private Stack<T> undoStack;
	
	public StackRedoUndo() {
		redoStack = new Stack<>();
		undoStack = new Stack<>();
	}

	public void setNew( T item ) {
		undoStack.push(item);
		redoStack.clear();
	}
	
	public boolean canUndo() {
		System.out.println(undoStack.size());
		return !undoStack.empty();
	}

	public boolean canRedo() {
		System.out.println(redoStack.size());
		return !redoStack.empty();
	}
	
	public void clear() {
		this.redoStack.clear();
		this.undoStack.clear();
	}
	
	public T redo() {
		if( redoStack.isEmpty() ) return null;
		return undoStack.push( redoStack.pop() );
	}
	
	public T undo() {		
		if( undoStack.isEmpty() ) return null;
		return redoStack.push( undoStack.pop() );
	}
		
}
