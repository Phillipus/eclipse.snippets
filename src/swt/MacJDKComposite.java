package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Run on Mac Sonoma (or later?) with Temurin 17.0.10 or 21.0.2 or later and the top composite overlaps the bottom one
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1012
 */
public class MacJDKComposite {

    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);
        shell.setText(MacJDKComposite.class.getSimpleName());
        shell.setLayout(new FillLayout(SWT.VERTICAL));
        shell.setSize(250, 250);

        Composite comp1 = new Composite(shell, SWT.NONE);
        comp1.setBackground(new Color(255, 128, 128));
        
        Composite comp2 = new Composite(shell, SWT.NONE);
        comp2.setBackground(new Color(128, 255, 128));
        
        shell.open();
        
        // Mouse click on composite prints its bounds
        // Note that the second composite seems to only visually cover the whole shell
        // as mouse clicks work as expected.
        
        comp1.addListener(SWT.MouseUp, e -> {
            System.out.println("Clicked in top composite");
            System.out.println(comp1.getBounds());
        });
        
        comp2.addListener(SWT.MouseUp, e -> {
            System.out.println("Clicked in bottom composite");
            System.out.println(comp2.getBounds());
        });

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}