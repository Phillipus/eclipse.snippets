package swt;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Mac crashes to desktop when Cmd-Z pressed
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/1273
 */
public class MacTextControlCrash {
    
    static Text textControl;

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Mac Text Control Bug");
        shell.setLayout(new GridLayout());
        
        Button b  = new Button(shell, SWT.PUSH);
        b.setText("New Text Control");
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                // Dispose of text control and create a new one
                textControl.dispose();
                textControl = createTextControl(shell, "Now press Cmd-Z");
                shell.layout();
            }
        });
        
        textControl = createTextControl(shell, "Type something here and then click the button above.");
        
        shell.setSize(300, 250);
        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
    
    static Text createTextControl(Composite parent, String text) {
        Text textControl = new Text(parent, SWT.MULTI | SWT.WRAP);
        GridDataFactory.create(GridData.FILL_BOTH).applyTo(textControl);
        textControl.setText(text);
        textControl.setFocus();
        return textControl;
    }
}