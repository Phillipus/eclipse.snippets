package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FontTest {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("SWT Test");
        shell.setLayout(new FillLayout());
        shell.setSize(300, 250);
        shell.setBackground(new Color(255, 255, 255));
        
        Font font = new Font(display, "Arial", 11, SWT.NORMAL);
        
        shell.addPaintListener(e -> {
            e.gc.setFont(font);
            e.gc.drawText("Hello World, this is Arial Font 11", 20, 50);
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
