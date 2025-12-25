package draw2d;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * On Windows 200% display scale throws IllegalArgumentException when calling
 *        GC.drawImage(Image image, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY, int destWidth, int destHeight)
 */
public class FileImageDataProviderIllegalArgumentException {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setSize(800, 600);

        Image image = FileImageDataProvider.createImage(FileImageDataProviderIllegalArgumentException.class, "gef.svg");
        int width = image.getBounds().width;
        int height = image.getBounds().height;
        
        shell.addPaintListener(event -> {
            GC gc = event.gc;
            gc.drawImage(image, 0, 0, width, height, 0, 0, width, height);
        });

        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}
