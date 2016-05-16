package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeVariable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedDerivedFieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedRepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.InformationFilter;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.getter.Derive;
import net.digitalid.utility.validation.annotations.size.MinSize;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass}, {@link BuilderGenerator builder} and {@link ConverterGenerator converter}.
 * 
 * @see InterfaceInformation
 * @see ClassInformation
 */
public abstract class TypeInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Returns an iterable of constructor information objects.
     */
    @Pure
    public abstract @Unmodifiable @Nonnull @MinSize(1) FiniteIterable<ConstructorInformation> getConstructors();
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the representing field information objects.
     */
    @Pure
    public abstract @Unmodifiable @Nonnull FiniteIterable<FieldInformation> getRepresentingFieldInformation();
    
    /* -------------------------------------------------- Accessible Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the accessible field information objects.
     */
    @Pure
    public abstract @Unmodifiable @Nonnull FiniteIterable<FieldInformation> getAccessibleFieldInformation();
    
    /* -------------------------------------------------- Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the field information objects.
     */
    @Pure
    public abstract @Unmodifiable @Nonnull FiniteIterable<FieldInformation> getFieldInformation();
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * Returns an iterable of the overridden method information objects.
     */
    @Pure
    public abstract @Unmodifiable @Nonnull FiniteIterable<MethodInformation> getOverriddenMethods();
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull TypeElement getElement() {
        return (TypeElement) super.getElement();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Unmodifiable @Nonnull DeclaredType getType() {
        return (DeclaredType) super.getType();
    }
    
    /**
     * Returns the type arguments of the represented declared type.
     */
    @Pure
    public @Unmodifiable @Nonnull FiniteIterable<@Nonnull TypeVariable> getTypeArguments() {
        return FiniteIterable.of(getType().getTypeArguments()).instanceOf(TypeVariable.class);
    }
    
    /* -------------------------------------------------- Subclass -------------------------------------------------- */
    
    /**
     * Returns the simple name of the generated subclass.
     */
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedSubclass() {
        return getName() + "Subclass";
    }
    
    /**
     * Returns the qualified name of the generated subclass.
     */
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedSubclass() {
        return getQualifiedPackageName() + "." + getSimpleNameOfGeneratedSubclass();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    /**
     * Returns the simple name of the generated builder.
     */
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedBuilder() {
        return getName() + "Builder";
    }
    
    /**
     * Returns the qualified name of the generated builder.
     */
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedBuilder() {
        return getQualifiedPackageName() + "." + getSimpleNameOfGeneratedBuilder();
    }
    
    /* -------------------------------------------------- Converter -------------------------------------------------- */
    
    /**
     * Returns the simple name of the generated converter.
     */
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedConverter() {
        return getName() + "Converter";
    }
    
    /**
     * Returns the qualified name of the generated converter.
     */
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedConverter() {
        return getQualifiedPackageName() + "." + getSimpleNameOfGeneratedConverter();
    }
    
    /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
    
    /**
     * An iterable of all generated field information objects.
     */
    public final @Unmodifiable @Nonnull FiniteIterable<GeneratedRepresentingFieldInformation> generatedRepresentingFieldInformation;
    
    /* -------------------------------------------------- Derived Field Information -------------------------------------------------- */
    
    /**
     * An iterable of all derived field information objects.
     */
    public final @Unmodifiable @Nonnull FiniteIterable<GeneratedDerivedFieldInformation> derivedFieldInformation;
    
    /* -------------------------------------------------- Abstract Getter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract getters.
     */
    public final @Unmodifiable @Nonnull Map<@Nonnull String, @Nonnull MethodInformation> abstractGetters;
    
    /* -------------------------------------------------- Abstract Setter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract setters.
     */
    public final @Unmodifiable @Nonnull Map<@Nonnull String, @Nonnull MethodInformation> abstractSetters;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new type information instance and initializes the abstract getters, abstract setters, generated and derived field information.
     */
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, typeElement.asType(), containingType);
        
        final @Unmodifiable @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformation = InformationFilter.getMethodInformation(typeElement, containingType);
        
        this.abstractGetters = methodInformation.filter((method) -> (method.isGetter() && method.isAbstract())).toMap(MethodInformation::getFieldName);
    
        this.abstractSetters = methodInformation.filter((method) -> (method.isSetter() && method.isAbstract())).toMap(MethodInformation::getFieldName);
        
        final @Nonnull List<@Nonnull Pair<@Nonnull MethodInformation, @Nullable MethodInformation>> gettersAndSetters = new ArrayList<>();
        
        for (Map.@Nonnull Entry<String, MethodInformation> indexedGetter : abstractGetters.entrySet()) {
            final @Nonnull MethodInformation getter = indexedGetter.getValue();
            final @Nullable MethodInformation setter = abstractSetters.get(indexedGetter.getKey());
            ProcessingLog.debugging("For field '" + indexedGetter.getKey() + "', adding getter: '" + getter + "' and setter '" + setter + "'.");
            gettersAndSetters.add(Pair.of(getter, setter));
        }
        
        this.generatedRepresentingFieldInformation = FiniteIterable.of(gettersAndSetters).filter(pair -> !pair.get0().hasAnnotation(Derive.class)).map((pair) -> (GeneratedRepresentingFieldInformation.of(pair.get0().getContainingType(), pair.get0(), pair.get1())));
    
        this.derivedFieldInformation = FiniteIterable.of(abstractGetters.entrySet()).filter(entry -> entry.getValue().hasAnnotation(Derive.class)).map(entry -> GeneratedDerivedFieldInformation.of(entry.getValue().getContainingType(), entry.getValue()));
    
        final @Nonnull FiniteIterable<MethodInformation> allRemainingAbstractMethods = methodInformation.filter((method) -> (method.isAbstract() && !method.isSetter() && !method.isGetter())).filter(MethodSignatureMatcher.of("equals", Object.class).and(MethodSignatureMatcher.of("toString")).and(MethodSignatureMatcher.of("hashCode")));
        
        if (allRemainingAbstractMethods.size() != 0) {
            ProcessingLog.debugging("Found $ abstract methods which cannot be generated", allRemainingAbstractMethods.size());
            for (MethodInformation remainingAbstractMethod : allRemainingAbstractMethods) {
                ProcessingLog.debugging("Remaining method $ cannot be generated", remainingAbstractMethod);
            }
            throw FailedClassGenerationException.with("Found abstract methods which cannot be generates: ", SourcePosition.of(typeElement), allRemainingAbstractMethods.join());
        }
    }
    
}
