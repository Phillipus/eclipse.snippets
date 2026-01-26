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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import utils.Utils;

/**
 * See https://github.com/eclipse-gef/gef-classic/issues/985
 */
public class ScalingTest {
    
    private static ScalableFigure scalable;
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setLayout(new GridLayout());
        shell.setBounds(100, 100, 1000, 600);
        
        Composite comp = new Composite(shell, 0);
        comp.setLayout(new GridLayout(2, false));
        GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).grab(true, false).applyTo(comp);
        
        Label label = new Label(comp, 0);
        label.setText("Scale:");
        
        Spinner scaleSpinner = new Spinner(comp, 0);
        scaleSpinner.setMinimum(1);
        scaleSpinner.setMaximum(8);
        scaleSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            scalable.setScale(scaleSpinner.getSelection());
        }));
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.setControl(shell);

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
            box.setBackgroundColor(new Color(181, 255, 255));
            box.setBounds(new Rectangle(0, 0, 80, 80));
            layer.add(box);
            
            return layer;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}