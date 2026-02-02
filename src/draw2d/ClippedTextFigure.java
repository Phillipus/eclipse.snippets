package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * Text is clipped when creating Image
 * @see https://github.com/eclipse-gef/gef-classic/issues/977
 */
public class ClippedTextFigure {
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setText("Figure Text Clipped");
        shell.setLayout(new GridLayout());
        shell.setSize(300, 200);
        Display display = shell.getDisplay();
        
        TextFigure figure = new TextFigure();
        figure.setBounds(new Rectangle(0, 0, 200, 80));
        figure.setText("External Business Services");
        figure.validate();
        
// Use ImageGcDrawer
//        Image image = new Image(display, (gc, width, height) -> {
//            SWTGraphics graphics = new SWTGraphics(gc);
//            figure.paint(graphics);
//            graphics.dispose();
//        }, 200, 80);
        
        Image image = new Image(display, 200, 80);
        GC gc = new GC(image);
        SWTGraphics graphics = new SWTGraphics(gc);
        figure.paint(graphics);
        graphics.dispose();
        gc.dispose();
        
        shell.addPaintListener(e -> {
            e.gc.drawImage(image, 10, 10);
        });
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
        image.dispose();
    }
    
    static class TextFigure extends Figure {
        TextFlow textFlow;
        
        TextFigure() {
            setLayoutManager(new StackLayout());
            setOpaque(true);
            setBackgroundColor(new Color(237, 207, 226));

            // TextFlow
            textFlow = new TextFlow();
            textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));

            // Add TextFlow to FlowPage
            FlowPage page = new FlowPage();
            page.setHorizontalAligment(PositionConstants.CENTER);
            page.add(textFlow);
            add(page);
        }
        
        public void setText(String text) {
            textFlow.setText(text);
        }
    }
}