package net.digitalid.utility.generator.generators;

import javax.annotation.Nonnull;

import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.type.Immutable;

import org.junit.Test;

@Immutable
// TODO: The recover method of the converter expects there to be a builder!
//@GenerateConverter
class VariousFields {
    
    protected VariousFields(boolean flag, int size, @Nonnull String text) {}
    
}

public class ConverterTest extends CustomTest {
    
    // TODO: Test that the converter is generated correctly.
    
    @Test
    public void testFields() {
//        assertTrue(VariousFieldsConverter.INSTANCE.getFields().equals(FiniteIterable.of(/* ? */)));
    }
    
}
