package swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Emojis not displayed on Windows
 * Root cause is SWT when GC.setAdvanced(true)
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/3581
 */
public class EmojiTest {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("SWT Test");
        shell.setLayout(new FillLayout());
        shell.setSize(300, 250);
        shell.setBackground(new Color(255, 255, 255));
        
        String emoji = Character.toString(0x1F600);
        
        shell.addPaintListener(e -> {
            // Set to false to see the emoji
            e.gc.setAdvanced(true);
            e.gc.drawText("Hello World " + emoji, 20, 50);
        });
        
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
