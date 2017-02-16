package net.digitalid.utility.generator.generators.converter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.conversion.converters.StringConverter;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.exceptions.RecoveryExceptionBuilder;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.testing.RootTest;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.generation.Provide;
import net.digitalid.utility.validation.annotations.generation.Provided;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.value.Valid;

import org.junit.Test;

import static net.digitalid.utility.conversion.model.CustomType.*;
import static org.junit.Assert.assertEquals;

@Immutable
@GenerateConverter
class VariousFields {
    
    public final boolean flag;
    
    public final int size;
    
    public final String text;
    
    protected VariousFields(boolean flag, int size, @Nonnull String text) {
        this.flag = flag;
        this.size = size;
        this.text = text;
    }
    
}

@Immutable
@GenerateConverter
class ExternallyProvidedType {
    
    public final @Nonnull String value;
    
    ExternallyProvidedType(@Nonnull String value) {
        this.value = value;
    }
    
}

@Immutable
@GenerateConverter
class ExternallyProvidedField {
    
    @Provided
    public final @Nonnull ExternallyProvidedType externallyProvidedType;
    
    protected ExternallyProvidedField(@Nonnull ExternallyProvidedType externallyProvidedType) {
        this.externallyProvidedType = externallyProvidedType;
    }
    
}

@Immutable
@GenerateConverter
class DependentType {
    
    public final @Nonnull String value;
    
    @Provided
    public final @Nonnull ExternallyProvidedType externallyProvidedType;
    
    DependentType(@Nonnull String value, @Nonnull ExternallyProvidedType externallyProvidedType) {
        this.value = value;
        this.externallyProvidedType = externallyProvidedType;
    }
    
}

@Immutable
@GenerateConverter
class ProvideValue {
    
    public final @Nonnull ExternallyProvidedType externallyProvidedType;
    
    @Provide("externallyProvidedType")
    public final @Nonnull DependentType dependentType;
    
    protected ProvideValue(@Nonnull ExternallyProvidedType externallyProvidedType, @Nonnull DependentType dependentType) {
        this.externallyProvidedType = externallyProvidedType;
        this.dependentType = dependentType;
    }
    
}

@GenerateConverter
enum SimpleEnum {
    
    STAR,
    PLANET,
    COMET,
    MOON
    
}

@GenerateConverter
enum EnumWithRecoverMethod {
    
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);
    
    int id;
    
    EnumWithRecoverMethod(int id) {
        this.id = id;
    }
    
    @Recover
    public static @Nonnull EnumWithRecoverMethod of(int id) throws RecoveryException {
        switch (id) {
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
        }
        throw RecoveryExceptionBuilder.withMessage(Strings.format("Unexpected id $ found.", id)).build();
    }
    
}

@GenerateConverter
enum EnumWithRecoverMethodAndNonDirectlyAccessibleField {
    
    ZERO((byte) 0),
    ONE((byte) 1),
    TWO((byte) 2),
    THREE((byte) 3);
    
    private final @Valid byte value;
    
    @Pure
    public @Valid byte getValue() {
        return value;
    }
    
    public static boolean isValid(byte value) {
        return true;
    }
    
    EnumWithRecoverMethodAndNonDirectlyAccessibleField(byte value) {
        this.value = value;
    }
    
    @Recover
    public static @Nonnull EnumWithRecoverMethodAndNonDirectlyAccessibleField of(byte value) throws RecoveryException {
        switch (value) {
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
        }
        throw RecoveryExceptionBuilder.withMessage(Strings.format("Unexpected value $ found.", value)).build();
    }
    
}

@GenerateConverter
class ClassContainingEnum {
    
    public final EnumWithRecoverMethod enumWithRecoverMethod;
    
    ClassContainingEnum(EnumWithRecoverMethod enumWithRecoverMethod) {
        this.enumWithRecoverMethod = enumWithRecoverMethod;
    }
    
}

public class ConverterTest extends RootTest {
    
    @Test
    public void testFieldsOfClass() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        final @Nonnull ImmutableList<@Nonnull CustomField> fields = VariousFieldsConverter.INSTANCE.getFields(Representation.EXTERNAL);
        for (@Nonnull CustomField field : fields) {
            testDeclaration.setField(field);
        }
    
