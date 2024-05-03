package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Browser SWT Snippet
 * 
 * @author Phillip Beauvoir
 */
public class SWTBrowserTest {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("SWT Browser");
        shell.setLayout(new FillLayout());
        shell.setSize(1024, 768);
        
        Browser browser = new Browser(shell, SWT.NONE);
        browser.setUrl("https://www.archimatetool.com");
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
