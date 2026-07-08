package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ImagePrintFigureOperation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * On Windows throws IllegalArgumentException 
 * See https://github.com/eclipse-gef/gef-classic/issues/1146
 * 
 * @author Phillip Beauvoir
 */
public class DrawGC {

    public static void main(String[] args) {
        Shell shell = new Shell();
        Display display = shell.getDisplay();

        int width = 310;
        int height = 310;

        Figure figure = new Figure();
        figure.setSize(width, height);

        ImagePrintFigureOperation op = new ImagePrintFigureOperation(shell, figure);
        Image image = op.run();

        shell.addPaintListener(event -> {
            event.gc.drawImage(image, 0, 0, width, height, 0, 0, 100, 100);
        });

        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        image.dispose();
    }
}