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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Tree item heights should not shrink when setting font
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/677
 */
public class MacTreeItemHeight {
    
    public static void main(String[] args) {
        //System.setProperty("org.eclipse.swt.internal.cocoa.enforceNativeItemHeightMinimum", "false");
        //System.setProperty("org.eclipse.swt.internal.carbon.smallFonts", "");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Tree Row Height");
        shell.setLayout(new GridLayout());
        
        Tree tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        GridDataFactory.create(GridData.FILL_HORIZONTAL).applyTo(tree);
        
        for(int i = 0; i < 11; i++) {
            TreeItem item = new TreeItem(tree, 0);
            item.setText("Item " + i);
            
            for(int j = 0; j < 4; j++) {
                TreeItem item2 = new TreeItem(item, 0);
                item2.setText("Item " + j);
            }
        }
        
        Label label1 = new Label(shell, SWT.NONE);
        Label label2 = new Label(shell, SWT.NONE);
        updateLabels(tree, label1, label2);
        
        Button button = new Button(shell, SWT.PUSH);
        button.setText("Set font");
        button.addSelectionListener(widgetSelectedAdapter(e -> {
            // Set table font to the existing table font
            tree.setFont(tree.getFont());
            updateLabels(tree, label1, label2);
        }));
        
        shell.setSize(500, 450);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }

    private static void updateLabels(Tree tree, Label label1, Label label2) {
        label1.setText("Table font: " + tree.getFont().getFontData()[0]);
        label2.setText("Table item height: " + tree.getItemHeight());
    }
}