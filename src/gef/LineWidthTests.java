package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import utils.Utils;

/**
 * LineWidth adjustments on GEF 
 */
public class LineWidthTests {
    
    static ScalableFreeformRootEditPart rootEditPart;
    static int lineWidth = 1;
    
    static class TestFigure extends Figure {
        TestFigure() {
            setOpaque(true);
            setBackgroundColor(new Color(181, 255, 255));
        }
        
        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.fillRectangle(getBounds());
            
            graphics.setLineWidth(lineWidth);
            
            // Inner rectangle to show what the line width should be
            Rectangle innerRect = getBounds().getCopy();
            innerRect.shrink(12, 12);
            graphics.drawRectangle(innerRect);

            // Outer rectangle
            Rectangle outerRect = getBounds().getCopy();
            
            // If linewidth is even shrink by half of lineWidth
            // This works if lineWidth is an even number but not with an odd number
            if((lineWidth & 1) == 0) { // 
                outerRect.shrink(lineWidth / 2, lineWidth / 2);
            }
            // Odd number linewidth - reduce width and height by linewidth and translate by half of linewidth
            else {
                outerRect.resize(-lineWidth, -lineWidth);
                graphics.translate(lineWidth / 2.0f, lineWidth / 2.0f);
            }

            graphics.drawRectangle(outerRect);
        }
    }
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(2, false));
        shell.setBounds(100, 100, 1020, 600);
        
        Label label = new Label(shell, 0);
        label.setText("Scale:");
        
        Spinner scaleSpinner = new Spinner(shell, 0);
        GridDataFactory.swtDefaults().applyTo(scaleSpinner);
        scaleSpinner.setMinimum(1);
        scaleSpinner.setMaximum(8);
        scaleSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            int scale = scaleSpinner.getSelection();
            ScalableFigure figure = (ScalableFigure)rootEditPart.getLayer(LayerConstants.SCALABLE_LAYERS);
            figure.setScale(scale);
        }));
        
        label = new Label(shell, 0);
        label.setText("Line width:");
        
        Spinner linewidthSpinner = new Spinner(shell, 0);
        GridDataFactory.swtDefaults().applyTo(linewidthSpinner);
        linewidthSpinner.setMinimum(1);
        linewidthSpinner.setMaximum(6);
        linewidthSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            lineWidth = linewidthSpinner.getSelection();
            rootEditPart.getFigure().repaint();
        }));
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);
        
        MainEditPart mainEditPart = new MainEditPart();
        rootEditPart.setContents(mainEditPart);
        
        TestFigure textFigure = new TestFigure();
        textFigure.setBounds(new Rectangle(5, 5, 120, 55));
        mainEditPart.getFigure().add(textFigure);
        
        Canvas canvas = new Canvas(shell, 0);
        GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(canvas);
        viewer.setControl(canvas);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
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