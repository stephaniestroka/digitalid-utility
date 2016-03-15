package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.iterable.NonNullableIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class FieldInformationFactory {
    
    /* -------------------------------------------------- Fields as Variable Elements -------------------------------------------------- */
    
    private static @Nonnull @NonNullableElements NonNullableIterable<VariableElement> getFields(@Nonnull TypeElement typeElement) {
        return NullableIterable.ofNonNullableElements(javax.lang.model.util.ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement)));
    }
    
    /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
    
    private static final @Nonnull NonNullablePredicate<MethodInformation, Object> abstractGetterMatcher = new NonNullablePredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return methodInformation.isAbstract() && methodInformation.isGetter();
        }
        
    };
    
    private static final @Nonnull NonNullablePredicate<MethodInformation, Object> abstractSetterMatcher = new NonNullablePredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return methodInformation.isAbstract() && methodInformation.isSetter();
        }
        
    };
    
    private static final @Nonnull NonNullToNonNullUnaryFunction<MethodInformation, GeneratedFieldInformation, NonNullableIterable<MethodInformation>> getterToFieldFunction = new NonNullToNonNullUnaryFunction<MethodInformation, GeneratedFieldInformation, NonNullableIterable<MethodInformation>>() {
        
        @Override
        public @Nonnull GeneratedFieldInformation apply(@Nonnull MethodInformation element, @Nullable NonNullableIterable<MethodInformation> methodsOfType) {
            assert methodsOfType != null;
            
            final @Nullable MethodInformation setter;
            try {
                setter = methodsOfType.findFirst(abstractSetterMatcher);
            } catch (UnexpectedResultException e) {
                throw UnexpectedFailureException.with("Multiple fields with the same name cannot exist.", e);
            }
            return GeneratedFieldInformation.of(element.getContainingType(), element, setter);
        }
        
    };
    
    public static @Nonnull @NonNullableElements NonNullableIterable<GeneratedFieldInformation> getGeneratedFieldInformation(@Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        return methodsOfType.filter(abstractGetterMatcher).map(getterToFieldFunction, methodsOfType);
    }
    
    /* -------------------------------------------------- Directly Accessible Field Information -------------------------------------------------- */
    
    private static @Nonnull NonNullablePredicate<VariableElement, Object> accessibleFieldMatcher = new NonNullablePredicate<VariableElement, Object>() {
        
        @Override
        public boolean apply(@Nonnull VariableElement field, @Nullable Object object) {
            return !field.getModifiers().contains(Modifier.PRIVATE) && !field.getModifiers().contains(Modifier.FINAL);
        }
        
    };
    
    private static @Nonnull NonNullToNonNullUnaryFunction<VariableElement, DeclaredFieldInformation, DeclaredType> toDeclaredFieldFunction = new NonNullToNonNullUnaryFunction<VariableElement, DeclaredFieldInformation, DeclaredType>() {
        
        @Override
        public @Nonnull DeclaredFieldInformation apply(@Nonnull VariableElement element, @Nullable DeclaredType containingType) {
            assert containingType != null;
            return DeclaredFieldInformation.of(element, containingType);
        }
        
    };
    
    private static @Nonnull @NonNullableElements NonNullableIterable<DeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields, @Nonnull DeclaredType containingType) {
        return fields.filter(accessibleFieldMatcher).map(toDeclaredFieldFunction, containingType);
    }
    
    public static @Nonnull @NonNullableElements NullableIterable<DeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
    
        final @Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields = getFields(typeElement);
        return getDirectlyAccessibleFieldInformation(fields, containingType);
    }
    
   
/*  TODO: remove this
    private static @Nonnull NonNullablePredicate<VariableElement, NonNullableIterable<VariableElement>> notRepresentingAndAccessibleFieldMatcher = new NonNullablePredicate<VariableElement, NonNullableIterable<VariableElement>>() {
        
        @Override
        public boolean apply(@Nonnull VariableElement field, @Nullable NonNullableIterable<VariableElement> representingParameters) {
            assert representingParameters != null;
            try {
                return !field.getModifiers().contains(Modifier.PRIVATE) && (representingParameters.findFirst(variableElementNameMatcher, field.getSimpleName().toString()) == null);
            } catch (UnexpectedResultException e) {
                throw UnexpectedFailureException.with("Multiple fields with the same name cannot exist.", e);
            }
        }
        
    };
    
    private static @Nonnull @NonNullableElements NonNullableIterable<DeclaredFieldInformation> getDeclaredFieldInformation(@Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields, @Nonnull NonNullableIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType) {
         return fields.filter(notRepresentingAndAccessibleFieldMatcher, representingParameters).map(toDeclaredFieldFunction, containingType);
    }
    
    private static @Nonnull NonNullToNonNullUnaryFunction<? super RepresentingFieldInformation, RepresentingFieldInformation, Object> castToRepresentingFieldInformation = new NonNullToNonNullUnaryFunction<RepresentingFieldInformation, RepresentingFieldInformation, Object>() {
        
        @Override 
        public @Nonnull RepresentingFieldInformation apply(@Nonnull RepresentingFieldInformation element, @Nullable Object additionalInformation) {
            return element;
        }
        
    };
    
    private static @Nonnull NonNullToNonNullUnaryFunction<? super FieldInformation, FieldInformation, Object> castToFieldInformation = new NonNullToNonNullUnaryFunction<FieldInformation, FieldInformation, Object>() {
        
        @Override 
        public @Nonnull FieldInformation apply(@Nonnull FieldInformation element, @Nullable Object additionalInformation) {
            return element;
        }
        
    };
    
    @SuppressWarnings("unchecked")
    public static @Nonnull @NonNullableElements List<FieldInformation> getFieldInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType, @Nonnull NonNullableIterable<VariableElement> representingParameters, @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> methodsOfType) {
        final @Nonnull @NonNullableElements NonNullableIterable<VariableElement> fields = getFields(typeElement);
        final @Nonnull @NonNullableElements NonNullableIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation = getParameterBasedFieldInformation(fields, representingParameters, containingType, methodsOfType);
        final @Nonnull @NonNullableElements NonNullableIterable<GeneratedFieldInformation> generatedFieldInformation = getGeneratedFieldInformation(methodsOfType);
        
        final @Nonnull @NonNullableElements NonNullableIterable<RepresentingFieldInformation> representingFields = NullableIterable.ofNonNullableElements(parameterBasedFieldInformation).map(castToRepresentingFieldInformation).combine(generatedFieldInformation.map(castToRepresentingFieldInformation));
        
        final @Nonnull @NonNullableElements NonNullableIterable<DeclaredFieldInformation> declaredFieldInformation = getDeclaredFieldInformation(fields, representingParameters, containingType);
    
        return representingFields.map(castToFieldInformation).combine(declaredFieldInformation.map(castToFieldInformation)).toList();
    }
    */
    
}
