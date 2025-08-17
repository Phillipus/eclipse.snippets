package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * Menu on Windows stays wide
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2425
 */
public class WinMenuWidthBug {

    public static void main(String[] args) {
        final Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Menu Test");

        Menu appMenuBar = display.getMenuBar();
        if(appMenuBar == null) {
            appMenuBar = new Menu(shell, SWT.BAR);
            shell.setMenuBar(appMenuBar);
        }

        MenuItem main = new MenuItem(appMenuBar, SWT.CASCADE);
        main.setText("Menu");

        Menu dropdown = new Menu(appMenuBar);
        main.setMenu(dropdown);

        MenuItem mi = new MenuItem(dropdown, SWT.PUSH);
        mi.setText("Short text");
        
        mi.addSelectionListener(new SelectionListener() {
            boolean toggle;
            
            @Override
            public void widgetSelected(SelectionEvent e) {
                mi.setText(toggle ? "Short text" : "Some very long text that will make the menu wider!");
                toggle  = !toggle;
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {}
        });

        shell.setSize(300, 250);
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
}