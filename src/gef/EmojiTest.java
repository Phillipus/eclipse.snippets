package gef;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * Emojis not displayed on Windows
 * Root cause is SWT when GC.setAdvanced(true)
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/3581
 */
public class EmojiTest {
    
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.setLayout(new FillLayout());
        shell.setBounds(100, 100, 600, 400);
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.createControl(shell);

        // works with SimpleRootEditPart or Windows display scaling at 100%
        RootEditPart rootEditPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootEditPart);
        rootEditPart.setContents(new MainEditPart());
        
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }
    
    private static class MainEditPart extends AbstractGraphicalEditPart {
        @Override
        protected IFigure createFigure() {
            FreeformLayer figure = new FreeformLayer();
            
            String emoji = Character.toString(0x1F600);
            
            Label label = new Label();
            label.setText("Some text and " + emoji + "  ");
            label.setBounds(new Rectangle(40, 40, 120, 50));
            label.setOpaque(true);
            label.setBackgroundColor(new Color(237, 207, 226));
            figure.add(label);
            
            return figure;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}