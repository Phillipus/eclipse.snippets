package swt;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * See https://github.com/eclipse-platform/eclipse.platform.swt/issues/2621
 * Affects macOS 26.1 and later
 * 
 * @author Phillip Beauvoir
 */
public class MacStyledTextBug2621 {

    public static void main(String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setText("StyledText Test");
        shell.setLayout(new FillLayout());
        shell.setSize(1000, 800);

        StyledText st = new StyledText(shell, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);

        String text = generateRandomText(40, 20, 0.6d);
        st.setText(text);

        st.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.keyCode == 'a' && (e.stateMask & SWT.COMMAND) == SWT.COMMAND) {
                    st.selectAll();
                }
            }
        });

        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }

    private static String generateRandomText(int lineCount, int avgWordsPerLine, double variation) {
        StringBuilder sb = new StringBuilder();
        
        int minWords = Math.max(1, (int)(avgWordsPerLine * (1 - variation)));
        int maxWords = (int)(avgWordsPerLine * (1 + variation));

        for(int i = 0; i < lineCount; i++) {
            int wordCount = minWords + random.nextInt(maxWords - minWords + 1);
            sb.append(generateLine(wordCount));
            if(i < lineCount - 1) {
                sb.append('\n');
            }
        }
        
        return sb.toString();
    }

    private static String generateLine(int wordCount) {
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < wordCount; i++) {
            if(i > 0) {
                line.append(' ');
            }
            line.append(WORDS[random.nextInt(WORDS.length)]);
        }
        return line.toString();
    }

    private static final String[] WORDS = {"lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do",
            "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua", "enim", "ad", "minim", "veniam", "quis",
            "nostrud", "exercitation", "ullamco", "laboris", "nisi", "aliquip", "ex", "ea", "commodo", "consequat", "duis", "aute", "irure",
            "in", "reprehenderit", "voluptate", "velit", "esse", "cillum", "eu", "fugiat", "nulla", "pariatur", "excepteur", "sint",
            "occaecat", "cupidatat", "non", "proident", "sunt", "culpa", "qui", "officia", "deserunt", "mollit", "anim", "id", "est",
            "laborum"};

    private static final Random random = new Random();
}
