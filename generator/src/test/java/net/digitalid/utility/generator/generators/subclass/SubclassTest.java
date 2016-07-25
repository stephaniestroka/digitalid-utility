package net.digitalid.utility.generator.generators.subclass;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

@Immutable
@GenerateSubclass
interface SubclassedInterface {
    
    @Pure
    public boolean isFlag();
    
    @Pure
    public int getSize();
    
    @Pure
    public String getText();
    
}

@Immutable
@GenerateSubclass
abstract class SubclassedClass {
    
    @Pure
    public abstract boolean isFlag();
    
    @Pure
    public abstract int getSize();
    
    @Pure
    public abstract String getText();
    
}

public class SubclassTest extends CustomTest {
    
    @Test
    public void testInterfaceSubclass() {
        final @Nonnull SubclassedInterfaceSubclass object = new SubclassedInterfaceSubclass(true, 1234, "hi");
        assertEquals(true, object.isFlag());
        assertEquals(1234, object.getSize());
        assertEquals("hi", object.getText());
    }
    
    @Test
    public void testClassSubclass() {
        final @Nonnull SubclassedClassSubclass object = new SubclassedClassSubclass(true, 1234, "hi");
        assertEquals(true, object.isFlag());
        assertEquals(1234, object.getSize());
        assertEquals("hi", object.getText());
    }
    
}
