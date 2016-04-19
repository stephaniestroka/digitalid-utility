package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.functional.iterables.InfiniteIterable;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.query.MethodSignatureMatcher;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
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
    public abstract @Nonnull FiniteIterable<ConstructorInformation> getConstructors();
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the representing field information objects.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<RepresentingFieldInformation> getRepresentingFieldInformation();
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * Returns an iterable of the overridden method information objects.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<MethodInformation> getOverriddenMethods();
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull TypeElement getElement() {
        return (TypeElement) super.getElement();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull DeclaredType getType() {
        return (DeclaredType) super.getType();
    }
    
    /**
     * Returns the type arguments of the represented declared type.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull TypeVariable> getTypeArguments() {
        return FiniteIterable.of(getType().getTypeArguments()).instanceOf(TypeVariable.class);
    }
    
    /* -------------------------------------------------- Subclass -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedSubclass() {
        return getName() + "Subclass";
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedSubclass() {
        return getPackageName() + "." + getSimpleNameOfGeneratedSubclass();
    }
    
    /* -------------------------------------------------- Builder -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedBuilder() {
        return getName() + "Builder";
    }
    
    @Pure
    public @Nonnull String getQualifiedNameOfGeneratedBuilder() {
        return getPackageName() + "." + getSimpleNameOfGeneratedBuilder();
    }
    
    /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
    
    /**
     * An iterable of all generated field information objects.
     */
    public final @Nonnull FiniteIterable<GeneratedFieldInformation> generatedFieldInformation;
    
    /* -------------------------------------------------- Abstract Getter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract getters.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractGetters;
    
    /* -------------------------------------------------- Abstract Setter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract setters.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractSetters;
    
    /* -------------------------------------------------- Method Information -------------------------------------------------- */
    
    /**
     * The method indexes method information objects with the field name of the method.
     */
    protected @Nonnull @NonNullableElements Map<String, MethodInformation> indexMethodInformation(@Nonnull FiniteIterable<MethodInformation> iterable) {
        // TODO: The following statement can (and should) replace this whole method: iterable.toMap(method -> method.getFieldName());
        final @Nonnull @NonNullableElements Map<String, MethodInformation> indexedMethods = new LinkedHashMap<>();
        for (@Nonnull MethodInformation method : iterable) {
            indexedMethods.put(method.getFieldName(), method);
        }
        return indexedMethods;
    }
    
    protected @Nonnull @NonNullableElements <F extends FieldInformation> Map<String, F> indexFieldInformation(@Nonnull FiniteIterable<F> iterable) {
        // TODO: This method as well (see above)!
        final @Nonnull @NonNullableElements Map<String, F> indexedFields = new LinkedHashMap<>();
        for (@Nonnull F method : iterable) {
            indexedFields.put(method.getName(), method);
        }
        return indexedFields;
    }
    
    /**
     * Returns an iterable of method information objects for a given type element and declared type.
     */
    protected @Nonnull FiniteIterable<@Nonnull MethodInformation> getMethodInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        final @Nonnull List<ExecutableElement> allMethods = ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
        return FiniteIterable.of(allMethods).zipShortest(InfiniteIterable.repeat(containingType)).map((pair) -> (MethodInformation.of(pair.get0(), pair.get1())));
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new type information instance.
     */
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, typeElement.asType(), containingType);
        
        final @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformation = getMethodInformation(typeElement, containingType);
        
        this.abstractGetters = indexMethodInformation(methodInformation.filter((method) -> (method.isGetter() && method.isAbstract())));
    
        this.abstractSetters = indexMethodInformation(methodInformation.filter((method) -> (method.isSetter() && method.isAbstract())));
        
        final @Nonnull List<@Nonnull Pair<@Nonnull MethodInformation, @Nullable MethodInformation>> gettersAndSetters = new ArrayList<>();
        
        for (Map.@Nonnull Entry<String, MethodInformation> indexedGetter : abstractGetters.entrySet()) {
            final @Nonnull MethodInformation getter = indexedGetter.getValue();
            final @Nullable MethodInformation setter = abstractSetters.get(indexedGetter.getKey());
            ProcessingLog.debugging("For field '" + indexedGetter.getKey() + "', adding getter: '" + getter + "' and setter '" + setter + "'.");
            gettersAndSetters.add(Pair.of(getter, setter));
        }
        
        this.generatedFieldInformation = FiniteIterable.of(gettersAndSetters).map((pair) -> (GeneratedFieldInformation.of(pair.get0().getContainingType(), pair.get0(), pair.get1())));
        
        final @Nonnull FiniteIterable<MethodInformation> allRemainingAbstractMethods = methodInformation.filter((method) -> (method.isAbstract() && !method.isSetter() && !method.isGetter())).filter(MethodSignatureMatcher.of("equals", Object.class).and(MethodSignatureMatcher.of("toString")).and(MethodSignatureMatcher.of("hashCode")));
        
        if (allRemainingAbstractMethods.size() != 0) {
            ProcessingLog.debugging("Found abstract methods which cannot be generated: ", allRemainingAbstractMethods.size());
            for (MethodInformation remainingAbstractMethod : allRemainingAbstractMethods) {
                ProcessingLog.debugging("Remaining method $ cannot be generated", remainingAbstractMethod);
            }
            throw FailedClassGenerationException.with("Found abstract methods which cannot be generates: ", SourcePosition.of(typeElement), allRemainingAbstractMethods.join());
        }
        
    }
    
}
