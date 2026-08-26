package swt;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * Menu items disappear on macOS 27
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/3540
 */
public class MacMenuDisappearing {

    public static void main(String[] args) {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setSize(500, 500);
        shell.setText("Mac Menu Test");

        Menu appMenuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(appMenuBar);

        MenuItem file = new MenuItem(appMenuBar, SWT.CASCADE);
        file.setText("File");
        
        MenuItem editItem = new MenuItem(appMenuBar, SWT.CASCADE);
        editItem.setText("Edit");
        
        MenuItem source = new MenuItem(appMenuBar, SWT.CASCADE);
        source.setText("Source");

        shell.open();
        
        shell.addListener(SWT.MouseUp, e -> {
            Dialog dialog = new Dialog(shell) {};
            dialog.open();
        });

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
}