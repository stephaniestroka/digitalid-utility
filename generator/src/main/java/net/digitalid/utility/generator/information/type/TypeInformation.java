package net.digitalid.utility.generator.information.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeVariable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.state.Unmodifiable;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.annotations.generators.GenerateBuilder;
import net.digitalid.utility.generator.annotations.generators.GenerateSubclass;
import net.digitalid.utility.generator.exceptions.FailedClassGenerationException;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.ElementInformation;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedDerivedFieldInformation;
import net.digitalid.utility.generator.information.field.GeneratedRepresentingFieldInformation;
import net.digitalid.utility.generator.information.filter.InformationFilter;
import net.digitalid.utility.generator.information.filter.MethodSignatureMatcher;
import net.digitalid.utility.generator.information.method.ConstructorInformation;
import net.digitalid.utility.generator.information.method.ExecutableInformation;
import net.digitalid.utility.generator.information.method.MethodInformation;
import net.digitalid.utility.generator.information.variable.VariableElementInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.generation.Derive;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.size.MinSize;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.processing.AnnotationHandlerUtility;

/**
 * This type collects the relevant information about a type for generating a {@link SubclassGenerator subclass}, {@link BuilderGenerator builder} and {@link ConverterGenerator converter}.
 * 
 * @see InterfaceInformation
 * @see ClassInformation
 */
