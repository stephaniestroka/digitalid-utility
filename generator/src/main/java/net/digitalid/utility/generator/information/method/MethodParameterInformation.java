package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.getter.Default;

import com.sun.tools.javac.code.Type;

/**
 *
 */
public class MethodParameterInformation extends ElementInformationImplementation implements VariableElementInformation {
    
    /* -------------------------------------------------- Matching Field -------------------------------------------------- */
    
    private final @Nullable FieldInformation matchingFieldInformation;
    
    @Pure
    public @Nullable FieldInformation getMatchingField() {
        return matchingFieldInformation;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    protected MethodParameterInformation(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        super(element, element.asType(), containingType);
        
        Require.that(element.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(element));
        
        this.matchingFieldInformation = null;
    }
    
    protected MethodParameterInformation(@Nonnull Element element, @Nonnull DeclaredType containingType, @Nonnull FiniteIterable<DirectlyAccessibleDeclaredFieldInformation> directlyAccessibleDeclaredFieldInformation, @Nonnull FiniteIterable<NonDirectlyAccessibleDeclaredFieldInformation> nonDirectlyAccessibleDeclaredFieldInformation) {
        super(element, element.asType(), containingType);
        
        Require.that(element.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(element));
    
        @Nullable FieldInformation matchingFieldInformation = directlyAccessibleDeclaredFieldInformation.findFirst(field -> field.getName().equals(getName()));
        if (matchingFieldInformation == null) {
            matchingFieldInformation = nonDirectlyAccessibleDeclaredFieldInformation.findFirst(field -> field.getName().equals(getName()));
        }
        this.matchingFieldInformation = matchingFieldInformation;
    }
    
    /* -------------------------------------------------- toString -------------------------------------------------- */
    
    @Override
    public @Nonnull String toString() {
        return getType()  + " " + getElement();
    }
    
    /* -------------------------------------------------- Default Value -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean hasDefaultValue() {
        if (hasAnnotation(Default.class) || matchingFieldInformation != null && matchingFieldInformation.hasAnnotation(Default.class)) {
            return true;
        }
        return false;
    }
    
    @Pure
    @Override
    public @Nonnull String getDefaultValue() {
        @Nullable String defaultValue = null;
        if (hasAnnotation(Default.class)) {
            defaultValue = getAnnotation(Default.class).value();
        } else {
            if (matchingFieldInformation != null && matchingFieldInformation.hasAnnotation(Default.class)) {
                defaultValue = matchingFieldInformation.getDefaultValue();
            }
        }
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
                    defaultValue = "false";
                } else {
                    defaultValue = "0";
                }
            } else {
                ProcessingLog.debugging("element: $, type kind: $", getElement(), getType().getKind());
                defaultValue = "null";
            }
        }
        return defaultValue;
    }
}
