package draw2d;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Draw a rectangle figure
 * The line on the right and bottom should not be clipped
 * 
 * See https://github.com/eclipse-platform/eclipse.platform.swt/pull/1073
 */
public class GCRegressionTestDraw2d {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(200, 200);

        FigureCanvas fc = new FigureCanvas(shell);
        RectangleFigure root = new RectangleFigure();
        root.setBackgroundColor(new Color(null, 255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(root);
        
        RectangleFigure rectFigure = new RectangleFigure();
        rectFigure.setBounds(new Rectangle(10, 10, 100, 100));
        rectFigure.setLineWidth(2);
        root.add(rectFigure);

        shell.open();
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}