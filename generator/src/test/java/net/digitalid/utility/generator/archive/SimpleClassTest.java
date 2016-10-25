package net.digitalid.utility.generator.archive;

import javax.annotation.Nonnull;

import net.digitalid.utility.testing.RootTest;

import org.junit.Test;

import static net.digitalid.utility.testing.CustomAssert.*;

/**
 * Tests the instantiation of the generated subclass of {@link SimpleClass}.
 */
public class SimpleClassTest extends RootTest {
    
    @Test
    public void shouldInstantiateObjectWithNumber() throws Exception {
        final @Nonnull SimpleClass simpleClass = SimpleClassBuilder.withNumber(1).build();
        
        expecting("number", SimpleClass::getNumber).of(simpleClass).toBe(1);
    }
    
    @Test
    public void shouldBeEqual() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(1).build();
        
        expectingEqual("representing fields").of(simpleClass1).and(simpleClass2);
    }
    
    @Test
    public void shouldNotBeEqual() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(2).build();
    
        expectingDifferent("representing fields").of(simpleClass1).and(simpleClass2);
    }
    
    @Test
    public void shouldProduceSameHashCode() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(1).build();
        
        expectingEqual("hashCode", Object::hashCode).of(simpleClass1).and(simpleClass2);
    }
    
    @Test
    public void shouldProduceDifferentHashCode() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(2).build();
    
        expectingDifferent("hashCode", Object::hashCode).of(simpleClass1).and(simpleClass2);
    }
    
    @Test
    public void shouldProduceSameToString() throws Exception {
        final @Nonnull SimpleClass simpleClass = SimpleClassBuilder.withNumber(1).build();
    
        expecting("string representation", Object::toString).of(simpleClass).toBe("SimpleClass(number: 1)");
    }
    
    @Test
    public void shouldProduceDifferentToString() throws Exception {
        final @Nonnull SimpleClass simpleClass1 = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull SimpleClass simpleClass2 = SimpleClassBuilder.withNumber(2).build();
        
        expectingDifferent("string representation", Object::toString).of(simpleClass1).and(simpleClass2);
    }
    
}
