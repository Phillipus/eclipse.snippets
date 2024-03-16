package swt;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * List item heights should not shrink when setting font
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/677
 */
public class MacListItemHeight {

    public static void main(String[] args) {
        //System.setProperty("org.eclipse.swt.internal.cocoa.enforceNativeItemHeightMinimum", "false");
        //System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");

        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("List Row Height");
        shell.setLayout(new GridLayout());
        
        List list = new List(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        GridDataFactory.create(GridData.FILL_HORIZONTAL).applyTo(list);
        
        String[] items = new String[10];
        for(int i = 0; i < 10; i++) {
            items[i] = "Item " + i;
        }
        list.setItems(items);
        
        Label label1 = new Label(shell, SWT.NONE);
        Label label2 = new Label(shell, SWT.NONE);
        updateLabels(list, label1, label2);
        
        Button button = new Button(shell, SWT.PUSH);
        button.setText("Set font");
        button.addSelectionListener(widgetSelectedAdapter(e -> {
            // Set list font to the existing table font
            list.setFont(list.getFont());
            updateLabels(list, label1, label2);
        }));
        
        shell.setSize(500, 450);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }

    private static void updateLabels(List list, Label label1, Label label2) {
        label1.setText("List font: " + list.getFont().getFontData()[0]);
        label2.setText("List item height: " + list.getItemHeight());
    }
}