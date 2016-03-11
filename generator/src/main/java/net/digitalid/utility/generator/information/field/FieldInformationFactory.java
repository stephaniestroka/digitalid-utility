package net.digitalid.utility.generator.information.field;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.exceptions.ConformityViolation;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.functional.iterable.NonNullIterable;
import net.digitalid.utility.functional.iterable.NullableIterable;
import net.digitalid.utility.functional.iterable.exceptions.UnexpectedResultException;
import net.digitalid.utility.functional.iterable.filter.predicate.NonNullPredicate;
import net.digitalid.utility.functional.iterable.map.function.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 *
 */
public class FieldInformationFactory {
    
    /* -------------------------------------------------- Fields as Variable Elements -------------------------------------------------- */
    
    private static @Nonnull @NonNullableElements NonNullIterable<VariableElement> getFields(@Nonnull TypeElement typeElement) {
        return NullableIterable.ofNonNullElements(javax.lang.model.util.ElementFilter.fieldsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement)));
    }
    
    private static @Nonnull NonNullPredicate<VariableElement, String> variableElementNameMatcher = new NonNullPredicate<VariableElement, String>() {
        
        @Override public boolean apply(@Nonnull VariableElement object, @Nullable String name) {
            assert name != null;
            return object.getSimpleName().contentEquals(name);
        }
        
    };
    
    /* -------------------------------------------------- Parameter-based Field Information -------------------------------------------------- */
    
    private static @Nonnull NonNullPredicate<MethodInformation, String> getterForFieldMatcher = new NonNullPredicate<MethodInformation, String>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation object, @Nullable String name) {
            assert name != null;
            return object.isGetter() && object.getFieldName().equals(name);
        }
        
    };
    
    private static @Nonnull MethodInformation getGetterOf(@Nonnull String fieldName, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
        final @Nullable MethodInformation getter;
        try {
            getter = methodsOfType.find(getterForFieldMatcher, fieldName);
        } catch (UnexpectedResultException e) {
            throw UnexpectedFailureException.with("Multiple methods with the same name cannot exist.", e);
        }
        if (getter == null) {
            throw ConformityViolation.with("Representative fields must be accessible, either directly or through a getter, but the getter for the field '" + fieldName + "' was not found");
        }
        return getter;
    }
    
    private static @Nonnull NonNullPredicate<MethodInformation, String> setterForFieldMatcher = new NonNullPredicate<MethodInformation, String>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation object, @Nullable String name) {
            assert name != null;
            return object.isSetter() && object.getFieldName().equals(name);
        }
        
    };   
    
    private static @Nullable MethodInformation getSetterOf(@Nonnull String fieldName, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
        try {
            return methodsOfType.find(setterForFieldMatcher, fieldName);
        } catch (UnexpectedResultException e) {
            throw UnexpectedFailureException.with("Multiple methods with the same name cannot exist.", e);
        }
    }
    
    private static class AdditionalInformation {
        
        final @Nonnull @NonNullableElements NonNullIterable<VariableElement> fields;
        
        final @Nonnull DeclaredType containingType;
        
        final @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType;
        
        AdditionalInformation(@Nonnull @NonNullableElements NonNullIterable<VariableElement> fields, @Nonnull DeclaredType containingType, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
            this.fields = fields;
            this.containingType = containingType;
            this.methodsOfType = methodsOfType;
        }
        
    }
    
    private static NonNullToNonNullUnaryFunction<VariableElement, ParameterBasedFieldInformation, AdditionalInformation> parametersToFieldFunction = new NonNullToNonNullUnaryFunction<VariableElement, ParameterBasedFieldInformation, AdditionalInformation>() {
        
        @Override
        public @Nonnull ParameterBasedFieldInformation apply(@Nonnull VariableElement representingParameter, @Nullable AdditionalInformation additionalInformation) {
            assert additionalInformation != null;
            
            final @Nonnull @NonNullableElements NonNullIterable<VariableElement> fields = additionalInformation.fields;
            final @Nonnull DeclaredType containingType = additionalInformation.containingType;
            final @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType = additionalInformation.methodsOfType;
            
            final @Nonnull String parameterName = representingParameter.getSimpleName().toString();
            @Nullable VariableElement field;
            try {
                field = fields.find(variableElementNameMatcher, parameterName);
            } catch (UnexpectedResultException e) {
                // this should never happen.
                throw UnexpectedFailureException.with("Multiple fields with the same name cannot exist.", e);
            }
            if (field == null) {
                // throw ConformityException
                throw ConformityViolation.with("Representative fields must have the same name as their parameters, but the field for '" + parameterName + "' was not found");
            }
            if (!field.getModifiers().contains(Modifier.PRIVATE)) {
                return DirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, containingType, field);
            } else {
                return NonDirectlyAccessibleParameterBasedFieldInformation.of(representingParameter, containingType, getGetterOf(parameterName, methodsOfType), getSetterOf(parameterName, methodsOfType));
            }
            
        }
        
    };
    
    private static @Nonnull @NonNullableElements NonNullIterable<ParameterBasedFieldInformation> getParameterBasedFieldInformation(@Nonnull @NonNullableElements NonNullIterable<VariableElement> fields, @Nonnull NonNullIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
        
        return representingParameters.map(parametersToFieldFunction, new AdditionalInformation(fields, containingType, methodsOfType));
    }
    
    public static @Nonnull @NonNullableElements List<ParameterBasedFieldInformation> getParameterBasedFieldInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType, @Nonnull NonNullIterable<VariableElement> representingParameters, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
    
        final @Nonnull @NonNullableElements NonNullIterable<VariableElement> fields = getFields(typeElement);
        final @Nonnull @NonNullableElements NonNullIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation = getParameterBasedFieldInformation(fields, representingParameters, containingType, methodsOfType);
        return parameterBasedFieldInformation.toList();
    }
    
    /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
    
    private static final @Nonnull NonNullPredicate<MethodInformation, Object> abstractGetterMatcher = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return methodInformation.isAbstract() && methodInformation.isGetter();
        }
        
    };
    
    private static final @Nonnull NonNullPredicate<MethodInformation, Object> abstractSetterMatcher = new NonNullPredicate<MethodInformation, Object>() {
        
        @Override 
        public boolean apply(@Nonnull MethodInformation methodInformation, @Nullable Object none) {
            return methodInformation.isAbstract() && methodInformation.isSetter();
        }
        
    };
    
    private static final @Nonnull NonNullToNonNullUnaryFunction<MethodInformation, GeneratedFieldInformation, NonNullIterable<MethodInformation>> getterToFieldFunction = new NonNullToNonNullUnaryFunction<MethodInformation, GeneratedFieldInformation, NonNullIterable<MethodInformation>>() {
        
        @Override
        public @Nonnull GeneratedFieldInformation apply(@Nonnull MethodInformation element, @Nullable NonNullIterable<MethodInformation> methodsOfType) {
            assert methodsOfType != null;
            
            final @Nullable MethodInformation setter;
            try {
                setter = methodsOfType.find(abstractSetterMatcher);
            } catch (UnexpectedResultException e) {
                throw UnexpectedFailureException.with("Multiple fields with the same name cannot exist.", e);
            }
            return GeneratedFieldInformation.of(element.getContainingType(), element, setter);
        }
        
    };
    
    public static @Nonnull @NonNullableElements NonNullIterable<GeneratedFieldInformation> getGeneratedFieldInformation(@Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
        return methodsOfType.filter(abstractGetterMatcher).map(getterToFieldFunction, methodsOfType);
    }
    
    /* -------------------------------------------------- Directly Accessible Field Information -------------------------------------------------- */
    
    private static @Nonnull NonNullPredicate<VariableElement, Object> accessibleFieldMatcher = new NonNullPredicate<VariableElement, Object>() {
        
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
    
    private static @Nonnull @NonNullableElements NonNullIterable<DeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull @NonNullableElements NonNullIterable<VariableElement> fields, @Nonnull DeclaredType containingType) {
        return fields.filter(accessibleFieldMatcher).map(toDeclaredFieldFunction, containingType);
    }
    
    public static @Nonnull @NonNullableElements NullableIterable<DeclaredFieldInformation> getDirectlyAccessibleFieldInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
    
        final @Nonnull @NonNullableElements NonNullIterable<VariableElement> fields = getFields(typeElement);
        return getDirectlyAccessibleFieldInformation(fields, containingType);
    }
    
   
