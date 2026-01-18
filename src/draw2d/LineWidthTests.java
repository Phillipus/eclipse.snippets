package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * LineWidth adjustments
 */
public class LineWidthTests {
    
    static double scale = 1.0;
    static int lineWidth = 1;
    static ScalableFreeformLayeredPane layeredPane;
    
    static class TestFigure extends Figure {
        TestFigure() {
            setOpaque(true);
            setBackgroundColor(new Color(181, 255, 255));
        }
        
        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.fillRectangle(getBounds());
            
            graphics.setLineWidth(lineWidth);
            
            Rectangle rect = getBounds().getCopy();
           
            setLineWidthAdjustment(graphics, lineWidth, rect);
            
            graphics.drawRectangle(rect);
        }
        
        @SuppressWarnings("restriction")
        void setLineWidthAdjustment(Graphics graphics, int lineWidth, Rectangle bounds) {
            // If line width is 1 and scale is 100%
            if(lineWidth == 1 && scale == 1.0) {
                bounds.resize(-1, -1);
                return;
            }
        
            // Width and height reduced by line width to compensate for x,y offset
            bounds.resize(-lineWidth, -lineWidth);
            
            // x,y offset is half of line width
            float offset = (float)lineWidth / 2;
            
            // If this is a non hi-res device and scale == 100% round up to integer to stop anti-aliasing
            if(DPIUtil.getDeviceZoom() == 100 && scale == 1.0) {
                offset = (float)Math.ceil(offset);
            }
            
            graphics.translate(offset, offset);
        }
        
        void setLineWidthAdjustment2(Graphics graphics, int lineWidth, Rectangle bounds) {
            bounds.shrink(lineWidth - 1.5, lineWidth - 1.5);
        }
    }    

    public static void main(String[] args) {
        // Ensure this is set
        System.setProperty("swt.autoScale.updateOnRuntime", "true");
        
        // Drawd2d autoscale
        System.setProperty("draw2d.enableAutoscale", "true");
        
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
            scale = scaleSpinner.getSelection();
            layeredPane.setScale(scale);
        }));
        
        label = new Label(shell, 0);
        label.setText("Line width:");
        
        Spinner linewidthSpinner = new Spinner(shell, 0);
        GridDataFactory.swtDefaults().applyTo(linewidthSpinner);
        linewidthSpinner.setMinimum(1);
        linewidthSpinner.setMaximum(4);
        linewidthSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            lineWidth = linewidthSpinner.getSelection();
            layeredPane.repaint();
        }));
        
        FigureCanvas fc = new FigureCanvas(shell);
        GridDataFactory.defaultsFor(fc).span(2, 1).applyTo(fc);
        
        layeredPane = new ScalableFreeformLayeredPane();
        layeredPane.setOpaque(true);
        //root.setBackgroundColor(new Color(255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(layeredPane);
        
        TestFigure textFigure = new TestFigure();
        textFigure.setBounds(new Rectangle(5, 5, 120, 55));
        layeredPane.add(textFigure);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}