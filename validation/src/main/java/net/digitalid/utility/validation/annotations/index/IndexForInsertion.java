package net.digitalid.utility.validation.annotations.index;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that an index is valid for inserting an element in a {@link Collection collection}.
 * Such an index is valid if it is greater or equal to zero and less than or equal to the number of elements (usually given by {@link Collection#size()}).
 */
@Documented
@TargetTypes(int.class)
@Retention(RetentionPolicy.RUNTIME)
@Generator(IndexForInsertion.Generator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface IndexForInsertion {
    
    @Stateless
    public static class Generator extends ContractGenerator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            super.checkUsage(element, annotationMirror);
            
            if (!ProcessingUtility.hasMethod(ProcessingUtility.getSurroundingType(element), "size", int.class)) {
                AnnotationLog.error("The annotation '@IndexForInsertion' may only be used in types with an 'int size()' method:", SourcePosition.of(element, annotationMirror));
            }
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("# >= 0 && # <= size()", "The # may not be negative or greater than the size of this collection but was $.", element);
        }
        
    }
    
}
