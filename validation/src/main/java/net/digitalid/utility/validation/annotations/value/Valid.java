package net.digitalid.utility.validation.annotations.value;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.functional.interfaces.Predicate;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;


/**
 * This annotation indicates that the annotated value is valid.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Valid.Validator.class)
public @interface Valid {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the (optional) suffix that is appended to the referenced {@link Value#isValid(java.lang.Object) isValid method} name.
     */
    @Nonnull String value() default "";
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * This interface encapsulates one or more values which are validated by the given validator.
     */
    @Functional
    public static interface Value<V> {
        
        /**
         * Returns the validator which validates the encapsulated value(s).
         */
        @Pure
        @Default("object -> true")
        public @Nonnull Predicate<? super V> getValueValidator();
        
        /**
         * Returns whether the given value is valid.
         */
        @Pure
        public default boolean isValid(V value) {
            return getValueValidator().evaluate(value);
        }
        
    }
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator implements ValueAnnotationValidator {
        
        /**
         * Returns the value of the given annotation mirror as a suffix for the method name.
         */
        @Pure
        protected static @Nonnull String getSuffix(@Nonnull AnnotationMirror annotationMirror) {
            // The following line is only necessary because the IncorrectUsage testing annotation passes its own annotation mirror.
            if (ProcessingUtility.getSimpleName(annotationMirror).equals("IncorrectUsage")) { return ""; }
            final @Nullable AnnotationValue annotationValue = ProcessingUtility.getAnnotationValue(annotationMirror);
            final @Nullable String string = ProcessingUtility.getString(annotationValue);
            return string == null ? "" : Strings.uppercaseFirstCharacter(string);
        }
        
        /**
         * Returns whether the given surrounding type has a (static) method (with the given suffix) to check the validity of an instance of the given element type.
         */
        @Pure
        protected static boolean hasMethodToCheckValidity(@Nonnull DeclaredType declaredType, @Nonnull TypeMirror elementType, boolean onlyStatic, @Nonnull String suffix) {
            for (@Nonnull ExecutableElement inheritedMethod : ProcessingUtility.getAllMethods((TypeElement) declaredType.asElement())) {
                if (inheritedMethod.getThrownTypes().isEmpty()
                        && inheritedMethod.getSimpleName().contentEquals("isValid" + suffix)
                        && !inheritedMethod.getModifiers().contains(Modifier.PRIVATE)
                        && (!onlyStatic || inheritedMethod.getModifiers().contains(Modifier.STATIC))
                        && inheritedMethod.getReturnType().getKind() == TypeKind.BOOLEAN) {
                    final @Nonnull ExecutableType methodType = (ExecutableType) StaticProcessingEnvironment.getTypeUtils().asMemberOf(declaredType, inheritedMethod);
                    if (methodType.getParameterTypes().size() == 1 && StaticProcessingEnvironment.getTypeUtils().isAssignable(elementType, methodType.getParameterTypes().get(0))) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            final @Nonnull String suffix = getSuffix(annotationMirror);
            final @Nonnull TypeMirror elementType = ProcessingUtility.getType(element);
            final @Nonnull Element enclosingElement = element.getEnclosingElement();
            final boolean inConstructor = enclosingElement.getKind() == ElementKind.CONSTRUCTOR;
            if (hasMethodToCheckValidity((DeclaredType) ProcessingUtility.getSurroundingType(element).asType(), elementType, inConstructor, suffix)) { return; }
            if (enclosingElement.getKind() == ElementKind.METHOD) {
                final @Nonnull TypeMirror typeOfFirstParameter = ((ExecutableElement) enclosingElement).getParameters().get(0).asType();
                if (typeOfFirstParameter.getKind() == TypeKind.DECLARED && hasMethodToCheckValidity((DeclaredType) typeOfFirstParameter, elementType, false, suffix)) { return; }
            }
            final @Nonnull String annotationValue = suffix.isEmpty() ? "" : Brackets.inRound(Quotes.inDouble(suffix));
            if (inConstructor) { errorLogger.log("The annotation '@Valid" + annotationValue + "' may only be used on constructor parameters of types that have a 'public static boolean isValid" + suffix + "(value)' method for the corresponding type.", SourcePosition.of(element, annotationMirror)); }
            else { errorLogger.log("The annotation '@Valid" + annotationValue + "' may only be used in types that have a corresponding 'public (static) boolean isValid" + suffix + "(value)' method or on method parameters where the first method parameter has such a method.", SourcePosition.of(element, annotationMirror)); }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            final @Nonnull String suffix = getSuffix(annotationMirror);
            final @Nonnull TypeMirror elementType = ProcessingUtility.getType(element);
            final @Nonnull Element enclosingElement = element.getEnclosingElement();
            final boolean primitive = elementType.getKind().isPrimitive() || element.getAnnotation(Nonnull.class) == null && element.getAnnotation(Nullable.class) == null;
            final boolean local = hasMethodToCheckValidity((DeclaredType) ProcessingUtility.getSurroundingType(element).asType(), elementType, enclosingElement.getKind() == ElementKind.CONSTRUCTOR, suffix);
            return Contract.with((primitive ? "" : "# == null || ") + (local ? "" : ((ExecutableElement) enclosingElement).getParameters().get(0).getSimpleName() + ".") + "isValid" + suffix + "(#)", "The # has to be " + (primitive ? "" : "null or ") + "valid but was $.", element);
        }
        
    }
    
}
