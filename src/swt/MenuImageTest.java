package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * Show a Shell with a main menu and one menu item of type SWT.CHECK with an image.
 * On Windows 11 the checked status is not shown (should have highlight around the image)
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/501
 */
public class MenuImageTest {

    public static void main(String[] args) {
        final Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Menu Image Test");

        Menu appMenuBar = display.getMenuBar();
        if(appMenuBar == null) {
            appMenuBar = new Menu(shell, SWT.BAR);
            shell.setMenuBar(appMenuBar);
        }

        MenuItem file = new MenuItem(appMenuBar, SWT.CASCADE);
        file.setText("File");

        Menu dropdown = new Menu(appMenuBar);
        file.setMenu(dropdown);

        MenuItem testMenu = new MenuItem(dropdown, SWT.CHECK);
        testMenu.setImage(new Image(display, MenuImageTest.class.getResourceAsStream("images/app-16.png")));
        testMenu.setText("Test Menu");

        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
}