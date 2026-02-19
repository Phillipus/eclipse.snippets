package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * LineWidth adjustments
 */
@SuppressWarnings("restriction")
public class LineWidthTestsDraw2d {
    
    public static void main(String[] args) {
        new LineWidthTestsDraw2d();
    }
    
    private static class TestFigure extends Figure {
        int lineWidth = 1;
        
        TestFigure() {
            setBounds(new Rectangle(10, 10, 120, 55));
        }
        
        void setLineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
            repaint();
        }
        
        @Override
        protected void paintFigure(Graphics graphics) {
            graphics.setBackgroundColor(new Color(181, 255, 255));
            graphics.fillRectangle(getBounds());
            
            Rectangle rect = getBounds().getCopy();
            setLineWidth(graphics, lineWidth, rect);
            graphics.drawRectangle(rect);
        }
        
        void setLineWidth(Graphics graphics, int lineWidth, Rectangle figureBounds) {
            graphics.setLineWidth(lineWidth);
            
            boolean useLineOffset = "win32".equals(SWT.getPlatform()) && DPIUtil.getDeviceZoom() > 100;
            
            double scale = graphics.getAbsoluteScale();
            
            // If scale is below 100% bottom and right hand rectangle line might not be drawn
            if(scale < 1.0) {
                figureBounds.resize(-1, -1);
            }
            
            // If line width is 1 and scale is 100% and don't use line offset then don't apply an offset
            // useLineOffset is false if Mac/Linux or user set preference off on Windows
            // Typically we want it on for Windows where display scaling > 100%
            if(lineWidth == 1 && scale == 1.0 && !useLineOffset) {
                figureBounds.resize(-1, -1);
                return;
            }
            
            // If linewidth is even shrink by half of lineWidth
            if((lineWidth & 1) == 0) { // 
                figureBounds.shrink(lineWidth / 2, lineWidth / 2);
                return;
            }
            
            // If linewidth is odd...
            figureBounds.resize(-lineWidth, -lineWidth);
            
            // x,y offset is half of line width
            float offset = (float)lineWidth / 2;
            
            // If this is a non hi-res device and scale == 100% round up to integer to stop anti-aliasing
            if(DPIUtil.getDeviceZoom() == 100 && scale == 1.0) {
                offset = (float)Math.ceil(offset);
            }
            
            graphics.translate(offset, offset);
        }
    }
    
    private double scale = 1;
    private ScalableFreeformLayeredPane layeredPane;
    private TestFigure figure;
    private Spinner scaleSpinner;
    
    LineWidthTestsDraw2d() {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(2, false));
        shell.setBounds(100, 100, 1020, 600);
        
        Label label = new Label(shell, 0);
        label.setText("Scale:");
        
        scaleSpinner = new Spinner(shell, SWT.READ_ONLY);
        GridDataFactory.swtDefaults().applyTo(scaleSpinner);
        scaleSpinner.setMinimum(25);
        scaleSpinner.setMaximum(800);
        scaleSpinner.setIncrement(25);
        scaleSpinner.setSelection(100);
        scaleSpinner.setDigits(2);
        scaleSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            scale = scaleSpinner.getSelection() / 100.0f;
            layeredPane.setScale(scale);
        }));
        
        label = new Label(shell, 0);
        label.setText("Line width:");
        
        Spinner linewidthSpinner = new Spinner(shell, 0);
        GridDataFactory.swtDefaults().applyTo(linewidthSpinner);
        linewidthSpinner.setMinimum(1);
        linewidthSpinner.setMaximum(4);
        linewidthSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
            figure.setLineWidth(linewidthSpinner.getSelection());
        }));
        
        layeredPane = new ScalableFreeformLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackgroundColor(new Color(255, 255, 255));

        figure = new TestFigure();
        layeredPane.add(figure);

        FigureCanvas fc = createFigureCanvas(shell);
        fc.setContents(layeredPane);
        fc.setFocus();
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
    private FigureCanvas createFigureCanvas(Shell shell) {
        FigureCanvas fc = new FigureCanvas(shell);
        GridDataFactory.defaultsFor(fc).span(2, 1).applyTo(fc);
        
        fc.addListener(SWT.MouseVerticalWheel, e -> {
            if((e.stateMask & SWT.MOD1) != 0) {
                double inc = e.count < 0 ? -0.25 : 0.25;
                setScale(inc);
            }
        });
        
        fc.addListener(SWT.KeyDown, e -> {
            double inc = e.character == '-' ? -0.25 : e.character == '+' || e.character == '=' ?  0.25 : 0;
            setScale(inc);
        });
        
        return fc;
    }
    
    private void setScale(double inc) {
        scale = Math.clamp(scale + inc , 0.25, 8);
        layeredPane.setScale(scale);
        scaleSpinner.setSelection((int)(scale * 100));
        
        //int pos = (int)Math.ceil(10 / scale);
        //figure.setLocation(new Point(pos, pos));
    }
}