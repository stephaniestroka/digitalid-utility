package net.digitalid.utility.generator.generators.converter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.conversion.converter.CustomField;
import net.digitalid.utility.conversion.converter.Representation;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.generator.annotations.generators.GenerateConverter;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.testing.RootTest;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.generation.Provide;
import net.digitalid.utility.validation.annotations.generation.Provided;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.value.Valid;

import org.junit.Test;

import static net.digitalid.utility.conversion.converter.types.CustomType.*;

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
    public static @Nonnull EnumWithRecoverMethod of(int id) {
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
        throw UnexpectedFailureException.with("Unexpected id $ found", id);
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
    public static @Nonnull EnumWithRecoverMethodAndNonDirectlyAccessibleField of(byte value) {
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
        throw UnexpectedFailureException.with("Unexpected id $ found", value);
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
                CustomField.with(STRING, "text", ImmutableList.withElements())
        };
        assertEquals(Arrays.asList(customFieldsArray), testDeclaration.collectedFields);
    }
    
    @Test
    public void testValueCollectionOfClass() throws Exception {
        final @Nonnull VariousFields variousFields = new VariousFields(true, 5, "bla");
        final @Nonnull TestEncoder testValueCollector = new TestEncoder();
        VariousFieldsConverter.INSTANCE.convert(variousFields, testValueCollector);
        assertEquals(3, testValueCollector.collectedValues.size());
        assertEquals(Pair.of(true, boolean.class), testValueCollector.collectedValues.get(0));
        assertEquals(Pair.of(5, int.class), testValueCollector.collectedValues.get(1));
        assertEquals(Pair.of("bla", String.class), testValueCollector.collectedValues.get(2));
    }
    
    @Test
    public void testSelectionResultOfClass() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(true);
        testQueue.add(5);
        testQueue.add("bla");
        final @Nonnull TestDecoder testSelectionResult = new TestDecoder(testQueue);
        final @Nonnull VariousFields recoveredObject = VariousFieldsConverter.INSTANCE.recover(testSelectionResult, null);
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
        
        assertEquals(CustomField.with(STRING, "SimpleEnum", ImmutableList.withElements()), testDeclaration.collectedFields.get(0));
    }
    
    @Test
    public void testValueCollectionOfEnum() throws Exception {
        final @Nonnull TestEncoder testValueCollector = new TestEncoder();
        SimpleEnumConverter.INSTANCE.convert(SimpleEnum.COMET, testValueCollector);
        assertEquals(1, testValueCollector.collectedValues.size());
        assertEquals(Pair.of("COMET", String.class), testValueCollector.collectedValues.get(0));
    }
    
    @Test
    public void testSelectionResultOfEnum() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(SimpleEnum.COMET.name());
        final @Nonnull TestDecoder testSelectionResult = new TestDecoder(testQueue);
        final @Nonnull SimpleEnum recoveredObject = SimpleEnumConverter.INSTANCE.recover(testSelectionResult, null);
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
    public void testValueCollectionOfEnumWithRecoverMethod() throws Exception {
        final @Nonnull TestEncoder testValueCollector = new TestEncoder();
        EnumWithRecoverMethodConverter.INSTANCE.convert(EnumWithRecoverMethod.ONE, testValueCollector);
        assertEquals(1, testValueCollector.collectedValues.size());
        assertEquals(Pair.of(1, int.class), testValueCollector.collectedValues.get(0));
    }

    @Test
    public void testSelectionResultOfEnumWithRecoverMethod() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add(3);
        final @Nonnull TestDecoder testSelectionResult = new TestDecoder(testQueue);
        final @Nonnull EnumWithRecoverMethod recoveredObject = EnumWithRecoverMethodConverter.INSTANCE.recover(testSelectionResult, null);
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
    public void testValueCollectionOfEnumWithRecoverMethodAndNonDirectlyAccessibleField() throws Exception {
        final @Nonnull TestEncoder testValueCollector = new TestEncoder();
        EnumWithRecoverMethodAndNonDirectlyAccessibleFieldConverter.INSTANCE.convert(EnumWithRecoverMethodAndNonDirectlyAccessibleField.ONE, testValueCollector);
        assertEquals(1, testValueCollector.collectedValues.size());
        assertEquals(Pair.of((byte) 1, byte.class), testValueCollector.collectedValues.get(0));
    }

    @Test
    public void testSelectionResultOfEnumWithRecoverMethodAndNonDirectlyAccessibleField() throws Exception {
        final Queue<@Nonnull Object> testQueue = new LinkedList<>();
        testQueue.add((byte) 3);
        final @Nonnull TestDecoder testSelectionResult = new TestDecoder(testQueue);
        final @Nonnull EnumWithRecoverMethodAndNonDirectlyAccessibleField recoveredObject = EnumWithRecoverMethodAndNonDirectlyAccessibleFieldConverter.INSTANCE.recover(testSelectionResult, null);
        assertEquals(EnumWithRecoverMethodAndNonDirectlyAccessibleField.THREE, recoveredObject);
    }
    
}
