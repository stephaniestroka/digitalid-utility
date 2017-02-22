package net.digitalid.utility.generator.archive;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.testing.UtilityTest;

import org.junit.Test;

/**
 * Tests the instantiation of the generated subclass of {@link SimpleClass}.
 */
public class SimpleClassTest extends UtilityTest {
    
    @Test
    public void shouldInstantiateObjectWithNumber() throws Exception {
        final @Nonnull SimpleClass simpleClass = SimpleClassBuilder.withNumber(1).build();
        assertThat(simpleClass).hasFieldOrPropertyWithValue("number", 1);
    }
    
    @Test
    public void shouldBeEqual() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(1).build();
        assertThat(simpleClass1).isEqualTo(simpleClass2);
    }
    
    @Test
    public void shouldNotBeEqual() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(2).build();
        assertThat(simpleClass1).isNotEqualTo(simpleClass2);
    }
    
    @Test
    public void shouldProduceSameHashCode() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(1).build();
        assertThat(simpleClass1).isEqualToComparingOnlyGivenFields(simpleClass2, "hashCode");
    }
    
    @Test
    public void shouldProduceDifferentHashCode() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(2).build();
        assertThat(FiniteIterable.of(simpleClass1, simpleClass2)).extracting("hashCode").doesNotHaveDuplicates();
    }
    
    @Test
    public void shouldProduceSameToString() throws Exception {
        final @Nonnull SimpleClass simpleClass = SimpleClassBuilder.withNumber(1).build();
        assertThat(simpleClass).hasToString("SimpleClass(number: 1)");
    }
    
}
