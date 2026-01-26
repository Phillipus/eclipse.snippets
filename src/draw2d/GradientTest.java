package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * GradientTest
 * See https://github.com/eclipse-gef/gef-classic/discussions/968
 */
public class GradientTest {
    
    public static void main(String[] args) {
        // SWT scaling
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        
        // Drawd2d autoscale - set this true/false to compare
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setBounds(100, 100, 600, 400);

        FigureCanvas fc = new FigureCanvas(shell);
        
        // Set to true to use Scaledgraphics and the gridient will be correct
        //ScalableFreeformLayeredPane layeredPane = new ScalableFreeformLayeredPane(true);
        ScalableFreeformLayeredPane layeredPane = new ScalableFreeformLayeredPane();
        
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(layeredPane);
        
        // Set scale
        layeredPane.setScale(2.0);
        
        Figure figure = new RectangleFigure() {
            Color startColor = new Color(255, 255, 255);
            Color endColor = new Color(123, 123, 255);
            
            @Override
            public void paintFigure(Graphics graphics) {
                super.paintFigure(graphics);
                Rectangle rect = getBounds();
                
                Pattern gradient = createGradient(graphics, display, rect, startColor, endColor);
                graphics.setBackgroundPattern(gradient);
                graphics.fillRectangle(rect);
                gradient.dispose();
            }
        };
        
        figure.setBounds(new Rectangle(40, 40, 180, 120));
        layeredPane.add(figure);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    static Pattern createGradient(Graphics graphics, Device device, Rectangle rect, Color startColor, Color endColor) {
        // Get scale
        double scale = graphics.getAbsoluteScale();
        
        // But scale should be 1.0
        //scale = 1.0;
        
        float x1 = (float)(rect.x * scale);
        float y1 =  (float)(rect.y * scale);
        float x2 = (float)(rect.getRight().x * scale);
        float y2 = (float)(rect.y * scale);
        
        return new Pattern(device, x1, y1, x2, y2, startColor, 255, endColor, 255);
    }
}