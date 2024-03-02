package draw2d;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Draw a figure with dashes
 * Dashes should be same on all platforms at hi-dpi 200% scaling
 * 
 * See https://github.com/eclipse/gef-classic/issues/206
 */
public class LineDashesDraw2d {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(300, 300);

        FigureCanvas fc = new FigureCanvas(shell);
        RectangleFigure root = new RectangleFigure();
        root.setBackgroundColor(new Color(null, 255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(root);
        
        RectangleFigure rectFigure = new RectangleFigure();
        rectFigure.setBounds(new Rectangle(10, 10, 250, 150));
        rectFigure.setLineWidth(1);
        rectFigure.setLineStyle(SWT.LINE_CUSTOM);
        rectFigure.setLineDash(new float[] { 4 });
        root.add(rectFigure);

        Polyline line = new Polyline();
        line.addPoint(new Point(10, 85));
        line.addPoint(new Point(260, 85));
        line.setLineStyle(SWT.LINE_CUSTOM);
        line.setLineDash(new float[] { 4 });
        root.add(line);

        shell.open();
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}