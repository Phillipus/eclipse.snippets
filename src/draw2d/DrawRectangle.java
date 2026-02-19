package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Line width 
 * See https://github.com/eclipse-gef/gef-classic/issues/1020
 */
public class DrawRectangle {
    static int scale = 1;
    static int lineWidth = 1;
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(800, 800);
        
        FigureCanvas fc = new FigureCanvas(shell);

        ScalableFreeformLayeredPane layeredPane = new ScalableFreeformLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));
        fc.setContents(layeredPane);
        
        fc.addListener(SWT.MouseVerticalWheel, e -> {
            if((e.stateMask & SWT.MOD1) != 0) {
                scale += e.count < 0 ? -1 : 1;
                scale = Math.clamp(scale, 1, 8);
                layeredPane.setScale(scale);
                layeredPane.setPreferredSize(scale * 130, scale * 400);
            }
        });
        
        fc.addListener(SWT.KeyDown, e -> {
            scale += e.character == '-' ? -1 : e.character == '+' || e.character == '=' ? 1 : 0;
            scale = Math.clamp(scale, 1, 8);
            layeredPane.setScale(scale);
            layeredPane.setPreferredSize(scale * 130, scale * 400);
        });
        
        Color background = new Color(181, 255, 255);
        
        // RectangleFigure - completely wrong
        RectangleFigure figure1 = new RectangleFigure();
        figure1.setOpaque(true);
        figure1.setBackgroundColor(background);
        figure1.setBounds(new Rectangle(10, 10, 120, 70));
        figure1.setLineWidth(lineWidth);
        layeredPane.add(figure1);
        
        // Draw rectangle with figure bounds - line is clipped by 1 pixel on all sides
        IFigure figure2 = new Figure() {
            @Override
            protected void paintFigure(Graphics graphics) {
                graphics.setBackgroundColor(background);
                graphics.fillRectangle(getBounds());
                graphics.setLineWidth(lineWidth);
                graphics.drawRectangle(getBounds());
            }
        };
        figure2.setBounds(new Rectangle(10, 110, 120, 70));
        layeredPane.add(figure2);
        
        // Draw rectangle by shrinking bounds by lineWidth - the rectangle is too small (you can see fill color outside)
        IFigure figure3 = new Figure() {
            @Override
            protected void paintFigure(Graphics graphics) {
                graphics.setBackgroundColor(background);
                graphics.fillRectangle(getBounds());
                graphics.setLineWidth(lineWidth);
                Rectangle rect = getBounds().getCopy();
                rect.shrink(lineWidth, lineWidth);
                graphics.drawRectangle(rect);
            }
        };
        figure3.setBounds(new Rectangle(10, 210, 120, 70));
        layeredPane.add(figure3);
        
        // Draw rectangle by translating graphics by half of lineWidth - this is the only one that works
        IFigure figure4 = new Figure() {
            @Override
            protected void paintFigure(Graphics graphics) {
                graphics.setBackgroundColor(background);
                graphics.fillRectangle(getBounds());
                graphics.setLineWidth(lineWidth);
                Rectangle rect = getBounds().getCopy();
                rect.resize(-lineWidth, -lineWidth);
                graphics.translate(lineWidth / 2.0f, lineWidth / 2.0f);
                graphics.drawRectangle(rect);
            }
        };
        figure4.setBounds(new Rectangle(10, 310, 120, 70));
        layeredPane.add(figure4);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}