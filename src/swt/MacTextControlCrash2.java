package swt;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Mac crashes to desktop when Cmd-Z pressed
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1273
 */
public class MacTextControlCrash2 {
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Mac Text Control Bug");
        shell.setLayout(new GridLayout());
        
        Text multiText = new Text(shell, SWT.MULTI | SWT.WRAP);
        GridDataFactory.create(GridData.FILL_BOTH).applyTo(multiText);
        multiText.setText("Type something here and then click the button.");
        multiText.setFocus();
        
        Button button  = new Button(shell, SWT.PUSH);
        button.setText("Press after typing some text");
        GridDataFactory.create(GridData.FILL_HORIZONTAL).align(SWT.CENTER, SWT.CENTER).applyTo(button);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                // Dispose of text control
                multiText.dispose();
                
                button.dispose();
                
                // Add a new text control
                Text textControl = new Text(shell, SWT.SINGLE);
                GridDataFactory.create(GridData.FILL_BOTH).applyTo(textControl);
                textControl.setText("Now press Cmd-Z for a crash");
                textControl.setFocus();
                
                shell.layout();
            }
        });
        
        shell.setSize(300, 250);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
}