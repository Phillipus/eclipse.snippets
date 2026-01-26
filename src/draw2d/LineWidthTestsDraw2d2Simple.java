package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * LineWidth adjustments on Draw2d
 */
public class LineWidthTestsDraw2d2Simple {
    
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