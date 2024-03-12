package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * JUnit Test Template with parameters
 * Can be used as a basis for unit testing components
 */
public class TestTemplateWithParameters {
    
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

    @ParameterizedTest
    @MethodSource("getStyleArgs")
    public void testDateTime(int style) {
        DateTime datetime = new DateTime(shell, style);
        datetime.setTime(2, 10, 30);
        assertEquals(2, datetime.getHours());
        assertEquals(10, datetime.getMinutes());
        assertEquals(30, datetime.getSeconds());
    }
    
    @ParameterizedTest
    @MethodSource("getStyleArgsWithName")
    public void testDateTime2(int style) {
        DateTime datetime = new DateTime(shell, style);
        datetime.setTime(2, 10, 30);
        assertEquals(2, datetime.getHours());
        assertEquals(10, datetime.getMinutes());
        assertEquals(30, datetime.getSeconds());
    }
    
    private static Stream<Integer> getStyleArgs() {
        return Stream.of(SWT.DATE,
                         SWT.TIME,
                         SWT.CALENDAR);
    }
    
    private static Stream<Arguments> getStyleArgsWithName() {
        return Stream.of(Arguments.of(Named.named("SWT.DATE", SWT.DATE)),
                         Arguments.of(Named.named("SWT.TIME", SWT.TIME)),
                         Arguments.of(Named.named("SWT.CALENDAR", SWT.CALENDAR)));
    }
}