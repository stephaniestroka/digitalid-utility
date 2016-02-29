package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.ElementFilter;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.type.filter.FieldNameExtractor;
import net.digitalid.utility.generator.information.type.filter.GetterMatcher;
import net.digitalid.utility.generator.information.type.filter.SetterMatcher;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This type collects the relevant information about an interface for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class InterfaceInformation extends TypeInformation {
    
    /**
     * The class information builder collects information relevant for creating a class information object.
     */
    private static class InterfaceInformationBuilder implements TypeInformationBuilder {
        
        /* -------------------------------------------------- Final Fields -------------------------------------------------- */
        
        /**
         * The type element.
         */
        private final @Nonnull TypeElement typeElement;
        
        /**
         * The containing type.
         */
        private final @Nonnull DeclaredType containingType;
        
        /* -------------------------------------------------- Constructor -------------------------------------------------- */
        
        protected InterfaceInformationBuilder(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
            this.typeElement = typeElement;
            this.containingType = containingType;
        }
        
        /* -------------------------------------------------- Generatable -------------------------------------------------- */
        
        public boolean isGeneratable() {
            return true;
        }
    
        /* -------------------------------------------------- Abstract Getters -------------------------------------------------- */
        
        @Nonnull @Override public Map<String, MethodInformation> getAbstractGetters() {
            return ElementFilter.filterMethodInformations(typeElement, GetterMatcher.get(), FieldNameExtractor.get());
        }
    
        @Nonnull @Override public Map<String, MethodInformation> getAbstractSetters() {
            return ElementFilter.filterMethodInformations(typeElement, SetterMatcher.get(), FieldNameExtractor.get());
        }
        
        /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
        
        private @Nullable @NonNullableElements List<GeneratedFieldInformation> generatedFieldInformations;
        
        public @Nonnull @NonNullableElements List<GeneratedFieldInformation> getGeneratedFieldsInformation() {
            if (generatedFieldInformations == null) {
                 generatedFieldInformations = new ArrayList<>(getAbstractGetters().size());
                for (@Nonnull Map.Entry<String, MethodInformation> abstractGetter : getAbstractGetters().entrySet()) {
                    generatedFieldInformations.add(GeneratedFieldInformation.of(containingType, abstractGetter.getValue(), getAbstractSetters().get(abstractGetter.getKey())));
                }
            }
            return generatedFieldInformations;
        }
        
        /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
        
        /**
         * Combines and returns the parameter-based fields and the generated fields of the type.
         */
        public @Nonnull @NonNullableElements List<RepresentingFieldInformation> getRepresentingFieldInformation() {
            final @Nonnull @NonNullableElements List<RepresentingFieldInformation> representingFieldInformations = new ArrayList<>(getGeneratedFieldsInformation().size());
            representingFieldInformations.addAll(getGeneratedFieldsInformation());
            return representingFieldInformations;
        }
        
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InterfaceInformation(@Nonnull TypeElement element, @Nonnull DeclaredType containingType, @Nonnull InterfaceInformationBuilder builder) {
        super(element, containingType, builder);
    }
    
    /**
     * Returns a class information object of the given type typeElement and containing type.
     */
    public static @Nonnull InterfaceInformation of(@Nonnull TypeElement element, @Nonnull DeclaredType containingType) {
        final @Nonnull InterfaceInformationBuilder builder = new InterfaceInformationBuilder(element, containingType);
        
        return new InterfaceInformation(element, containingType, builder);
    }
    
}