/*  TODO: remove this
    private static @Nonnull NonNullPredicate<VariableElement, NonNullIterable<VariableElement>> notRepresentingAndAccessibleFieldMatcher = new NonNullPredicate<VariableElement, NonNullIterable<VariableElement>>() {
        
        @Override
        public boolean apply(@Nonnull VariableElement field, @Nullable NonNullIterable<VariableElement> representingParameters) {
            assert representingParameters != null;
            try {
                return !field.getModifiers().contains(Modifier.PRIVATE) && (representingParameters.find(variableElementNameMatcher, field.getSimpleName().toString()) == null);
            } catch (UnexpectedResultException e) {
                throw UnexpectedFailureException.with("Multiple fields with the same name cannot exist.", e);
            }
        }
        
    };
    
    private static @Nonnull @NonNullableElements NonNullIterable<DeclaredFieldInformation> getDeclaredFieldInformation(@Nonnull @NonNullableElements NonNullIterable<VariableElement> fields, @Nonnull NonNullIterable<VariableElement> representingParameters, @Nonnull DeclaredType containingType) {
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
    public static @Nonnull @NonNullableElements List<FieldInformation> getFieldInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType, @Nonnull NonNullIterable<VariableElement> representingParameters, @Nonnull @NonNullableElements NonNullIterable<MethodInformation> methodsOfType) {
        final @Nonnull @NonNullableElements NonNullIterable<VariableElement> fields = getFields(typeElement);
        final @Nonnull @NonNullableElements NonNullIterable<ParameterBasedFieldInformation> parameterBasedFieldInformation = getParameterBasedFieldInformation(fields, representingParameters, containingType, methodsOfType);
        final @Nonnull @NonNullableElements NonNullIterable<GeneratedFieldInformation> generatedFieldInformation = getGeneratedFieldInformation(methodsOfType);
        
        final @Nonnull @NonNullableElements NonNullIterable<RepresentingFieldInformation> representingFields = NullableIterable.ofNonNullElements(parameterBasedFieldInformation).map(castToRepresentingFieldInformation).combine(generatedFieldInformation.map(castToRepresentingFieldInformation));
        
        final @Nonnull @NonNullableElements NonNullIterable<DeclaredFieldInformation> declaredFieldInformation = getDeclaredFieldInformation(fields, representingParameters, containingType);
    
        return representingFields.map(castToFieldInformation).combine(declaredFieldInformation.map(castToFieldInformation)).toList();
    }
    */
    
}
