package draw2d;

import org.eclipse.draw2d.DelegatingLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * TextFlowTest
 * 
 * See https://github.com/eclipse-gef/gef-classic/issues/957
 * 
 */
public class TextFlowTest {
    
    /**
     * This is a simplified version of Archi's AbstractTextControlContainerFigure
     * 
     * It consists of:
     * 
     * Main Figure (DelegatingLayout)
     *   - TextWrapperFigure (Figure -> FlowPage -> TextFlow) text box
     *   - FreeformLayer (XYLayout()) acts as container for child figures
     */
    static class TextControlContainerFigure extends Figure {
        IFigure mainFigure;
        TextFlow textFlow;
        
        TextControlContainerFigure() {
            setLayoutManager(new DelegatingLayout());
            setOpaque(true);
            setBackgroundColor(new Color(181, 255, 255));
            
            // Text locator for text control
            Locator textLocator = new Locator() {
                @Override
                public void relocate(IFigure target) {
                    Rectangle rect = getBounds().getCopy();
                    translateFromParent(rect);
                    target.setBounds(rect);
                }
            };
            
            // Add text control
            textFlow = createTextFlowControl(textLocator);
            
            // Locator for FreeformLayer
            Locator mainLocator = new Locator() {
                @Override
                public void relocate(IFigure target) {
                    Rectangle rect = getBounds().getCopy();
                    translateFromParent(rect);
                    target.setBounds(rect);
                }
            };
            
            // Add FreeformLayer
            add(getMainFigure(), mainLocator);
        }
        
        IFigure getMainFigure() {
            if(mainFigure == null) {
                mainFigure = new FreeformLayer();
                mainFigure.setLayoutManager(new XYLayout());
            }
            return mainFigure;
        }
        
        protected TextFlow createTextFlowControl(Locator textLocator) {
            TextFlow textFlow = new TextFlow();
            textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));
            
            FlowPage page = new FlowPage();
            page.setHorizontalAligment(PositionConstants.CENTER);
            page.add(textFlow);
            
            Figure textWrapperFigure = new Figure();
            GridLayout layout = new GridLayout();
            layout.marginWidth = 4;
            layout.marginHeight = 4;
            textWrapperFigure.setLayoutManager(layout);
            textWrapperFigure.add(page, new GridData(SWT.CENTER, SWT.TOP, true, true));
            
            add(textWrapperFigure, textLocator);
            
            return textFlow;
        }
        
        @Override
        protected boolean useLocalCoordinates() {
            return true;
        }

        public void setText(String text) {
            textFlow.setText(text);
        }
    }
    
    /**
     * Simplified Text Figure
     */
    static class SimpleTextFigure extends Figure {
        TextFlow textFlow;
        
        SimpleTextFigure() {
            setLayoutManager(new StackLayout());
            setOpaque(true);
            setBackgroundColor(new Color(181, 255, 255));

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

    public static void main(String[] args) {
        // Ensure this is set
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        
        // Drawd2d autoscale
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "false");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setBounds(400, 400, 300, 300);

        FigureCanvas fc = new FigureCanvas(shell);
        
        Figure root = new Figure();
        root.setOpaque(true);
        root.setBackgroundColor(new Color(255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(root);
        
        TextControlContainerFigure textFigure = new TextControlContainerFigure();
        textFigure.setBounds(new Rectangle(80, 40, 124, 55));
        root.add(textFigure);
        
        textFigure.setText("TBK/TB Trading Bank A00004");

        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}