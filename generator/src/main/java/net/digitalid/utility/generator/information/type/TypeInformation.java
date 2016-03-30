package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.functional.function.unary.NonNullToNonNullUnaryFunction;
import net.digitalid.utility.functional.function.unary.NonNullToNullableUnaryFunction;
import net.digitalid.utility.functional.iterable.old.NonNullableIterable;
import net.digitalid.utility.functional.iterable.old.NullableIterable;
import net.digitalid.utility.functional.predicate.NonNullablePredicate;
import net.digitalid.utility.generator.BuilderGenerator;
import net.digitalid.utility.generator.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.GeneratedFieldInformation;
import net.digitalid.utility.generator.information.field.RepresentingFieldInformation;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.tuples.NonNullablePair;
import net.digitalid.utility.tuples.NullablePair;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.tuples.annotations.Pure;

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
    public abstract @Nonnull NonNullableIterable<ConstructorInformation> getConstructors();
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the representing field information objects.
     */
    @Pure
    public abstract @Nonnull NonNullableIterable<RepresentingFieldInformation> getRepresentingFieldInformation();
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    /**
     * Returns an iterable of the overridden method information objects.
     */
    @Pure
    public abstract @Nonnull NonNullableIterable<MethodInformation> getOverriddenMethods();
    
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
    
    /* -------------------------------------------------- Subclass -------------------------------------------------- */
    
    @Pure
    public @Nonnull String getSimpleNameOfGeneratedSubclass() {
        return "Generated" + getName();
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
    
    /* -------------------------------------------------- Generatable -------------------------------------------------- */
    
    /**
     * This flag indicates whether a subclass can be generated for this type.
     */
    private final boolean generatable;
    
    /**
     * Returns whether a subclass can be generated for this type.
     */
    @Pure
    public boolean isGeneratable() {
        return generatable;
    }
    
    /* -------------------------------------------------- Generated Field Information -------------------------------------------------- */
    
    /**
     * An iterable of all generated field information objects.
     */
    public final @Nonnull NonNullableIterable<GeneratedFieldInformation> generatedFieldInformation;
    
    private static final @Nonnull NonNullToNonNullUnaryFunction<NullablePair<MethodInformation, MethodInformation>, GeneratedFieldInformation> getterToFieldFunction = new NonNullToNonNullUnaryFunction<NullablePair<MethodInformation, MethodInformation>, GeneratedFieldInformation>() {
        
        @Override
        public @Nonnull GeneratedFieldInformation apply(@Nonnull NullablePair<MethodInformation, MethodInformation> triplet) {
            final @Nullable MethodInformation getter = triplet.get0();
            final @Nullable MethodInformation setter = triplet.get1();
            assert getter != null : "The getter method should not be null.";
            return GeneratedFieldInformation.of(getter.getContainingType(), getter, setter);
        }
        
    };
    
    /* -------------------------------------------------- Abstract Method -------------------------------------------------- */
    
    /**
     * The predicate checks whether a given method information is an abstract getter.
     */
    private final static @Nonnull NonNullablePredicate<MethodInformation> abstractMethodPredicate = new NonNullablePredicate<MethodInformation>() {
    
        @Override public boolean apply(@Nonnull MethodInformation methodInformation) {
            return methodInformation.isAbstract();
        }
        
    };
    
    /**
     * The function nulls method information objects that are getters or setters.
     */
    private final static @Nonnull NonNullToNullableUnaryFunction<MethodInformation, MethodInformation> removeAbstractGettersAndSetters = new NonNullToNullableUnaryFunction<MethodInformation, MethodInformation>() {
        
        @Override
        public @Nullable MethodInformation apply(@Nonnull MethodInformation element) {
            if (element.isGetter() || element.isSetter()) {
                return null;
            } else {
                return element;
            }
        }
        
    };
    
    /* -------------------------------------------------- Abstract Getter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract getters.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractGetters;
    
    /**
     * The predicate checks whether a given method information is an abstract getter.
     */
    private final static @Nonnull NonNullablePredicate<MethodInformation> abstractGetterPredicate = new NonNullablePredicate<MethodInformation>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation methodInformation) {
            return methodInformation.isGetter() && methodInformation.isAbstract();
        }
        
    };
    
    /* -------------------------------------------------- Abstract Setter -------------------------------------------------- */
    
    /**
     * Returns a map of indexed abstract setters.
     */
    public final @Nonnull @NonNullableElements Map<String, MethodInformation> abstractSetters;
    
    /**
     * The predicate checks whether a given method information is an abstract setter.
     */
    private final static @Nonnull NonNullablePredicate<MethodInformation> abstractSetterPredicate = new NonNullablePredicate<MethodInformation>() {
        
        @Override
        public boolean apply(@Nonnull MethodInformation object) {
            return object.isSetter() && object.isAbstract();
        }
        
    };
    
    /* -------------------------------------------------- Method Information -------------------------------------------------- */
    
    /**
     * The method indexes method information objects with the field name of the method.
     */
    protected @Nonnull @NonNullableElements Map<String, MethodInformation> indexMethodInformation(@Nonnull NonNullableIterable<MethodInformation> iterable) {
        final @Nonnull @NonNullableElements Map<String, MethodInformation> indexedMethods = new HashMap<>();
        for (@Nonnull MethodInformation method : iterable) {
            indexedMethods.put(method.getFieldName(), method);
        }
        return indexedMethods;
    }
    
    /**
     * The function transforms executable elements to method information elements.
     */
    protected static final @Nonnull NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, MethodInformation> toMethodInformation = new NonNullToNonNullUnaryFunction<NonNullablePair<ExecutableElement, DeclaredType>, MethodInformation>() {
        
        @Override
        public @Nonnull MethodInformation apply(@Nonnull NonNullablePair<ExecutableElement, DeclaredType> pair) {
            final @Nonnull ExecutableElement element = pair.get0();
            final @Nonnull DeclaredType containingType = pair.get1();
            return MethodInformation.of(element, containingType);
        }
        
    };
    
    /**
     * Returns an iterable of method information objects for a given type element and declared type.
     */
    protected @Nonnull @NonNullableElements NonNullableIterable<MethodInformation> getMethodInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        final @Nonnull List<ExecutableElement> allMethods = ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers(typeElement));
        return NullableIterable.ofNonNullableElements(allMethods).zipNonNull(NonNullableIterable.ofNonNullableElement(containingType)).map(toMethodInformation);
    }
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * This function receives a subtype of representing field information and returns a representing field information object.
     */
    protected static @Nonnull NonNullToNonNullUnaryFunction<? super RepresentingFieldInformation, RepresentingFieldInformation> toRepresentingFieldInformation = new NonNullToNonNullUnaryFunction<RepresentingFieldInformation, RepresentingFieldInformation>() {
        
        @Override 
        public @Nonnull RepresentingFieldInformation apply(@Nonnull RepresentingFieldInformation element) {
            return element;
        }
        
    };
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new type information instance.
     */
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, typeElement.asType(), containingType);
        
        boolean generatable = true;
        
        final @Nonnull NonNullableIterable<MethodInformation> methodInformation = getMethodInformation(typeElement, containingType);
        
        this.abstractGetters = indexMethodInformation(methodInformation.filter(abstractGetterPredicate));
    
        this.abstractSetters = indexMethodInformation(methodInformation.filter(abstractSetterPredicate));
        
        final @Nonnull @NonNullableElements List<NullablePair<MethodInformation, MethodInformation>> gettersAndSetters = new ArrayList<>();
        
        for (@Nonnull Map.Entry<String, MethodInformation> indexedGetter : abstractGetters.entrySet()) {
            final @Nonnull MethodInformation getter = indexedGetter.getValue();
            final @Nullable MethodInformation setter = abstractSetters.get(indexedGetter.getKey());
            ProcessingLog.debugging("For field '" + indexedGetter.getKey() + "', adding getter: '" + getter + "' and setter '" + setter + "'.");
            gettersAndSetters.add(Pair.withNullable(getter, setter));
        }
        
        this.generatedFieldInformation = NonNullableIterable.ofNonNullableElements(gettersAndSetters).map(getterToFieldFunction);
        
        final @Nonnull NonNullableIterable<MethodInformation> allRemainingAbstractMethods = methodInformation.filter(abstractMethodPredicate).map(removeAbstractGettersAndSetters).filterNonNull();
        
        if (allRemainingAbstractMethods.size() != 0) {
            ProcessingLog.debugging("Found abstract methods which cannot be generated: ", allRemainingAbstractMethods.size());
            for (MethodInformation remainingAbstractMethod : allRemainingAbstractMethods) {
                ProcessingLog.debugging("Remaining method $ cannot be generated", remainingAbstractMethod);
            }
            generatable = false;
        }
        
        this.generatable = generatable;
    }
    
}
