package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.annotations.Default;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;

import com.sun.tools.javac.code.Type;

/**
 * This class implements the {@link FieldInformation} interface.
 * 
 * @see NonDirectlyAccessibleFieldInformation
 * @see DirectlyAccessibleFieldInformation
 */
public abstract class FieldInformationImplementation extends ElementInformationImplementation implements FieldInformation {
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    private final @Nullable String defaultValue;
    
    @Pure
    @Override
    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
    
    @Pure
    @Override
    public @Nonnull String getDefaultValue() {
        if (defaultValue == null) {
            final String typeName;
            if (getType().getKind().isPrimitive()) {
                if (getType() instanceof Type.AnnotatedType) {
                    Type.AnnotatedType annotatedType = (Type.AnnotatedType) getType();
                    final Type type = annotatedType.unannotatedType();
                    typeName = type.toString();
                } else {
                    typeName = getType().toString();
                }
                if (typeName.equals("boolean")) {
                    return "false";
                } else {
                    return "0";
                }
            } else {
                ProcessingLog.debugging("element: $, type kind: $", getElement(), getType().getKind());
                return "null";
            }
        } else {
            return defaultValue;
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FieldInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
        
        this.defaultValue = ProcessingUtility.getStringValue(element, Default.class);
    }
    
}
