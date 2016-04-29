package net.digitalid.utility.generator.information.field;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.converter.Converter;
import net.digitalid.utility.conversion.converter.CustomType;
import net.digitalid.utility.generator.annotations.Default;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processor.generator.JavaFileGenerator;

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
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    public @Nonnull String getFieldType(@Nonnull JavaFileGenerator javaFileGenerator) {
        final @Nonnull StringBuilder returnTypeAsString = new StringBuilder();
        if (getType() instanceof Type.AnnotatedType) {
            final Type.AnnotatedType annotatedType = (Type.AnnotatedType) getType();
            for (AnnotationMirror annotationMirror : annotatedType.getAnnotationMirrors()) {
                returnTypeAsString.append("@").append(javaFileGenerator.importIfPossible(annotationMirror.getAnnotationType()));
                if (annotationMirror.getElementValues().size() > 0) {
                    returnTypeAsString.append("(");
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> elementValue : annotationMirror.getElementValues().entrySet()) {
                        final @Nonnull String nameOfKey = elementValue.getKey().getSimpleName().toString();
                        if (!nameOfKey.equals("value")) {
                            returnTypeAsString.append(nameOfKey).append("=").append(elementValue.getValue());
                        } else {
                            returnTypeAsString.append(elementValue.getValue());
                        }
                    }
                    returnTypeAsString.append(")");
                }
                returnTypeAsString.append(" ");
            }
            returnTypeAsString.append(javaFileGenerator.importIfPossible(annotatedType.unannotatedType()));
        } else {
            returnTypeAsString.append(javaFileGenerator.importIfPossible(getType()));
        }
        return returnTypeAsString.toString();
    }
    
    /* -------------------------------------------------- Is Array -------------------------------------------------- */
    
    @Pure
    public boolean isArray() {
        return (getType() instanceof Type.ArrayType);
    }
    
    @Pure
    public @Nonnull TypeMirror getComponentType() {
        Require.that(isArray()).orThrow("Expected array type");
    
        final Type.@Nonnull ArrayType type = (Type.ArrayType) getType();
        @Nonnull TypeMirror componentType = type.getComponentType();
        if (componentType instanceof Type.AnnotatedType) {
            componentType = ((Type.AnnotatedType) componentType).unannotatedType();
        }
        return componentType;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected FieldInformationImplementation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
        
        this.defaultValue = ProcessingUtility.getStringValue(element, Default.class);
    }
    
}
