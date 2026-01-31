package gef;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import utils.Utils;

/**
 * Resize window lag
 * See https://github.com/eclipse-gef/gef-classic/issues/983
 */
public class DragLag {
    
    public static void main(String[] args) {
        // Setting either of these to false = no lag/delay
        System.setProperty(Utils.SWT_AUTOSCALE_UPDATE_ON_RUNTIME, "true");
        // Drawd2d autoscale - set this true/false to compare
        System.setProperty(Utils.DRAW2D_ENABLE_AUTOSCALE, "true");
        
        Shell shell = new Shell();
        shell.setText("Resize to large size and note the lag when resizing");
        shell.setLayout(new GridLayout());
        shell.setBounds(100, 100, 1024, 800);
        
        GraphicalViewer viewer = new GraphicalViewerImpl();
        viewer.setControl(shell);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }
}