package swt;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * LineWidth adjustments om SWT
 */
public class LineWidthTestsSWT {
    
    static float scale = 1.0f;
    static int lineWidth = 1;
    
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
            shell.redraw();
        }));
        
        label = new Label(shell, 0);
        label.setText("Line width:");
        
        Spinner linewidthSpinner = new Spinner(shell, 0);
        GridDataFactory.swtDefaults().applyTo(linewidthSpinner);
        linewidthSpinner.setMinimum(1);
        linewidthSpinner.setMaximum(4);
        linewidthSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            lineWidth = linewidthSpinner.getSelection();
            shell.redraw();
        }));
        
        shell.addPaintListener(e -> {
            GC gc = e.gc;
            
            Transform scaleTransform = new Transform(gc.getDevice());
            scaleTransform.scale(scale, scale);
            gc.setTransform(scaleTransform);
            
            Rectangle rect = new Rectangle(20, 70, 120, 55);
            
            rect.x = Math.round(rect.x / scale);
            rect.y = Math.round(rect.y / scale);
            
            gc.setBackground(new Color(181, 255, 255));
            gc.fillRectangle(rect);
            
            gc.setLineWidth(lineWidth);
            gc.drawRectangle(rect);
            
            scaleTransform.dispose();
        });
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}