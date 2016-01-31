package net.digitalid.utility.processor;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessor;
import net.digitalid.utility.logging.processing.SourcePosition;

/**
 * Description.
 */
@SupportedAnnotationTypes("javax.annotation.processing.SupportedAnnotationTypes")
public class ProcessorProcessor extends CustomProcessor {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    @Override
    public boolean processFirstRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (final Element rootElement : roundEnvironment.getRootElements()) {
            AnnotationLog.information("Root element: " + rootElement.getKind(), SourcePosition.of(rootElement));
            if (rootElement.getKind() == ElementKind.CLASS) {
                AnnotationLog.information("Subtype of " + ((TypeElement) rootElement).getSuperclass().toString());
                final List<? extends Element> members = AnnotationProcessor.environment.get().getElementUtils().getAllMembers((TypeElement) rootElement);
                for (final Element member : members) {
                    AnnotationLog.debugging("Member element: " + member.getModifiers() + " " + member.getKind(), SourcePosition.of(member));
                }
                if (rootElement.getSimpleName().toString().equals("TestClass")) {
                    AnnotationLog.verbose("TestClass found!");
//                    TestAnnotation annotation = rootElement.getAnnotation(TestAnnotation.class);
//                    try {
//                        Class<?> value = annotation.value();
//                        try {
//                            value.newInstance();
//                        } catch (InstantiationException | IllegalAccessException ex) {
//                            Log.error("Problem!", ex);
//                        }
//                    } catch (MirroredTypeException exception) {
//                        DeclaredType declaredType = (DeclaredType) exception.getTypeMirror();
//                        TypeElement typeElement = (TypeElement) declaredType.asElement();
//                        AnnotationLog.information(typeElement.getQualifiedName());
//                        try {
//                            Class.forName(typeElement.getQualifiedName().toString()).newInstance();
//                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//                            Log.error("Problem!", ex);
//                        }
//                    }
//                    
//                    List<? extends AnnotationMirror> annotationMirrors = rootElement.getAnnotationMirrors();
//                    AnnotationLog.verbose("Annotation mirrors: " + annotationMirrors);
//                    AnnotationMirror mirror = annotationMirrors.get(0);
//                    AnnotationLog.verbose("Values of the first annotation mirror: " + mirror.getElementValues());
                }
            }
        }
        for (final Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(SupportedAnnotationTypes.class)) {
            AnnotationLog.warning("The processor processor was triggered for:" , SourcePosition.of(annotatedElement));
        }
        return false;
    }
    
}
