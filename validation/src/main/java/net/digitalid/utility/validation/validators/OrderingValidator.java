package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;

/**
 * This class implements common methods for all ordering-related validators.
 * 
 * @see net.digitalid.utility.validation.annotations.order
 */
@Stateless
public abstract class OrderingValidator extends IterableValidator {
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        super.checkUsage(element, annotationMirror);
        
        final @Nonnull TypeMirror type = ProcessingUtility.getType(element);
        if (type.getKind() == TypeKind.ARRAY) {
            final @Nonnull ArrayType arrayType = (ArrayType) type;
            if (!ProcessingUtility.isAssignable(arrayType.getComponentType(), Comparable.class)) {
                ProcessingLog.error("The annotation $ may only be used on arrays whose component type is comparable:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
            }
        } else if (type.getKind() == TypeKind.DECLARED) {
            final @Nonnull DeclaredType declaredType = (DeclaredType) type;
            if (declaredType.getTypeArguments().size() != 1 || !ProcessingUtility.isAssignable(declaredType.getTypeArguments().get(0), Comparable.class)) {
                ProcessingLog.error("The annotation $ may only be used on iterables whose component type is comparable:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
            }
        } else {
            ProcessingLog.error("The annotation $ may only be used on arrays or declared types:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
        }
    }
    
    /* -------------------------------------------------- Validation -------------------------------------------------- */
    
    /**
     * Returns whether the elements in the given iterable are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in the iterable are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static boolean validate(@Nullable Iterable<?> iterable, boolean strictly, boolean ascending) {
        if (iterable == null) { return true; }
        @Nullable Object lastElement = null;
        for (final @Nullable Object element : iterable) {
            if (element == null) { continue; }
            if (lastElement != null) {
                if (element instanceof Comparable<?>) {
                    if (((Comparable<Object>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
            }
            lastElement = element;
        }
        return true;
    }
    
    /**
     * Returns whether the elements in the given array are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in the array are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static boolean validate(@Nullable Object[] array, boolean strictly, boolean ascending) {
        if (array == null) { return true; }
        @Nullable Object lastElement = null;
        for (final @Nullable Object element : array) {
            if (element == null) { continue; }
            if (lastElement != null) {
                if (element instanceof Comparable<?>) {
                    if (((Comparable<Object>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
            }
            lastElement = element;
        }
        return true;
    }
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
    @Pure
    protected abstract boolean isStrictly();
    
    @Pure
    protected abstract boolean isAscending();
    
    /* -------------------------------------------------- Contract Generation -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
        return Contract.with(typeImporter.importIfPossible(OrderingValidator.class) + ".validate(#, " + isStrictly() + ", " + isAscending() + ")", "The # has to be ordered.", element);
    }
    
}
