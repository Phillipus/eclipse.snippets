package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Scale control not showing when on the left on macOS Sonoma 14
 * Works OK on macOS 13
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1107
 */
public class ScaleControlNotShowingMacOS14 {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(2, false));
        shell.setText("SWT Test");
        shell.setSize(450, 300);
        
        // Change this to true to show the Scale control first,
        // or false to show it after the Text control
        boolean showScaleFirst = true;
        
        if(showScaleFirst) {
            new Scale(shell, SWT.VERTICAL);
        }

        Text text = new Text(shell, SWT.MULTI);
        text.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        if(!showScaleFirst) {
            new Scale(shell, SWT.VERTICAL);
        }
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
