package gef;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * On Windows changing display scale doesn't update text control
 * See https://github.com/eclipse-gef/gef-classic/issues/1194
 */
public class WindowsFontDisplayScale {
    
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.setLayout(new FillLayout());
        shell.setBounds(100, 100, 600, 400);
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.createControl(shell);

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
            FlowPage page = new FlowPage();
            page.setFont(new Font(null, "Arial", 30, SWT.NORMAL));
            page.setBounds(new Rectangle(10, 10, 400, 200));
            page.setBackgroundColor(new Color(181, 255, 255));
            page.setOpaque(true);
            page.setHorizontalAligment(PositionConstants.CENTER);

            TextFlow textFlow = new TextFlow();
            textFlow.setText("External Business Services");
            page.add(textFlow);
            
            return page;
        }

        @Override
        protected void createEditPolicies() {
        }
    }
}