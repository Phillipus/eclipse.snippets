package swt;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * On Mac there is a delay when closing a Dialog
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/3718
 */
public class DialogCloseDelay {

    public static void main(String[] args) {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setText("Dialog Test");
        shell.setLayout(new FillLayout());
        shell.setSize(450, 250);
        shell.open();

        // Same behavior on JFace Dialog or SWT Dialog (MessageBox)
        boolean useDialog = true;
        if(useDialog) {
            Dialog dialog = new Dialog(shell) {};
            dialog.open();
        }
        else {
            MessageBox box = new MessageBox(shell);
            box.open();
        }
        
        // Uncomment this to unfreeze it
        // shell.getDisplay().readAndDispatch();

        // Long running task
        try {
            Thread.sleep(4000);
        }
        catch(InterruptedException e) {}

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
}