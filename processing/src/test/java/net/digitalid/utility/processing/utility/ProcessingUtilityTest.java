package net.digitalid.utility.processing.utility;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import net.digitalid.utility.functional.iterables.FiniteIterable;

import com.google.testing.compile.CompilationRule;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.util.Context;
import net.digitqalid.utility.processing.testset.ClassA;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

/**
 * This test set is used to confirm that the methods of ProcessingUtility work as expected.
 */
public class ProcessingUtilityTest {
    
    public @Rule CompilationRule rule = new CompilationRule();
    
    private Elements elements;
    
    @BeforeClass
    public static void beforeClass() {
        Context context = new Context();
        final JavacProcessingEnvironment javacProcessingEnvironment = JavacProcessingEnvironment.instance(context);
        StaticProcessingEnvironment.environment.set(javacProcessingEnvironment);
    }
    
    @Before
    public void before() {
        this.elements = rule.getElements();
    }
    
    @Test
    public void shouldReturnAllMembersOfClassA() {
        TypeElement classAElement = elements.getTypeElement(ClassA.class.getName());
        @Nonnull final FiniteIterable<@Nonnull Element> allMembers = ProcessingUtility.getAllMembers(classAElement);
        assertThat(allMembers.filter(element -> element.getSimpleName().contentEquals("equals")).size()).isSameAs(1);
    }

}
