package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Tests Suite
 */
@Suite
@SelectClasses({
    TestTemplate.class,
    TestTemplateWithParameters.class
})

@SuiteDisplayName("All Tests")
public class AllTests {
}