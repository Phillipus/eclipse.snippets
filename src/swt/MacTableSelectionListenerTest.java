package swt;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Selection using keyboard is out of sync on Mac
 * 
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1052
 */
public class MacTableSelectionListenerTest {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Table");
        shell.setLayout(new FillLayout());
        
        Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        for(int i = 0; i < 12; i++) {
            TableItem item = new TableItem(table, 0);
            item.setText("Item " + i);
        }
        
        table.addSelectionListener(widgetSelectedAdapter(e -> System.out.println(((Item)e.item).getText())));
        
        shell.setSize(200, 200);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }
}