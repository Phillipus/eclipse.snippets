package swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Context menu on Windows stays wide
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2425
 */
public class WinContextMenuWidthBug {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Windows Context Menu");
        shell.setLayout(new GridLayout());
        
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        shell.setMenu(menuMgr.createContextMenu(shell));

        Action action = new Action() {};
        
        menuMgr.addMenuListener(new IMenuListener() {
            boolean toggle = true;
            
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                action.setText(toggle ? "Short text" : "Some very long text that will make the menu wider!");
                manager.add(action);
                manager.add(new Action("Other 1") {});
                manager.add(new Action("Other 2") {});
                toggle = !toggle;
            }
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