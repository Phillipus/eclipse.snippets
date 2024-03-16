package swt;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * Table item heights should not shrink when setting font
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/677
 * 
 */
public class MacTableItemHeight {

    public static void main(String[] args) {
        //System.setProperty("org.eclipse.swt.internal.cocoa.useNativeItemHeight", "false");
        //System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Table Row Height");
        shell.setLayout(new GridLayout());
        
        Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        GridDataFactory.create(GridData.FILL_HORIZONTAL).applyTo(table);
        
        for(int i = 0; i < 11; i++) {
            TableItem item = new TableItem(table, 0);
            item.setText("Item " + i);
        }
        
        Label label1 = new Label(shell, SWT.NONE);
        Label label2 = new Label(shell, SWT.NONE);
        updateLabels(table, label1, label2);
        
        Button button = new Button(shell, SWT.PUSH);
        button.setText("Set font");
        button.addSelectionListener(widgetSelectedAdapter(e -> {
            // Set table font to the existing table font
            table.setFont(table.getFont());
            updateLabels(table, label1, label2);
        }));
        
        shell.setSize(500, 450);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }

    private static void updateLabels(Table table, Label label1, Label label2) {
        label1.setText("Table font: " + table.getFont().getFontData()[0]);
        label2.setText("Table item height: " + table.getItemHeight());
    }
}