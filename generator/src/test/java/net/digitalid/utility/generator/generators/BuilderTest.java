package net.digitalid.utility.generator.generators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.testing.CustomTest;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;

import org.junit.Test;

@Mutable
abstract class AbstractFields {
    
    private final boolean flag;
    
    @Pure
    public boolean isFlag() {
        return flag;
    }
    
    private final int size;
    
    @Pure
    public int getSize() {
        return size;
    }
    
    private final @Nullable String text;
    
    @Pure
    public @Nullable String getText() {
        return text;
    }
    
    protected AbstractFields(boolean flag, int size, @Nullable String text) {
        this.flag = flag;
        this.size = size;
        this.text = text;
    }
    
}

@Immutable
@GenerateBuilder
class MandatoryFields extends AbstractFields {
    
    @Pure
    @Override
    public @Nonnull String getText() {
        return super.getText();
    }
    
    protected MandatoryFields(boolean flag, int size, @Nonnull String text) {
        super(flag, size, text);
    }
    
}

@Mutable
@GenerateBuilder
class OptionalFields extends AbstractFields {
    
    @Impure
    public void setFlag(boolean flag) {
        // Ignore
    }
    
    @Impure
    public void setSize(int size) {
        // Ignore
    }
    
    protected OptionalFields(boolean flag, int size, @Nullable String text) {
        super(flag, size, text);
    }
    
}

@Mutable
@GenerateBuilder
class MixedFields {
    
    boolean flag;
    
    final int size;
    
    String text;
    
    MixedFields(boolean flag, int size, String text) {
        this.flag = flag;
        this.size = size;
        this.text = text;
    }
    
}

public class BuilderTest extends CustomTest {
    
    @Test
    public void testMandatoryFields() {
//        final @Nonnull MandatoryFields object = MandatoryFieldsBuilder.withFlag(true).withSize(1234).withText("hi").build();
//        assertEquals(true, object.flag);
//        assertEquals(1234, object.size);
//        assertEquals("hi", object.text);
//    }
//    
//    @Test
//    public void testOptionalFields() {
//        final @Nonnull OptionalFields object = OptionalFieldsBuilder.build();
//        assertEquals(false, object.flag);
//        assertEquals(0, object.size);
//        assertEquals(null, object.text);
//        
//        assertEquals(true, OptionalFieldsBuilder.withFlag(true).build().flag);
//        assertEquals(1234, OptionalFieldsBuilder.withSize(1234).build().size);
//        assertEquals("hi", OptionalFieldsBuilder.withText("hi").build().text);
//    }
//    
//    @Test
//    public void testMixedFields() {
//        final @Nonnull MixedFields object = MixedFieldsBuilder.withSize(1234).build();
//        assertEquals(false, object.flag);
//        assertEquals(1234, object.size);
//        assertEquals(null, object.text);
//        
//        assertEquals(true, MixedFieldsBuilder.withSize(1234).withFlag(true).build().flag);
//        assertEquals("hi", MixedFieldsBuilder.withSize(1234).withText("hi").build().text);
    }
    
}
