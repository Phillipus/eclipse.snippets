package swt;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Image is flipped on Sonoma using Control#setBackgroundImage
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/772
 */
public class MacBackgroundImageFlipped {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Image Test");
        shell.setLayout(new GridLayout());
        
        shell.setSize(200, 200);
        
        Image image = new Image(Display.getDefault(), 200, 200);
        GC gc = new GC(image);
        gc.drawText("Hello World", 50, 100);
        gc.dispose();
        
        shell.setBackgroundImage(image);

        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
        image.dispose();
    }
}