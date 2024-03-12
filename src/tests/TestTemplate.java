package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Test Template
 * Can be used as a basis for unit testing components
 */
public class TestTemplate {
    
    private Shell shell;
    
    @BeforeEach
    public void runBeforeEachTest() {
        shell = new Shell();
    }
    
    @AfterEach
    public void runAfterEachTest() {
        shell.dispose();
    }
    
    @BeforeAll
    public static void runBeforeAllTests() {
    }
    
    @AfterAll
    public static void runAfterAllTests() {
    }

    @Test
    public void testlabel() {
        assertNotNull(shell);
        Label label = new Label(shell, 0);
        label.setText("Hello");
        assertEquals("Hello", label.getText());
    }
}