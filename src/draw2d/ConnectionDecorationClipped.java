package draw2d;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * See https://github.com/eclipse-gef/gef-classic/issues/1047
 */
public class ConnectionDecorationClipped {
    
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.setLayout(new FillLayout());
        shell.setSize(1200, 500);
        Display display = shell.getDisplay();
        
        FigureCanvas canvas = new FigureCanvas(shell);
        ScalableFreeformLayeredPane layeredPane = new ScalableFreeformLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));
        canvas.setContents(layeredPane);
        layeredPane.setScale(5);
        
        Figure figure1 = new Figure();
        figure1.setBackgroundColor(ColorConstants.cyan);
        figure1.setOpaque(true);
        figure1.setBounds(new Rectangle(10, 10, 50, 50));
        layeredPane.add(figure1);
        
        Figure figure2 = new Figure();
        figure2.setBackgroundColor(ColorConstants.cyan);
        figure2.setOpaque(true);
        figure2.setBounds(new Rectangle(160, 10, 50, 50));
        layeredPane.add(figure2);
        
        PolylineConnection connection = new PolylineConnection();
        connection.setLineWidth(1);
        
        // Set this to true to expand bounds
        boolean doFix = true;

        PolygonDecoration dec1 = new PolygonDecoration() {
            @Override
            public Rectangle getBounds() {
                return doFix ? super.getBounds().getExpanded(1, 1) : super.getBounds();
            }
        };
        dec1.setScale(10, 7);
        connection.setSourceDecoration(dec1);
        
        PolylineDecoration dec2 = new PolylineDecoration() {
            @Override
            public Rectangle getBounds() {
                return doFix ? super.getBounds().getExpanded(1, 1) : super.getBounds();
            }
        };
        dec2.setScale(10, 7);
        connection.setTargetDecoration(dec2);

        connection.setSourceAnchor(new ChopboxAnchor(figure1));
        connection.setTargetAnchor(new ChopboxAnchor(figure2));
        
        layeredPane.add(connection);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}