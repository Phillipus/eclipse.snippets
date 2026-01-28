package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
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
 * Scaling Issue on Windows
 * See https://github.com/eclipse-gef/gef-classic/issues/993
 */
public class ScalingTest2 {
    
    private static ScalableFigure scalable;
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setLayout(new GridLayout());
        shell.setBounds(100, 100, 600, 400);
        
        Button button = new Button(shell, 0);
        button.setText("Repaint");
        button.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            scalable.repaint();
        }));
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        
        Canvas canvas = new Canvas(shell, 0);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);
        viewer.setControl(canvas);

        ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);
        rootEditPart.setContents(new MainEditPart());
        scalable = (ScalableFigure)rootEditPart.getLayer(LayerConstants.SCALABLE_LAYERS);
        
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
            
            IFigure box = new Figure();
            box.setOpaque(true);
            box.setBackgroundColor(new Color(255, 125, 125));
            box.setBounds(new Rectangle(0, 0, 80, 80));
            layer.add(box);
            
            return layer;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}