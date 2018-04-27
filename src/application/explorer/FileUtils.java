package application.explorer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileUtils {
	
	public static BufferedImage openImage( Stage stage, CustomEventListener handler ) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a picture");
		fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
		
		try {
			fileChooser.setTitle("Open an image");
			File file = fileChooser.showOpenDialog(stage);
	        if (file != null) {
					return FileUtils.openImage( file );
	        }
	        return null;
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
	}
	public static BufferedImage openImage( File path ) {
		try { 
			return ImageIO.read( path );
		}
		catch ( IOException e ) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	public static File saveImage( BufferedImage image ) throws IOException {
		File f = FileUtils.showSaveDialog(null);
		if( f  != null ) {
			FileUtils.saveImage( image, f );
		}
		return f;
	}
	
	public static void saveImage( BufferedImage image, File path ) throws IOException {
		
		try { 
            ImageIO.write( image, 
            	path.getName().substring( path.getName().lastIndexOf('.')+1 ), 
            	path );
		}
		catch ( IOException e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static File showSaveDialog( Stage stage ) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a picture");
		fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
		
		try {
			
			fileChooser.setTitle("Save the image");
			File file = fileChooser.showSaveDialog(stage);
			
	        if (file != null) {
	        	String ext = fileChooser.getSelectedExtensionFilter().getDescription();			
				String format = file.getName().substring( file.getName().lastIndexOf(".")+1 );
				
				if( format.toLowerCase().equals("jpg") || 
					format.toLowerCase().equals("jpeg") || 
					format.toLowerCase().equals("png") ) {
					return file;
				}
				else {
					return new File( file.getAbsolutePath() +"."+ext.toLowerCase());
				}				
	        }
	        return null;
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract class  CustomEventListener implements EventListener{
		
		public abstract void onEvent( CustomEvent<BufferedImage> ev );
		
	}
	public class CustomEvent<T> {
		private T data;
		public T getData() {
			return data;
		}
		public void fireEvent() {
			System.out.println("algo");
		}
		
	}
} 
