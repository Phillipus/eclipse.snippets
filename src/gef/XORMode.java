package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * Once Graphics.scale() is called internally in AutoscaleFreeformViewport and IScalablePaneHelper
 * advanced mode is turned on and XOR doesn't work.
 */
public class XORMode {
    
    public static void main(String[] args) {
        // Drawd2d autoscale
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setLayout(new FillLayout());
        shell.setSize(400, 300);
        Display display = shell.getDisplay();
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);
        
        Canvas canvas = new Canvas(shell, 0);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);
        viewer.setControl(canvas);

        MainEditPart mainEditPart = new MainEditPart();
        rootEditPart.setContents(mainEditPart);
        
        SimpleFigure figure = new SimpleFigure();
        figure.setBounds(new Rectangle(10, 10, 300, 300));
        mainEditPart.getFigure().add(figure);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    static class SimpleFigure extends Figure {
        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
            graphics.fillRectangle(5, 5, 90, 45);
            
            graphics.setXORMode(true);
            graphics.setAdvanced(false);
            
            graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            graphics.fillRectangle(20,20,50,50);
            graphics.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
            graphics.fillOval(80,20,50,50);
        }
    }    

    static class MainEditPart extends AbstractGraphicalEditPart {
        @Override
        protected IFigure createFigure() {
            FreeformLayer figure = new FreeformLayer();
            figure.setOpaque(true);
            figure.setBackgroundColor(new Color(255, 255, 255));
            figure.setLayoutManager(new FreeformLayout());
            return figure;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}