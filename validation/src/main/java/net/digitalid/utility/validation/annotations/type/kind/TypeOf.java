package net.digitalid.utility.validation.annotations.type.kind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.generators.ElementKindContractGenerator;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

import static javax.lang.model.element.ElementKind.*;

/**
 * This annotation indicates that a class or element is of one of the given kinds.
 * 
 * @see ElementKind
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(TypeOf.Generator.class)
@TargetTypes({Class.class, Element.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface TypeOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the kinds one of which the annotated type or element has to be.
     */
    @Nonnull ElementKind[] value();
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        private static final @Nonnull @NonNullableElements List<ElementKind> TYPE_KINDS = Arrays.asList(new ElementKind[]{CLASS, INTERFACE, ENUM, ANNOTATION_TYPE});
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            super.checkUsage(element, annotationMirror);
            
            if (ProcessingUtility.isAssignable(element, Class.class) && !TYPE_KINDS.containsAll(Arrays.asList(element.getAnnotation(TypeOf.class).value()))) {
                ProcessingLog.error("In case of classes, the annotation '@TypeKind' may only be used with CLASS, INTERFACE, ENUM and ANNOTATION_TYPE:", SourcePosition.of(element, annotationMirror));
            }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            final @Nonnull StringBuilder condition = new StringBuilder("# == null");
            for (@Nonnull ElementKind kind : element.getAnnotation(TypeOf.class).value()) {
                condition.append(" || ").append(ElementKindContractGenerator.getCondition(element, kind, typeImporter));
            }
            return Contract.with(condition.toString(), "The # has to be null or one of the specified kinds but was $.", element);
        }
        
    }
    
}
