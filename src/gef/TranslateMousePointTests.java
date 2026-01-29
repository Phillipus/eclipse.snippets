package gef;

import java.util.Objects;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

public class TranslateMousePointTests {
    
    public static void main(String[] args) {
        // Set true
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        
        // Set to true for scaling, false otherwise
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        new TranslateMousePointTests();
    }
    
    TestFigure figure;
    
    TranslateMousePointTests() {
        Shell shell = new Shell();
        shell.setLayout(new FillLayout());
        shell.setSize(400, 300);
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.setControl(shell);
        
        MainEditPart mainEditPart = new MainEditPart();
        viewer.setContents(mainEditPart);
        
        figure = new TestFigure();
        mainEditPart.getFigure().add(figure);
        
        testLocations();
        
        shell.dispose();
    }
    
    void testLocations() {
        // Have to use PrecisionPoint in case of monitor scales like 1.25, 1.5, 1.75
        
        testPoint(new Point(0, 0), new PrecisionPoint(0, 0));
        testPoint(new Point(0, 0), new PrecisionPoint(50, 50));
        testPoint(new Point(0, 0), new PrecisionPoint(100, 100));
        testPoint(new Point(0, 0), new PrecisionPoint(200, 200));
        testPoint(new Point(0, 0), new PrecisionPoint(300, 300));
        testPoint(new Point(0, 0), new PrecisionPoint(400, 400));
        
        testPoint(new Point(100, 100), new PrecisionPoint(0, 0));
        testPoint(new Point(100, 100), new PrecisionPoint(50, 50));
        testPoint(new Point(100, 100), new PrecisionPoint(100, 100));
        testPoint(new Point(100, 100), new PrecisionPoint(200, 200));
        testPoint(new Point(100, 100), new PrecisionPoint(300, 300));
        testPoint(new Point(100, 100), new PrecisionPoint(400, 400));
    }
    
    void testPoint(Point location, PrecisionPoint testPoint) {
        figure.setLocation(location);
        
        Point expected = new Point();
        
        // If Windows autoscaling is set
        if(Utils.isAutoScaleEnabled()) {
            float scale = Utils.getDeviceZoom();
            expected.x = Math.round((testPoint.x / scale) - location.x);
            expected.y = Math.round((testPoint.y / scale) - location.y);
        }
        else {
            expected.x = testPoint.x - location.x;
            expected.y = testPoint.y - location.y;
        }
        
        figure.getFreeformLayer().translateToRelative(testPoint);
        
        // Round the result in case of scale factors like 1.25, 1.5, 1.75
        Point result = new Point((int)Math.round(testPoint.preciseX()), (int)Math.round(testPoint.preciseY()));
        
        assertEquals(expected, result);
    }
    
    void assertEquals(Object expected, Object actual) {
        if(Objects.equals(expected, actual)) {
            System.out.println("Pass! expected: <" + expected + "> equals <" + actual + ">");
        }
        else {
            System.out.println("Fail! expected: <" + expected + "> but was <" + actual + ">");
        }
    }
    
    void printPoint(Point location, PrecisionPoint testPoint) {
        figure.setLocation(location);
        System.out.println("Location: " + location.x + "," + location.y);

        System.out.println("Test Point: " + testPoint.x + "," + testPoint.y);
        
        figure.getFreeformLayer().translateToRelative(testPoint);
        System.out.println("Translated: " + testPoint.x + "," + testPoint.y);
        System.out.println("------------------------");
    }
    
    static class TestFigure extends Figure {
        FreeformLayer freeFormLayer;
        
        TestFigure() {
            freeFormLayer = new FreeformLayer();
            freeFormLayer.setLayoutManager(new XYLayout());
            add(freeFormLayer);
        }
        
        FreeformLayer getFreeformLayer() {
            return freeFormLayer;
        }
        
        @Override
        protected boolean useLocalCoordinates() {
            return true;
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