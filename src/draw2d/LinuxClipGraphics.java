package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * See https://github.com/eclipse-gef/gef-classic/issues/1044
 * 
 * @author Phillip Beauvoir
 */
public class LinuxClipGraphics {
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(600, 300);
        shell.setText("Clip Graphics");
        
        FreeformLayeredPane layeredPane = new FreeformLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));
        layeredPane.setPreferredSize(580, 800);

        FigureCanvas canvas = new FigureCanvas(shell);
        canvas.setContents(layeredPane);
        
        Figure figure1 = new TestFigure();
        figure1.setBounds(new Rectangle(10, 120, 120, 120));
        layeredPane.add(figure1);
        
        Figure figure2 = new TestFigure();
        figure2.setBounds(new Rectangle(10, 300, 120, 120));
        layeredPane.add(figure2);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    private static class TestFigure extends Figure {
        Color background = new Color(181, 255, 255);
        
        @Override
        protected void paintFigure(Graphics graphics) {
            Rectangle rect = getBounds().getCopy();
            graphics.setBackgroundColor(background);
            graphics.fillRectangle(rect);
            
            graphics.setLineWidth(6);
            
            Path path = new Path(null);
            path.addRectangle(rect.x, rect.y, rect.width, rect.height);
            graphics.setClip(path);
            graphics.drawPath(path);
            path.dispose();
        }
    }
}