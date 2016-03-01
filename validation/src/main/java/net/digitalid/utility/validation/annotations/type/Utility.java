package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.generator.TypeValidator;
import net.digitalid.utility.validation.processing.ProcessingUtility;

/**
 * This annotation indicates that the annotated class has only static fields and methods.
 * 
 * @see Stateless
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Validator(Utility.Validator.class)
public @interface Utility {
    
    @Stateless
    public static class Validator extends TypeValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            for (@Nonnull Element member : StaticProcessingEnvironment.getElementUtils().getAllMembers((TypeElement) element)) {
                if (ProcessingUtility.isDeclaredInDigitalIDLibrary(member) && !member.getModifiers().contains(Modifier.STATIC)) {
                    ProcessingLog.error("The utility type $ may only have static fields and methods.", SourcePosition.of(member), element);
                }
            }
        }
        
    }
    
}
