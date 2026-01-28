package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * Repaint Delay on Windows
 * See https://github.com/eclipse-gef/gef-classic/issues/983
 */
public class RepaintDelay {
    
    private static ScalableFreeformRootEditPart rootEditPart;
    private static IFigure boxFigure;
    
    public static void main(String[] args) {
        // Setting either of these to false = no delay
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setLayout(new GridLayout());
        shell.setBounds(100, 100, 1000, 800);
        
        Button button = new Button(shell, 0);
        button.setText("Update");
        button.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            Color randomColor = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
            boxFigure.setBackgroundColor(randomColor);
        }));
        
        GraphicalViewer viewer = new GraphicalViewerImpl();

        Canvas canvas = new Canvas(shell, 0);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);
        viewer.setControl(canvas);

        rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);
        rootEditPart.setContents(new MainEditPart());
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }
    
    private static class MainEditPart extends AbstractGraphicalEditPart {
        @Override
        protected IFigure createFigure() {
            IFigure layer = new FreeformLayer();
            layer.setLayoutManager(new FreeformLayout());
            
            boxFigure = new Figure();
            boxFigure.setOpaque(true);
            boxFigure.setBackgroundColor(new Color(181, 255, 255));
            boxFigure.setBounds(new Rectangle(10, 40, 80, 80));
            layer.add(boxFigure);
            
            return layer;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}