package swt;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Load an SVG Image
 * 
 * @author Phillip Beauvoir
 */
public class SVGImageLoader {
    
    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("SVG Image Test");
        shell.setLayout(new FillLayout());
        shell.setSize(600, 450);
        
        ImageDescriptor id = ImageDescriptor.createFromFile(SVGImageLoader.class, "images/exportpref_wiz.svg");
        Image image = id.createImage();
        
        //Image image = new Image(display, SVGImageLoader.class.getResourceAsStream("images/exportpref_wiz.svg"));
        
        shell.addPaintListener(e -> {
            e.gc.drawImage(image, 0, 0);
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
