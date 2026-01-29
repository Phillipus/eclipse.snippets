package gef;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
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
public class SimpleClippedTextImage {
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        new SimpleClippedTextImage();
    }
    
    SimpleClippedTextImage() {
        Shell shell = new Shell();
        shell.setText("Image Text Clipped");
        shell.setLayout(new GridLayout());
        shell.setSize(400, 300);
        Display display = shell.getDisplay();
        
        Image image = createImageFromGraphicalViewer();
        
        shell.addPaintListener(e -> {
            e.gc.drawImage(image, 0, 0);
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
    
    Image createImageFromGraphicalViewer() {
        Shell tmpShell = new Shell();
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.setControl(tmpShell);
        
        MainEditPart mainEditPart = new MainEditPart();
        viewer.setContents(mainEditPart);
        viewer.flush();
        
        LayerManager layerManager = (LayerManager)viewer.getEditPartRegistry().get(LayerManager.ID);
        IFigure printableLayer = layerManager.getLayer(LayerConstants.PRINTABLE_LAYERS);
        
        Image image = new Image(tmpShell.getDisplay(), printableLayer.getBounds().width, printableLayer.getBounds().height);
        GC gc = new GC(image);
        SWTGraphics graphics = new SWTGraphics(gc);
        printableLayer.paint(graphics);
        
        gc.dispose();
        graphics.dispose();
        tmpShell.dispose();
        
        return image;
    }
    
    static class MainEditPart extends AbstractGraphicalEditPart {
        @Override
        protected IFigure createFigure() {
            FreeformLayer figure = new FreeformLayer();
            figure.setOpaque(true);
            figure.setBackgroundColor(new Color(255, 255, 255));
            figure.setLayoutManager(new FreeformLayout());
            
            TextFigure textFigure = new TextFigure();
            textFigure.setBounds(new Rectangle(40, 40, 200, 80));
            textFigure.setText("External Business Services");
            figure.add(textFigure);
            
            return figure;
        }

        @Override
        protected void createEditPolicies() {
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
}