        final @Nonnull CustomField[] customFieldsArray = { 
                CustomField.with(BOOLEAN, "flag", ImmutableList.withElements()),
                CustomField.with(INTEGER32, "size", ImmutableList.withElements()),
                CustomField.with(TUPLE.of(StringConverter.INSTANCE), "text", ImmutableList.withElements())
        };
        assertEquals(Arrays.asList(customFieldsArray), testDeclaration.collectedFields);
    }
    
    @Test
    public void testEncodingOfClass() throws Exception {
        final @Nonnull VariousFields variousFields = new VariousFields(true, 5, "bla");
        final @Nonnull TestEncoder testEncoder = new TestEncoder();
        VariousFieldsConverter.INSTANCE.convert(variousFields, testEncoder);
        assertEquals(3, testEncoder.encodedValues.size());
        assertEquals(Pair.of(true, boolean.class), testEncoder.encodedValues.get(0));
        assertEquals(Pair.of(5, int.class), testEncoder.encodedValues.get(1));
        assertEquals(Pair.of("bla", String.class), testEncoder.encodedValues.get(2));
    }
    
    @Test
    public void testDecodingOfClass() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(true);
        testQueue.add(5);
        testQueue.add("bla");
        final @Nonnull TestDecoder testDecoder = new TestDecoder(testQueue);
        final @Nonnull VariousFields recoveredObject = VariousFieldsConverter.INSTANCE.recover(testDecoder, null);
        assertEquals(true, recoveredObject.flag);
        assertEquals(5, recoveredObject.size);
        assertEquals("bla", recoveredObject.text);
    }
    
    @Test
    public void testFieldsOfEnum() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        final @Nonnull ImmutableList<@Nonnull CustomField> fields = SimpleEnumConverter.INSTANCE.getFields(Representation.EXTERNAL);
        for (@Nonnull CustomField field : fields) {
            testDeclaration.setField(field);
        }
        
        assertEquals(CustomField.with(TUPLE.of(StringConverter.INSTANCE), "SimpleEnum", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }
    
    @Test
    public void testEncodingOfEnum() throws Exception {
        final @Nonnull TestEncoder testEncoder = new TestEncoder();
        SimpleEnumConverter.INSTANCE.convert(SimpleEnum.COMET, testEncoder);
        assertEquals(1, testEncoder.encodedValues.size());
        assertEquals(Pair.of("COMET", String.class), testEncoder.encodedValues.get(0));
    }
    
    @Test
    public void testDecodingOfEnum() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(SimpleEnum.COMET.name());
        final @Nonnull TestDecoder testDecoder = new TestDecoder(testQueue);
        final @Nonnull SimpleEnum recoveredObject = SimpleEnumConverter.INSTANCE.recover(testDecoder, null);
        assertEquals(SimpleEnum.COMET, recoveredObject);
    }
    
    @Test
    public void testFieldsOfEnumWithRecoverMethod() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        final @Nonnull ImmutableList<@Nonnull CustomField> fields = EnumWithRecoverMethodConverter.INSTANCE.getFields(Representation.EXTERNAL);
        for (@Nonnull CustomField field : fields) {
            testDeclaration.setField(field);
        }
        
        assertEquals(CustomField.with(INTEGER32, "id", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }

    @Test
    public void testEncodingOfEnumWithRecoverMethod() throws Exception {
        final @Nonnull TestEncoder testEncoder = new TestEncoder();
        EnumWithRecoverMethodConverter.INSTANCE.convert(EnumWithRecoverMethod.ONE, testEncoder);
        assertEquals(1, testEncoder.encodedValues.size());
        assertEquals(Pair.of(1, int.class), testEncoder.encodedValues.get(0));
    }

    @Test
    public void testDecodingOfEnumWithRecoverMethod() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(3);
        final @Nonnull TestDecoder testDecoder = new TestDecoder(testQueue);
        final @Nonnull EnumWithRecoverMethod recoveredObject = EnumWithRecoverMethodConverter.INSTANCE.recover(testDecoder, null);
        assertEquals(EnumWithRecoverMethod.THREE, recoveredObject);
    }
    
    @Test
    public void testFieldsOfEnumWithRecoverMethodAndNonDirectlyAccessibleField() {
        final @Nonnull TestDeclaration testDeclaration = new TestDeclaration();
        final @Nonnull ImmutableList<@Nonnull CustomField> fields = EnumWithRecoverMethodAndNonDirectlyAccessibleFieldConverter.INSTANCE.getFields(Representation.EXTERNAL);
        for (@Nonnull CustomField field : fields) {
            testDeclaration.setField(field);
        }
        assertEquals(CustomField.with(INTEGER08, "value", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }

    @Test
    public void testEncodingOfEnumWithRecoverMethodAndNonDirectlyAccessibleField() throws Exception {
        final @Nonnull TestEncoder testEncoder = new TestEncoder();
        EnumWithRecoverMethodAndNonDirectlyAccessibleFieldConverter.INSTANCE.convert(EnumWithRecoverMethodAndNonDirectlyAccessibleField.ONE, testEncoder);
        assertEquals(1, testEncoder.encodedValues.size());
        assertEquals(Pair.of((byte) 1, byte.class), testEncoder.encodedValues.get(0));
    }

    @Test
    public void testDecodingOfEnumWithRecoverMethodAndNonDirectlyAccessibleField() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add((byte) 3);
        final @Nonnull TestDecoder testDecoder = new TestDecoder(testQueue);
        final @Nonnull EnumWithRecoverMethodAndNonDirectlyAccessibleField recoveredObject = EnumWithRecoverMethodAndNonDirectlyAccessibleFieldConverter.INSTANCE.recover(testDecoder, null);
        assertEquals(EnumWithRecoverMethodAndNonDirectlyAccessibleField.THREE, recoveredObject);
    }
    
}
