package swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2866
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2923
 * 
 * @author Phillip Beauvoir
 */
public class FontDialogWindowsDoubled {

    public static void main(String[] args) {
        // Set to false for no high DPI
        System.setProperty("swt.autoScale.updateOnRuntime", "true");

        Shell shell = new Shell();
        FontData fd = new FontData("Arial", 9, SWT.NORMAL);
        FontDialog dialog = new FontDialog(shell);
        dialog.setFontList(new FontData[]{fd});
        dialog.open();
    }
}
