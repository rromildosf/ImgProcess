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
	
	public static void saveImage( BufferedImage image, File path ) {
		String format = path.getPath().substring( path.getPath().lastIndexOf(".")+1 );
//		String image  = path.getPath().substring( path.getPath().lastIndexOf("/")+1 );
		
		try { 
			File outputFile = new File(  path +"." + format );
            ImageIO.write( image, format, outputFile);
		}
		catch ( IOException e ) {
			e.printStackTrace();
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
