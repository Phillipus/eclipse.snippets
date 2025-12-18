package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Line Width problem in {@link SWTGraphics#checkPaint()}
 * Line widths should be different.
 * This seems to be OK on latest version of Windows 11. Maybe it's Windows 10?
 * 
 * https://github.com/eclipse-gef/gef-classic/issues/248
 */
public class LineWidthBug248 {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(300, 300);

        FigureCanvas fc = new FigureCanvas(shell);
        
        IFigure rectFigure = new Figure() {
            @Override
            protected void paintFigure(Graphics graphics) {
                graphics.setAdvanced(true);
                Rectangle rect = getBounds().getCopy();
                
                rect.shrink(10, 10);
                graphics.setLineWidth(1);
                graphics.drawRectangle(rect);
                
                rect.shrink(20, 20);
                graphics.setLineWidth(2);
                graphics.drawRectangle(rect);
            }
        };
        
        rectFigure.setBounds(new Rectangle(10, 10, 250, 150));
        fc.getLightweightSystem().getRootFigure().add(rectFigure);

        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}