package net.digitalid.utility.generator.generators;

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

public class SubclassTest extends CustomTest {
    
    @Test
    public void testSubclass() {
        final @Nonnull SubclassedInterfaceSubclass object = new SubclassedInterfaceSubclass(true, 1234, "hi");
        assertEquals(true, object.isFlag());
        assertEquals(1234, object.getSize());
        assertEquals("hi", object.getText());
    }
    
}
