package swt;
import java.nio.file.Path;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.ImageFileNameProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * On Windows 200% display scale throws IllegalArgumentException when calling
 *        GC.drawImage(Image image, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY, int destWidth, int destHeight)
 */
public class DrawImageIllegalArgumentException {
    
    public static void main(String[] args) throws Exception {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(800, 600);
        
        String imgPath = "images/eclipse.png";
        String fullPath = Path.of(DrawImageIllegalArgumentException.class.getResource(imgPath).toURI()).toString();
        
        // Use ImageDataProvider
        Image image1 = new Image(display, (ImageDataProvider)zoom -> {
            return new ImageData(DrawImageIllegalArgumentException.class.getResourceAsStream(imgPath));
        });
        
        addPaintListener(shell, image1);
        
        // Use ImageFileNameProvider
        Image image2 = new Image(display, (ImageFileNameProvider)zoom -> {
            return fullPath;
        });
        
        addPaintListener(shell, image2);
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    private static void addPaintListener(Shell shell, Image image) {
        int width = image.getBounds().width;
        int height = image.getBounds().height;
        
        shell.addPaintListener(event -> {
            GC gc = event.gc;
            gc.drawImage(image, 0, 0, width, height, 0, 0, width, height);
        });
    }
}
