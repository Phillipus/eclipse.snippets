package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * See https://github.com/eclipse-gef/gef-classic/issues/984
 */
public class TextFlowIssue {
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setLayout(new GridLayout());
        shell.setSize(400, 300);
        Display display = shell.getDisplay();
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        
        Canvas canvas = new Canvas(shell, 0);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);
        viewer.setControl(canvas);
        
        ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);

        MainEditPart mainEditPart = new MainEditPart();
        rootEditPart.setContents(mainEditPart);
        
        TextFigure textFigure = new TextFigure();
        textFigure.setBounds(new Rectangle(40, 40, 200, 80));
        textFigure.setText("External Business Services");
        mainEditPart.getFigure().add(textFigure);
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
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

    static class MainEditPart extends AbstractGraphicalEditPart {
        @Override
        protected IFigure createFigure() {
            FreeformLayer figure = new FreeformLayer();
            figure.setLayoutManager(new FreeformLayout());
            return figure;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}