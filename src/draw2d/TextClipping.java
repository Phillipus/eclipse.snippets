package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * TextClipping
 * See https://github.com/eclipse-gef/gef-classic/issues/957
 */
public class TextClipping {
    
    static class SimpleTextFigure extends Figure {
        TextFlow textFlow;
        
        SimpleTextFigure() {
            setLayoutManager(new StackLayout());
            setOpaque(true);
            setBackgroundColor(new Color(181, 255, 255));

            textFlow = new TextFlow();
            textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));

            FlowPage page = new FlowPage();
            page.setHorizontalAligment(PositionConstants.CENTER);
            page.add(textFlow);
            add(page);
        }
        
        public void setText(String text) {
            textFlow.setText(text);
        }
    }    

    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setBounds(400, 400, 300, 300);

        FigureCanvas fc = new FigureCanvas(shell);
        
        Figure root = new Figure();
        root.setOpaque(true);
        root.setBackgroundColor(new Color(255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(root);
        
        SimpleTextFigure textFigure = new SimpleTextFigure();
        textFigure.setBounds(new Rectangle(20, 40, 200, 55));
        root.add(textFigure);
        
        textFigure.setText("External Business Services");

        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}