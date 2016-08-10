package net.digitalid.utility.generator.archive;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;

import org.junit.Test;

import static net.digitalid.utility.testing.CustomAssert.*;

/**
 * 
 */
public class ComplexClassTest {
    
    @Pure
    public ComplexClassBuilder.@Nonnull InnerComplexClassBuilder prepareBuilder() {
        final @Nonnull SimpleClass simpleClass = SimpleClassBuilder.withNumber(1).build();
        final @Nonnull String[] arrayOfStrings = new String[] { "first", "second", "third" };
        final @Nonnull List<Integer> listOfIntegers = new ArrayList<>();
        listOfIntegers.add(1);
        listOfIntegers.add(2);
        listOfIntegers.add(3);
        return ComplexClassBuilder.withText("This is a test").withSimpleClass(simpleClass).withListOfIntegers(listOfIntegers).withArrayOfStrings(arrayOfStrings);
    }
    
    @Test
    public void shouldInstantiateObject() throws Exception {
        final @Nonnull ComplexClass complexClass = prepareBuilder().build();
        
        expecting("instance", complexClass).toBeNonNull();
    }
    
    @Test
    public void shouldBeEqual() throws Exception {
        final @Nonnull ComplexClass complexClass1 = prepareBuilder().build();
        final @Nonnull ComplexClass complexClass2 = prepareBuilder().build();
        
        expectingEqual("representing fields").of(complexClass1).and(complexClass2);
    }
    
    @Test
    public void shouldBeNonEqual() throws Exception {
        final @Nonnull List<Integer> differentListOfIntegers = new ArrayList<>();
        differentListOfIntegers.add(4);
        differentListOfIntegers.add(5);
        differentListOfIntegers.add(6);
        final @Nonnull ComplexClass complexClass1 = prepareBuilder().build();
        final @Nonnull ComplexClass complexClass2 = prepareBuilder().withListOfIntegers(differentListOfIntegers).build();
        
        expectingDifferent("representing fields").of(complexClass1).and(complexClass2);
    }
    
    @Test
    public void shouldProduceSameHashCode() throws Exception {
        final @Nonnull ComplexClass complexClass1 = prepareBuilder().build();
        final @Nonnull ComplexClass complexClass2 = prepareBuilder().build();
    
        expectingEqual("hash code", Object::hashCode).of(complexClass1).and(complexClass2);
    }
    
    @Test
    public void shouldProduceDifferentHashCode() throws Exception {
        final @Nonnull List<Integer> differentListOfIntegers = new ArrayList<>();
        differentListOfIntegers.add(4);
        differentListOfIntegers.add(5);
        differentListOfIntegers.add(6);
        final @Nonnull ComplexClass complexClass1 = prepareBuilder().build();
        final @Nonnull ComplexClass complexClass2 = prepareBuilder().withListOfIntegers(differentListOfIntegers).build();
    
        expectingDifferent("hash code", Object::hashCode).of(complexClass1).and(complexClass2);
    }
    
    @Test
    public void shouldProduceSameToString() throws Exception {
        final @Nonnull ComplexClass complexClass = prepareBuilder().build();
    
        expecting("string representation", Object::toString).of(complexClass).toBe("ComplexClass(text: \"This is a test\", simpleClass: SimpleClass(number: 1), listOfIntegers: [1, 2, 3], setOfSimpleClass: null, arrayOfStrings: [first, second, third])");
    }
    
    @Test
    public void shouldProduceDifferentToString() throws Exception {
        final @Nonnull ComplexClass complexClass1 = prepareBuilder().build();
        final @Nonnull ComplexClass complexClass2 = prepareBuilder().withText("This is a negative test").build();
    
        expectingDifferent("string representation", Object::toString).of(complexClass1).and(complexClass2);
    }
    
}
