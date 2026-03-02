package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Test line widths
 */
public class DrawRectangle2 {
    
    private static double scale = 1;
    private static final double increment = 0.5f;
    
    private static boolean isMacLinux = !SWT.getPlatform().equals("win32");
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(800, 800);
        shell.setText("Scale: 100%");
        
        FigureCanvas canvas = new FigureCanvas(shell);
        ScalableFreeformLayeredPane layeredPane = new ScalableFreeformLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));
        canvas.setContents(layeredPane);
        
        canvas.addListener(SWT.MouseVerticalWheel, e -> {
            if((e.stateMask & SWT.MOD1) != 0) {
                double inc = e.count < 0 ? -increment : increment;
                setScale(shell, layeredPane, inc);
            }
        });
        
        canvas.addListener(SWT.KeyDown, e -> {
            double inc = e.character == '-' ? -increment : e.character == '+' || e.character == '=' ? increment : 0;
            setScale(shell, layeredPane, inc);
        });
        
        Color background = new Color(181, 255, 255);
        
        Figure figure = new Figure() {
            @Override
            protected void paintFigure(Graphics graphics) {
                int lineWidth = 1;
                Rectangle rect = getBounds().getCopy();
                graphics.setBackgroundColor(background);
                fillRectangle(graphics, rect);
                drawRectangle(graphics, rect, lineWidth);
            }
        };
        
        figure.setBounds(new Rectangle(10, 10, 120, 70));
        layeredPane.add(figure);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    static void drawRectangle(Graphics graphics, Rectangle rect, int lineWidth) {
       // linewidth is even number 2, 4, 6...
        if((lineWidth & 1) == 0) {
            graphics.setLineWidth(lineWidth);
            graphics.drawRectangle(rect.getCopy().shrink(lineWidth / 2, lineWidth / 2));
        }
        // linewidth is odd 1, 3, 5...
        else {
            drawRectanglePath(graphics, rect, lineWidth);
        }
    }
    
    static void drawRectanglePath(Graphics graphics, Rectangle rect, int lineWidth) {
        graphics.setLineWidth(lineWidth);
        Path path = createRectanglePath(rect, lineWidth, false);
        graphics.drawPath(path);
        path.dispose();
    }
    
    static void fillRectangle(Graphics graphics, Rectangle rect) {
        Path path = createRectanglePath(rect, 1, true);
        graphics.fillPath(path);
        path.dispose();
    }
    
    static Path createRectanglePath(Rectangle rect, int lineWidth, boolean isFill) {
        float inset = lineWidth / 2.0f;
        float extraInset = isMacLinux ? isFill ? inset * 2 : inset : 0;
        
        float x = rect.x + inset;
        float y = rect.y + inset;
        float width = rect.width - lineWidth - extraInset;
        float height = rect.height - lineWidth - extraInset;
        
        Path path = new Path(null);
        path.addRectangle(x, y, width, height);
        return path;
    }

    static void setScale(Shell shell, ScalableFreeformLayeredPane layeredPane, double inc) {
        scale = Math.clamp(scale + inc, 1, 8);
        layeredPane.setScale(scale);
        layeredPane.setPreferredSize((int)scale * 130, (int)scale * 400);
        shell.setText("Scale: " + String.format("%d%%", Math.round(scale * 100)));
    }
}