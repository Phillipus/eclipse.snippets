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
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * Text is clipped when exporting to Image
 * @see https://github.com/eclipse-gef/gef-classic/issues/977
 */
public class SimpleClippedTextImage {
    
    public static void main(String[] args) {
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        new SimpleClippedTextImage();
    }
    
    SimpleClippedTextImage() {
        Shell parentShell = new Shell();
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.setControl(parentShell);
        
        ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);

        MainEditPart mainEditPart = new MainEditPart();
        rootEditPart.setContents(mainEditPart);
        
        TextFigure textFigure = new TextFigure();
        textFigure.setBounds(new Rectangle(40, 40, 200, 80));
        textFigure.setText("External Business Services");
        mainEditPart.getFigure().add(textFigure);
        
        viewer.flush();
        
        createImage(viewer);
        parentShell.dispose();
    }
    
    @SuppressWarnings("restriction")
    void createImage(GraphicalViewer viewer) {
        LayerManager layerManager = (LayerManager)viewer.getEditPartRegistry().get(LayerManager.ID);
        IFigure printableLayer = layerManager.getLayer(LayerConstants.PRINTABLE_LAYERS);
        
        Image image = new Image(null, printableLayer.getBounds().width, printableLayer.getBounds().height);
        GC gc = new GC(image);
        SWTGraphics graphics = new SWTGraphics(gc);
        printableLayer.paint(graphics);
        gc.dispose();
        graphics.dispose();
        
        ImageData imageData = image.getImageData(DPIUtil.getNativeDeviceZoom());
        image.dispose();
        
        // Set image path here...
        String imagePath = "C:/Users/Phillipus/Desktop/test.png";
        
        ImageLoader loader = new ImageLoader();
        loader.data = new ImageData[] { imageData };
        loader.save(imagePath, SWT.IMAGE_PNG);
        
        Program.launch(imagePath);
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
            figure.setOpaque(true);
            figure.setBackgroundColor(new Color(255, 255, 255));
            figure.setLayoutManager(new FreeformLayout());
            return figure;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}