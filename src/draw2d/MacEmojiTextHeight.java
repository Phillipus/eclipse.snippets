package draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Text Control height is not calculated corrctly if text contains an emoji on Mac.
 * 
 * See https://github.com/eclipse/gef-classic/issues/414
 */
public class MacEmojiTextHeight {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setSize(300, 200);

        // Create a container with root figure
        FigureCanvas fc = new FigureCanvas(shell);
        Figure rootFigure = new Figure();
        rootFigure.setOpaque(true);
        rootFigure.setBackgroundColor(new Color(255, 255, 255));
        fc.getLightweightSystem().getRootFigure().add(rootFigure);
        
        // XY Layout
        rootFigure.setLayoutManager(new XYLayout());
        
        // Setting a non-system font is important here. The system font renders correctly.
        Font font = new Font(display, new FontData("Arial", 14, SWT.NORMAL));
        rootFigure.setFont(font);

        // Create a TextFlow control with FlowPage
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));

        FlowPage page = new FlowPage();
        page.add(textFlow);
        rootFigure.add(page, new Rectangle(10, 10, 200, 100));
        
        // Display text with an emoji
        String emoji = Character.toString(0x1F600); // Smiley face emoji
        textFlow.setText("Hello World " + emoji);

        shell.open();
        
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}