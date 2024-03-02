package swt;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * Rectangle should not be clipped
 * 
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1067
 * See https://github.com/eclipse-platform/eclipse.platform.swt/pull/1073
 * 
 */
public class GCRegressionTest {

    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("GC Regression Test");
        shell.setLayout(new FillLayout());

        // Create an image of a white background and black rectangle
        // Rectangle should not be clipped on right and bottom sides
        Image image = new Image(display, new ImageDataProvider() {
            @Override
            public ImageData getImageData(int zoom) {
                Image image = new Image(display, 60, 60);
                GC gc = new GC(image);

                gc.fillRectangle(image.getBounds());
                gc.setLineWidth(2);
                gc.drawRectangle(1, 1, 58, 58);
                gc.dispose();

                ImageData data = image.getImageData(zoom);
                image.dispose();

                return data;
            }
        });

        shell.addPaintListener(e -> {
            e.gc.drawImage(image, 10, 10);
        });

        shell.setSize(120, 120);
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}