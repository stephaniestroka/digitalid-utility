package net.digitalid.utility.generator.information.filter;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.information.method.MethodInformation;

/**
 * TODO: we already have a transformer interface in the conversion package. Maybe we should move that down/re-use it.
 */
public interface Transformer<F, T> {
    
    public @Nonnull T transformNonNullable(F from);
    
    public boolean canTransform(F from);
    
    public static class MethodInformationTransformer implements Transformer<Element, MethodInformation> {
        
        private @Nonnull DeclaredType containingType;
        
        private MethodInformationTransformer(@Nonnull DeclaredType containingType) {
            this.containingType = containingType;
        }
        
        @Override
        public @Nonnull MethodInformation transformNonNullable(@Nonnull Element from) {
            return MethodInformation.of((ExecutableElement) from, containingType);
        }
        
        @Override
        public boolean canTransform(@Nonnull Element from) {
            return from.getKind() == ElementKind.METHOD;
        }
    
        public static @Nonnull MethodInformationTransformer get(@Nonnull DeclaredType containingType) {
            return new MethodInformationTransformer(containingType);
        }
        
    }
    
    public static class VariableElementTransformer implements Transformer<Element, VariableElement> {
        
        @Override
        public @Nonnull VariableElement transformNonNullable(@Nonnull Element from) {
            return (VariableElement) from;
        }
        
        @Override
        public boolean canTransform(@Nonnull Element from) {
            return from.getKind() == ElementKind.FIELD || from.getKind() == ElementKind.PARAMETER;
        }
        
        public static @Nonnull VariableElementTransformer get() {
            return new VariableElementTransformer();
        }
        
    }
    
}