@Immutable
@TODO(task = "The type validators are never loaded, which means their usage is never checked.", date = "2016-05-16", author = Author.KASPAR_ETTER)
public abstract class TypeInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Recover Method or Constructor -------------------------------------------------- */
    
    public @Nullable ExecutableInformation getRecoverConstructorOrMethod() {
        if (getRecoverMethod() != null) {
            return getRecoverMethod();
        } else {
            @Nullable ConstructorInformation recoverConstructor = null;
            if (getConstructors().size() == 1) {
                recoverConstructor = getConstructors().getFirst();
            } else if (getConstructors().size() > 1) {
                for (@Nonnull ConstructorInformation constructorInformation : getConstructors()) {
                    if (constructorInformation.hasAnnotation(Recover.class)) {
                        if (recoverConstructor != null) {
                            throw FailedClassGenerationException.with("Only one recover constructor allowed, but multiple @Recover annotations found in type $", SourcePosition.of(getElement()), getName());
                        }
                        recoverConstructor = constructorInformation;
                    }
                }
                if (recoverConstructor == null) {
                    throw FailedClassGenerationException.with("Found multiple constructors, but none is annotated with @Recover in type $", SourcePosition.of(getElement()), getName());
                }
            }
            return recoverConstructor;
        }
    }
    
    /* -------------------------------------------------- Recover Method -------------------------------------------------- */
    
    /**
     * Returns the recover method, if one exists.
     */
    @Pure
    public abstract @Nullable MethodInformation getRecoverMethod();
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Returns an iterable of constructor information objects.
     */
    @Pure
    public abstract @Nonnull @MinSize(0) FiniteIterable<ConstructorInformation> getConstructors();
    
    /* -------------------------------------------------- Constructor Parameters -------------------------------------------------- */
    
    /**
     * Return parameters required for the construction of the class.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<VariableElementInformation> getConstructorParameters();
    
    /* -------------------------------------------------- Representing Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the representing field information objects.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<FieldInformation> getRepresentingFieldInformation();
    
    /* -------------------------------------------------- Accessible Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the accessible field information objects.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<FieldInformation> getAccessibleFieldInformation();
    
    /* -------------------------------------------------- Field Information -------------------------------------------------- */
    
    /**
     * Returns an iterable of the field information objects.
     */
    @Pure
    public abstract @Nonnull FiniteIterable<FieldInformation> getFieldInformation();
    
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
    public final @Nonnull FiniteIterable<GeneratedRepresentingFieldInformation> generatedRepresentingFieldInformation;
    
    /* -------------------------------------------------- Derived Field Information -------------------------------------------------- */
    
    /**
     * An iterable of all derived field information objects.
     */
    public final @Nonnull FiniteIterable<GeneratedDerivedFieldInformation> derivedFieldInformation;
    
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
    
    /* -------------------------------------------------- Abstract Methods -------------------------------------------------- */
    
    /**
     * Returns an iterable of methods that must be implemented.
     */
    public final @Unmodifiable @Nonnull FiniteIterable<@Nonnull MethodInformation> generatedMethods;
    
    /* -------------------------------------------------- Initialization Marker -------------------------------------------------- */
    
    /**
     * Returns true, iff the object is fully initialized. This means, implementing classes should only return true at the end of the object construction. To avoid errors, this should only be implemented and returned by classes that are final.
     */
    @Pure
    public abstract boolean isInitialized();
    
    /* -------------------------------------------------- Instance Code -------------------------------------------------- */
    
    /**
     * Returns a string that can be used in generated code to instantiate this type.
     */
    public @Nonnull String getInstantiationCode(boolean useBuilderIfAvailable, boolean useSubclassIfAvailable, boolean useRecoverMethodIfAvailable) {
        // The expected result is:
        // - the call to the generated builder, if useBuilderIfAvailable flag is set to true and the @GenerateBuilder annotation is available for the type, 
        // - the call to the recover method, if useRecoverMethodIfAvailable is true and a @Recover annotated method is available, or
        // - the call to the generated subclass constructor, if useSubclassIfAvailable is true and the @GenerateSubclass annotation is available for the type,
        // - the call to the constructor.
        if (useBuilderIfAvailable && hasAnnotation(GenerateBuilder.class)) {
            final @Nonnull StringBuilder assignedParameters = new StringBuilder();
            for (@Nonnull VariableElementInformation constructorParameter : getConstructorParameters()) {
                assignedParameters.append(".with").append(Strings.capitalizeFirstLetters(constructorParameter.getName())).append("(").append(constructorParameter.getName()).append(")");
            }
            return "return " + getSimpleNameOfGeneratedBuilder() + assignedParameters.append(".build()").toString();
        } else if (useRecoverMethodIfAvailable && getRecoverMethod() != null) {
            final @Nonnull MethodInformation recoverMethod = getRecoverMethod();
            return "return " + getName() + "." + recoverMethod.getName() + getConstructorParameters().map(ElementInformation::getName).join(Brackets.ROUND);
        } else if (getElement().getKind() == ElementKind.ENUM) {
            // maybe move to subclass, because that's a special case of EnumInformation
            return "return " + getName() + ".valueOf(value)";
        } else {
            final @Nonnull String nameOfConstructor;
            if (useSubclassIfAvailable && hasAnnotation(GenerateSubclass.class)) {
                nameOfConstructor = getSimpleNameOfGeneratedSubclass();
            } else {
                nameOfConstructor = getName();
            }
            return "return new " + nameOfConstructor + getConstructorParameters().map(ElementInformation::getName).join(Brackets.ROUND);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new type information instance and initializes the abstract getters, abstract setters, generated and derived field information.
     */
    protected TypeInformation(@Nonnull TypeElement typeElement, @Nonnull DeclaredType containingType) {
        super(typeElement, typeElement.asType(), containingType);
        
        // Make the usage checks of the type annotations:
        AnnotationHandlerUtility.getTypeValidators(typeElement);
        
        // TODO: Enforce that every type has an @Immutable, @Stateless, @Utility and the like annotation?
        
        final @Nonnull FiniteIterable<@Nonnull MethodInformation> methodInformation = InformationFilter.getMethodInformation(typeElement, containingType, this);
        
        this.abstractGetters = methodInformation.filter((method) -> method.isGetter() && method.isAbstract()).toMap(MethodInformation::getFieldName);
    
        this.abstractSetters = methodInformation.filter((method) -> method.isSetter() && method.isAbstract()).toMap(MethodInformation::getFieldName);
        
        this.generatedMethods = methodInformation.filter((method) -> method.isAbstract() && method.canBeImplemented());
        
        final @Nonnull List<@Nonnull Pair<@Nonnull MethodInformation, @Nullable MethodInformation>> gettersAndSetters = new ArrayList<>();
        
        for (Map.@Nonnull Entry<String, MethodInformation> indexedGetter : abstractGetters.entrySet()) {
            final @Nonnull MethodInformation getter = indexedGetter.getValue();
            final @Nullable MethodInformation setter = abstractSetters.get(indexedGetter.getKey());
            ProcessingLog.debugging("For field '" + indexedGetter.getKey() + "', adding getter: '" + getter + "' and setter '" + setter + "'.");
            gettersAndSetters.add(Pair.of(getter, setter));
        }
        
        this.generatedRepresentingFieldInformation = FiniteIterable.of(gettersAndSetters).filter(pair -> !pair.get0().hasAnnotation(Derive.class)).map((pair) -> (GeneratedRepresentingFieldInformation.of(pair.get0().getContainingType(), pair.get0(), pair.get1())));
    
        this.derivedFieldInformation = FiniteIterable.of(abstractGetters.entrySet()).filter(entry -> entry.getValue().hasAnnotation(Derive.class)).map(entry -> GeneratedDerivedFieldInformation.of(entry.getValue().getContainingType(), entry.getValue()));
    
        final @Nonnull FiniteIterable<MethodInformation> allRemainingAbstractMethods = methodInformation.filter((method) -> (method.isAbstract() && !method.isSetter() && !method.isGetter() && (!hasAnnotation(GenerateSubclass.class) || !method.canBeImplemented()))).filter(MethodSignatureMatcher.of("equals", Object.class).and(MethodSignatureMatcher.of("toString")).and(MethodSignatureMatcher.of("hashCode")));
        
        if (allRemainingAbstractMethods.size() != 0) {
            ProcessingLog.debugging("Found $ abstract methods which cannot be generated", allRemainingAbstractMethods.size());
            for (MethodInformation remainingAbstractMethod : allRemainingAbstractMethods) {
                ProcessingLog.debugging("Remaining method $ cannot be generated", remainingAbstractMethod);
            }
            throw FailedClassGenerationException.with("Found abstract methods which cannot be generates: ", SourcePosition.of(typeElement), allRemainingAbstractMethods.join());
        }
    }
    
}
