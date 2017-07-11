package net.digitalid.utility.generator.generators.converter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.annotations.generators.GenerateTableConverter;
import net.digitalid.utility.storage.enumerations.ForeignKeyAction;
import net.digitalid.utility.storage.interfaces.Unit;
import net.digitalid.utility.testing.UtilityTest;

import org.junit.Test;

@GenerateSubclass
@GenerateTableConverter(columns = "key", onDelete = ForeignKeyAction.SET_NULL)
interface ClassForTable {
    
    @Pure
    public long getKey();
    
    @Pure
    public @Nonnull String getValue();
    
}

public class TableConverterTest extends UtilityTest {
    
    @Test
    public void testColumnNames() {
        assertThat(ClassForTableConverter.INSTANCE.getColumnNames(Unit.DEFAULT)).containsExactly("key");
    }
    
    @Test
    public void testOnDeleteAction() {
        assertThat(ClassForTableConverter.INSTANCE.getOnDeleteAction()).isEqualTo(ForeignKeyAction.SET_NULL);
    }
    
}
