package swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Template SWT Snippet
 * 
 * @author Phillip Beauvoir
 */
public class SWTTemplate {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("SWT Test");
        shell.setLayout(new FillLayout());
        shell.setSize(450, 250);
        
        // Add code here
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
