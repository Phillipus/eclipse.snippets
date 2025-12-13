package swt;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2880
 * 
 * @author Phillip Beauvoir
 */
public class MacTreeAnimation {
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Tree Expansion Animation");
        shell.setLayout(new FillLayout());
        
        Tree tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL);
        
        // If there is no cell editor then expansion/collapse of tree nodes has a sliding animation,
        // but if there is a cell editor there is no animation.
        // Set this to true to compare.
        boolean addCellEditor = false;
        if(addCellEditor) {
            new TextCellEditor(tree);
        }
        
        for(int i = 0; i <= 10; i++) {
            TreeItem item = new TreeItem(tree, 0);
            item.setText("Item " + i);
            
            for(int j = 0; j <= 8; j++) {
                TreeItem item2 = new TreeItem(item, 0);
                item2.setText("Item " + j);
            }
        }
        
        shell.setSize(500, 450);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }
}