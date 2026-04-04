package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageGcDrawer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Linux image wrong at 200% scaling
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/3142
 */
public class LinuxButtonImageDrawer {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("Linux Button Image");
        shell.setLayout(new GridLayout());
        shell.setSize(450, 250);
        
        Button button = new Button(shell, SWT.FLAT);
        
        ImageGcDrawer gcDrawer = (gc, width, height) -> {
            gc.setBackground(new Color(255, 200, 255));
            gc.fillRectangle(0, 0, width - 1, height - 1);
            gc.drawRectangle(0, 0, width - 1, height - 1);
        };
        
        Image image = new Image(display, gcDrawer, 40, 15);
        button.setImage(image);
        
        // Setting this to false causes the problem
        button.setEnabled(false);
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
