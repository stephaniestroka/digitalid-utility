package net.digitalid.utility.processor.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.circumfixes.Brackets;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.contracts.Constraint;
import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.exceptions.CaseExceptionBuilder;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.processor.generator.annotations.NonWrittenRecipient;
import net.digitalid.utility.processor.generator.annotations.OnlyPossibleIn;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.contract.Contract;

import static net.digitalid.utility.processor.generator.JavaFileGenerator.CodeBlock.*;

/**
 * This class generates Java source files during annotation processing.
 */
@Mutable
public class JavaFileGenerator extends FileGenerator implements TypeImporter {
    
    /* -------------------------------------------------- Generated Class -------------------------------------------------- */
    
    private final @Nonnull String qualifiedClassName;
    
    /**
     * Returns the qualified name of the class that is generated.
     */
    @Pure
    public @Nonnull String getQualifiedClassName() {
        return qualifiedClassName;
    }
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return qualifiedClassName;
    }
    
    /* -------------------------------------------------- Source Class -------------------------------------------------- */
    
    private final @Nonnull TypeElement sourceClassElement;
    
    /**
     * Returns the type element of the class for which this source file is generated.
     */
    @Pure
    public @Nonnull TypeElement getSourceClassElement() {
        return sourceClassElement;
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    private final @Nonnull String packageName;
    
    @Pure
    public @Nonnull String getPackageName() {
        return packageName;
    }
    
    @Impure
    protected void printPackage(@Nonnull PrintWriter printWriter) {
        printWriter.println("package " + packageName + ";");
        printWriter.println();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected JavaFileGenerator(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
        this.qualifiedClassName = qualifiedClassName;
        this.sourceClassElement = sourceClassElement;
        this.packageName = qualifiedClassName.substring(0, qualifiedClassName.lastIndexOf('.'));
        ProcessingLog.verbose("Generating the Java source file for the class $.", getName());
    }
    
    /**
     * Returns a Java file generator that generates the class with the given qualified name for the given source class.
     */
    @Pure
    public static @Capturable @Nonnull JavaFileGenerator forClass(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
        return new JavaFileGenerator(qualifiedClassName, sourceClassElement);
    }
    
    /* -------------------------------------------------- Import Groups -------------------------------------------------- */
    
    /**
     * An import group collects all the import statements of a given prefix.
     */
    @Mutable
    private static class ImportGroup {
        
        final @Nonnull String prefix;
        
        ImportGroup(@Nonnull String prefix) {
            this.prefix = prefix;
        }
        
        final @Nonnull Set<@Nonnull String> imports = new HashSet<>();
        
    }
    
    /**
     * Stores the import groups which will be separated by an empty line in the desired order.
     */
    private final @Nonnull ImmutableList<@Nonnull ImportGroup> importGroups = ImmutableList.withElements(
        new ImportGroup("java."),
        new ImportGroup("javax."),
        new ImportGroup("net.digitalid.utility."),
        new ImportGroup("net.digitalid.database"),
        new ImportGroup("net.digitalid.core"),
        new ImportGroup("static "),
        new ImportGroup("")
    );
    
    @Impure
    protected void printImports(@Nonnull PrintWriter printWriter) {
        for (@Nonnull ImportGroup importGroup : importGroups) {
            if (!importGroup.imports.isEmpty()) {
                final @Nonnull @NonNullableElements ArrayList<String> imports = new ArrayList<>(importGroup.imports);
                Collections.sort(imports);
                for (@Nonnull String importString : imports) {
                    printWriter.println("import " + importString + ";");
                }
                printWriter.println();
            }
        }
    }
    
    /**
     * Adds an import statement for the class with the given qualified name.
     * 
     * @return whether the class with the given qualified name has not already been imported.
     * 
     * @require qualifiedName.contains(".") : "The name has to be qualified.";
     */
    @Impure
    @NonWrittenRecipient
    protected boolean addImport(@Nonnull String qualifiedName) {
        Require.that(qualifiedName.contains(".")).orThrow("The name $ has to be qualified.", qualifiedName);
        requireNotWritten();
        
        final @Nonnull String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
        if (packageName.equals("java.lang") || packageName.equals(getPackageName())) { return false; }
        
        for (@Nonnull ImportGroup importGroup : importGroups) {
            if (qualifiedName.startsWith(importGroup.prefix)) {
                return importGroup.imports.add(qualifiedName);
            }
        }
        
        // The empty prefix of the last import group should always be matched.
        throw CaseExceptionBuilder.withVariable("qualifiedClassName").withValue(qualifiedName).build();
    }
    
    /**
     * Adds a static import statement for the member with the given qualified name.
     * 
     * @return whether the member with the given qualified name has not already been imported.
     */
    @Impure
    @NonWrittenRecipient
    protected boolean addStaticImport(@Nonnull String qualifiedMemberName) {
        return addImport("static " + qualifiedMemberName);
    }
    
    /* -------------------------------------------------- Type Visitor -------------------------------------------------- */
    
    /**
     * This type visitor imports the given type mirror with its generic parameters if their simple names are not yet mapped to different types.
     */
    @Immutable
    public class ImportingTypeVisitor extends ProcessingUtility.QualifiedNameTypeVisitor {
        
        protected ImportingTypeVisitor() {}
        
        @Pure
        @Override
        public @Nonnull String visitDeclared(@Nonnull DeclaredType type, @Nullable Void none) {
            @Nonnull TypeElement typeElement = (TypeElement) type.asElement();
            @Nonnull String nestedTypeName = "";
            while (typeElement.getNestingKind() == NestingKind.MEMBER) {
                final @Nonnull Element enclosingElement = typeElement.getEnclosingElement();
                if (enclosingElement instanceof TypeElement) {
                    nestedTypeName = "." + typeElement.getSimpleName() + nestedTypeName;
                    typeElement = (TypeElement) enclosingElement;
                } else {
                    ProcessingLog.error("The enclosing element $ of the member type $ is not a type element.", enclosingElement, typeElement);
                    break;
                }
            }
            return ProcessingUtility.getAnnotationsAsString(type, JavaFileGenerator.this) + importIfPossible(typeElement) + nestedTypeName + FiniteIterable.of(type.getTypeArguments()).map(this::visit).join(Brackets.POINTY, "");
        }
        
        @Pure
        @Override
        public @Nonnull String visitWildcard(@Nonnull WildcardType type, @Nullable Void none) {
            if (type.getSuperBound() != null) { return "? super " + visit(type.getSuperBound()); }
            if (type.getExtendsBound() != null) { return "? extends " + visit(type.getExtendsBound()); }
            return "?";
        }
        
        @Pure
        @Override
        public @Nonnull String visitExecutable(@Nonnull ExecutableType type, @Nullable Void none) {
            return FiniteIterable.of(type.getTypeVariables()).map(JavaFileGenerator.this::importWithBounds).join("<", "> ", "") + visit(type.getReturnType());
        }
        
    }
    
    private final @Nonnull ImportingTypeVisitor importingTypeVisitor = new ImportingTypeVisitor();
    
    /* -------------------------------------------------- Type Importer -------------------------------------------------- */
    
    /**
     * Maps the non-qualified names that are currently imported to their fully qualified names.
     */
    private final @Nonnull @NonNullableElements Map<String, String> nameSpace = new HashMap<>();
    
    @Impure
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull String qualifiedTypeName) {
        final @Nonnull String simpleTypeName = Strings.substringFromLast(qualifiedTypeName, '.');
        if (!nameSpace.containsKey(simpleTypeName)) {
            addImport(qualifiedTypeName);
            nameSpace.put(simpleTypeName, qualifiedTypeName);
            return simpleTypeName;
        } else if (qualifiedTypeName.equals(nameSpace.get(simpleTypeName))) {
            return simpleTypeName;
        } else {
            return qualifiedTypeName;
        }
    }
    
    @Impure
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull Class<?> type) {
        return importIfPossible(type.getCanonicalName());
    }
    
    @Impure
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull Element typeElement) {
        Require.that(typeElement.getKind().isClass() || typeElement.getKind().isInterface()).orThrow("The element $ has to be a type.", typeElement);
        
        return importIfPossible(((QualifiedNameable) typeElement).getQualifiedName().toString());
    }
    
    @Impure
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull TypeMirror typeMirror) {
        return ProcessingUtility.getAnnotationsAsString(typeMirror, this) + importingTypeVisitor.visit(typeMirror);
    }
    
    @Impure
    @Override
    @NonWrittenRecipient
    public @Nonnull String importStaticallyIfPossible(@Nonnull String qualifiedMemberName) {
        final @Nonnull String simpleMemberName = Strings.substringFromLast(qualifiedMemberName, '.');
        if (!nameSpace.containsKey(simpleMemberName)) {
            addStaticImport(qualifiedMemberName);
            nameSpace.put(simpleMemberName, qualifiedMemberName);
            return simpleMemberName;
        } else if (qualifiedMemberName.equals(nameSpace.get(simpleMemberName))) {
            return simpleMemberName;
        } else {
            return qualifiedMemberName;
        }
    }
    
    /* -------------------------------------------------- Special Imports -------------------------------------------------- */
    
    /**
     * Imports the given type variable with its bounds if their simple names are not yet mapped to different types.
     * 
     * @return the given type variable with the simple names of its bounds if they could be imported without a naming conflict and the qualified name of its bounds otherwise.
     */
    @Impure
    @NonWrittenRecipient
    public @Nonnull String importWithBounds(@Nonnull TypeVariable typeVariable) {
        if (typeVariable.getLowerBound().getKind() != TypeKind.NULL) { return typeVariable.toString() + " super " + importIfPossible(typeVariable.getLowerBound()); }
        if (!typeVariable.getUpperBound().toString().equals("java.lang.Object")) { return typeVariable.toString() + " extends " + importIfPossible(typeVariable.getUpperBound()); }
        return typeVariable.toString();
    }
    
    /**
     * Imports the given type arguments with their bounds if their simple names are not yet mapped to different types.
     * 
     * @return the given type arguments with the simple names of their bounds if they could be imported without a naming conflict and the qualified name of their bounds otherwise.
     */
    @Impure
    @NonWrittenRecipient
    public @Nonnull String importWithBounds(@Nonnull FiniteIterable<@Nonnull TypeVariable> typeArguments) {
        return typeArguments.map(this::importWithBounds).join(Brackets.POINTY, "");
    }
    
    /**
     * Returns the declaration of the parameters of the given executable type and element in round brackets.
     * The type is required to declare the parameters in subclasses correctly (see {@link Types#asMemberOf(javax.lang.model.type.DeclaredType, javax.lang.model.element.Element)}).
     * 
     * @require executableType.getParameterTypes().size() == executableElement.getParameters().size() : "The executable type and the executable element have to have the same number of parameters.";
     */
    @Impure
    @NonWrittenRecipient
    public @Nonnull String declareParameters(@Nonnull ExecutableType executableType, @Nonnull ExecutableElement executableElement) {
        Require.that(executableType.getParameterTypes().size() == executableElement.getParameters().size()).orThrow("The executable type and the executable element have to have the same number of parameters.");
        
        return FiniteIterable.of(executableType.getParameterTypes()).zipShortest(FiniteIterable.of(executableElement.getParameters())).map(pair -> importIfPossible(pair.get0()) + " " + pair.get1().getSimpleName()).join(Brackets.ROUND);
    }
    
    /* -------------------------------------------------- Code Blocks -------------------------------------------------- */
    
    /**
     * This class enumerates the various code blocks which can be nested in each other to generate Java code.
     */
    @Immutable
    public static enum CodeBlock {
        NONE(""), JAVADOC(" * "), CLASS(false), INTERFACE(false), ANONYMOUS_CLASS(false), BLOCK(true), CONSTRUCTOR(true), METHOD(true), IF(true), ELSE(true), ELSE_IF(true), FOR_LOOP(true), WHILE_LOOP(true), TRY(true), CATCH(true), FINALLY(true);
        
        private final @Nonnull String indentation;
        
        /**
         * Returns the indentation that is used for all blocks that are nested within this code block.
         */
        @Pure
        public @Nonnull String getIndentation() {
            return indentation;
        }
        
        private final boolean allowsStatements;
        
        /**
         * Returns whether this code block allows to {@link #addStatement(java.lang.String) add statements}.
         */
        @Pure
        public boolean allowsStatements() {
            return allowsStatements;
        }
        
        private CodeBlock(@Nonnull String indentation, boolean allowsStatements) {
            this.indentation = indentation;
            this.allowsStatements = allowsStatements;
        }
        
        private CodeBlock(@Nonnull String indentation) {
            this(indentation, false);
        }
        
        private CodeBlock(boolean allowsStatements) {
            this("    ", allowsStatements);
        }
        
    }
    
    /**
     * Stores the stack of code blocks that are currently nested in each other.
     */
    private final @Nonnull @NonNullableElements Stack<CodeBlock> codeBlocksStack = new Stack<>();
    
    /**
     * Returns the code block which is currently on top of the stack or {@link CodeBlock#NONE} if the stack is empty.
     */
    @Pure
    public @Nonnull CodeBlock getCurrentCodeBlock() {
        return codeBlocksStack.empty() ? NONE : codeBlocksStack.peek();
    }
    
    /**
     * Returns the code block stack as a string for debugging.
     */
    @Pure
    public @Nonnull String getCodeBlockStackAsString() {
        return FiniteIterable.of(codeBlocksStack).join();
    }
    
    /**
     * Requires that the {@link #getCurrentCodeBlock() current code block} is one of the given code blocks.
     * An empty array of code blocks is used to indicate that any block that {@link CodeBlock#allowsStatements() allows statements} is fine.
     * 
     * @throws net.digitalid.utility.contracts.exceptions.PreconditionViolationException if this is not the case.
     */
    @Pure
    @NonWrittenRecipient
    protected void requireCurrentCodeBlock(@Nonnull @NonNullableElements CodeBlock... codeBlocks) {
        requireNotWritten();
        
        if (codeBlocks.length == 0) {
            Require.that(getCurrentCodeBlock().allowsStatements()).orThrow("The current code block (" + getCurrentCodeBlock() + ") should allow statements but does not.");
        } else {
            Require.that(Arrays.asList(codeBlocks).contains(getCurrentCodeBlock())).orThrow("The current code block should have been one of " + Arrays.toString(codeBlocks) + " but was " + getCurrentCodeBlock());
        }
    }
    
    /* -------------------------------------------------- Source Code -------------------------------------------------- */
    
    /**
     * Stores the source code that is added after the import statements.
     */
    private final @Nonnull StringBuilder sourceCode = new StringBuilder();
    
    @Impure
    protected void printSourceCode(@Nonnull PrintWriter printWriter) {
        printWriter.append(sourceCode);
    }
    
    /**
     * Adds the given code to the source code on a new line with the right indentation.
     */
    @Impure
    @NonWrittenRecipient
    protected void addCodeLineWithIndentation(@Nonnull String code) {
        requireNotWritten();
        
        for (@Nonnull CodeBlock codeBlock : codeBlocksStack) {
            sourceCode.append(codeBlock.getIndentation());
        }
        sourceCode.append(code).append(System.lineSeparator());
    }
    
    /**
     * Begins the given code block with the given declaration and a brace.
     */
    @Impure
    @NonWrittenRecipient
    protected void beginBlock(@Nonnull String declaration, @Nonnull CodeBlock codeBlock, @Nonnull @NonNullableElements CodeBlock... requiredCurrentCodeBlocks) {
        requireCurrentCodeBlock(requiredCurrentCodeBlocks);
        
        addCodeLineWithIndentation(declaration + (declaration.isEmpty() ? "" : " ") + "{");
        codeBlocksStack.push(codeBlock);
    }
    
    /**
     * Begins the given code block with the given keyword and condition in parentheses before a brace.
     */
    @Impure
    @NonWrittenRecipient
    protected void beginBlock(@Nonnull String keyword, @Nonnull String condition, @Nonnull CodeBlock codeBlock, @Nonnull @NonNullableElements CodeBlock... requiredCurrentCodeBlocks) {
        beginBlock(keyword + " (" + condition + ")", codeBlock, requiredCurrentCodeBlocks);
    }
    
    /**
     * Ends the current code block by adding the closing brace on a new line with less indentation.
     */
    @Impure
    @NonWrittenRecipient
    protected void endBlock(@Nonnull @NonNullableElements CodeBlock... requiredCurrentCodeBlocks) {
        requireCurrentCodeBlock(requiredCurrentCodeBlocks);
        
        codeBlocksStack.pop();
        addCodeLineWithIndentation("}");
    }
    
    /**
     * Ends the current code block and begins the given code block with the given declaration and a brace.
     */
    @Impure
    @NonWrittenRecipient
    protected void endAndBeginBlock(@Nonnull @NonEmpty String declaration, @Nonnull CodeBlock codeBlock, @Nonnull @NonNullableElements CodeBlock... requiredCurrentCodeBlocks) {
        requireCurrentCodeBlock(requiredCurrentCodeBlocks);
        
        codeBlocksStack.pop();
        addCodeLineWithIndentation("} " + declaration + " {");
        codeBlocksStack.push(codeBlock);
    }
    
    /**
     * Ends the current code block and begins the given code block with the given keyword and condition in parentheses before a brace.
     */
    @Impure
    @NonWrittenRecipient
    protected void endAndBeginBlock(@Nonnull String keyword, @Nonnull String condition, @Nonnull CodeBlock codeBlock, @Nonnull @NonNullableElements CodeBlock... requiredCurrentCodeBlocks) {
        endAndBeginBlock(keyword + " (" + condition + ")", codeBlock, requiredCurrentCodeBlocks);
    }
    
    /* -------------------------------------------------- Empty Line -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    public void addEmptyLine() {
        addCodeLineWithIndentation("");
    }
    
    /* -------------------------------------------------- Comment -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    public void addComment(@Nonnull String comment) {
        addCodeLineWithIndentation("// " + comment);
    }
    
    /* -------------------------------------------------- Section -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(CLASS)
    public void addSection(@Nonnull String name) {
        requireCurrentCodeBlock(CLASS);
        
        addCodeLineWithIndentation("/* -------------------------------------------------- " + name + " -------------------------------------------------- */");
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Javadoc -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginJavadoc() {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE);
        
        addCodeLineWithIndentation("/**");
        codeBlocksStack.push(JAVADOC);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(JAVADOC)
    public void addJavadoc(@Nonnull String javadoc) {
        requireCurrentCodeBlock(JAVADOC);
        
        addCodeLineWithIndentation(javadoc);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(JAVADOC)
    public void endJavadoc() {
        requireCurrentCodeBlock(JAVADOC);
        
        codeBlocksStack.pop();
        addCodeLineWithIndentation(" */");
    }
    
    /* -------------------------------------------------- Annotation -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE, ANONYMOUS_CLASS})
    public void addAnnotation(@Nonnull Class<? extends Annotation> annotationType, @Nullable String optionalValues, @Nonnull @NonNullableElements Object... optionalArguments) {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE, ANONYMOUS_CLASS);
        
        addCodeLineWithIndentation("@" + importIfPossible(annotationType) + (optionalValues != null ? "(" + Strings.format(optionalValues, Quotes.CODE, optionalArguments) + ")" : ""));
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE, ANONYMOUS_CLASS})
    public void addAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        addAnnotation(annotationType, null);
    }
    
    /* -------------------------------------------------- Class or Interface -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    private void beginClassOrInterface(@Nonnull String declaration, @Nonnull CodeBlock classOrInterface) {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE);
        
        if (getCurrentCodeBlock() == NONE) {
            addAnnotation(SuppressWarnings.class, "$", "null");
            StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
            final @Nonnull String generator = caller.getClassName();
            final @Nonnull String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
            addAnnotation(Generated.class, "value = $, date = $", generator, date);
        }
        
        beginBlock(declaration, classOrInterface, NONE, CLASS, INTERFACE);
        addEmptyLine();
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, INTERFACE})
    public void endClassOrInterface(@Nonnull CodeBlock classOrInterface) {
        endBlock(classOrInterface);
        if (getCurrentCodeBlock() != NONE) {
            addEmptyLine();
        }
    }
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * Begins an interface block in a Java source file.
     */
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginInterface(@Nonnull String declaration) {
        beginClassOrInterface(declaration, INTERFACE);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({INTERFACE})
    public void endInterface() {
        endClassOrInterface(INTERFACE);
    }
    
    /* -------------------------------------------------- Class -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginClass(@Nonnull String declaration) {
        beginClassOrInterface(declaration, CLASS);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS})
    public void endClass() {
        endClassOrInterface(CLASS);
    }
    
    /* -------------------------------------------------- Anonymous Class -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, METHOD})
    public void beginAnonymousClass(@Nonnull String declaration) {
        beginBlock(declaration, ANONYMOUS_CLASS, CLASS, METHOD);
        addEmptyLine();
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({ANONYMOUS_CLASS})
    public void endAnonymousClass() {
        endClassOrInterface(ANONYMOUS_CLASS);
    }
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void addField(@Nonnull String declaration) {
        requireCurrentCodeBlock(CLASS, ANONYMOUS_CLASS);
        
        addCodeLineWithIndentation(declaration + ";");
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Block -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginBlock(boolean withStaticModifier) {
        beginBlock(withStaticModifier ? "static" : "", BLOCK, CLASS, ANONYMOUS_CLASS);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(BLOCK)
    public void endBlock() {
        endBlock(BLOCK);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginConstructor(@Nonnull String declaration) {
        beginBlock(declaration, CONSTRUCTOR, CLASS, ANONYMOUS_CLASS);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(CONSTRUCTOR)
    public void endConstructor() {
        endBlock(CONSTRUCTOR);
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Method Declaration -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, INTERFACE})
    public void addMethodDeclaration(@Nonnull String declaration) {
        requireCurrentCodeBlock(CLASS, INTERFACE);
        
        addCodeLineWithIndentation(declaration + ";");
    }
    
    /* -------------------------------------------------- Method -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginMethod(@Nonnull String declaration) {
        beginBlock(declaration, METHOD, CLASS, ANONYMOUS_CLASS);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(METHOD)
    public void endMethod() {
        endBlock(METHOD);
        addEmptyLine();
    }
    
    /* -------------------------------------------------- If -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginIf(@Nonnull String condition) {
        beginBlock("if", condition, IF);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(IF)
    public void endIf() {
        endBlock(IF);
    }
    
    /* -------------------------------------------------- Else -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({IF, ELSE_IF})
    public void endIfBeginElse() {
        endAndBeginBlock("else", ELSE, IF, ELSE_IF);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(ELSE)
    public void endElse() {
        endBlock(ELSE);
    }
    
    /* -------------------------------------------------- Else If -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({IF, ELSE_IF})
    public void endIfBeginElseIf(@Nonnull String condition) {
        endAndBeginBlock("else if", condition, ELSE_IF, IF, ELSE_IF);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(ELSE_IF)
    public void endElseIf() {
        endBlock(ELSE_IF);
    }
    
    /* -------------------------------------------------- For Loop -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginForLoop(@Nonnull String condition) {
        beginBlock("for", condition, FOR_LOOP);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(FOR_LOOP)
    public void endForLoop() {
        endBlock(FOR_LOOP);
    }
    
    /* -------------------------------------------------- While Loop -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginWhileLoop(@Nonnull String condition) {
        beginBlock("while", condition, WHILE_LOOP);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(WHILE_LOOP)
    public void endWhileLoop() {
        endBlock(WHILE_LOOP);
    }
    
    /* -------------------------------------------------- Try & Catch -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginTry() {
        beginBlock("try", TRY);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({TRY, CATCH})
    public void endTryOrCatchBeginCatch(@Nonnull String exceptionName, @Nonnull @NonNullableElements @NonEmpty FiniteIterable<String> exceptionTypes) {
        endAndBeginBlock("catch", "@" + importIfPossible(Nonnull.class) + " " + exceptionTypes.join(" | ") + " " + exceptionName, CATCH, TRY, CATCH);
    }
    
    @Impure
    @SafeVarargs
    @NonWrittenRecipient
    @OnlyPossibleIn({TRY, CATCH})
    public final void endTryOrCatchBeginCatch(@Nonnull @NonNullableElements @NonEmpty Class<? extends Throwable>... exceptionTypes) {
        endTryOrCatchBeginCatch("exception", FiniteIterable.of(exceptionTypes).map(this::importIfPossible));
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({TRY, CATCH})
    public void endTryOrCatchBeginCatch(@Nonnull @NonNullableElements @NonEmpty List<? extends TypeMirror> exceptionTypes) {
        endTryOrCatchBeginCatch("exception", FiniteIterable.of(exceptionTypes).map(this::importIfPossible));
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(CATCH)
    public void endCatch() {
        endBlock(CATCH);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn({TRY, CATCH})
    public void endTryOrCatchBeginFinally() {
        endAndBeginBlock("finally", FINALLY, TRY, CATCH);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn(FINALLY)
    public void endFinally() {
        endBlock(FINALLY);
    }
    
    /* -------------------------------------------------- Statement -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addStatement(@Nonnull String statement) {
        requireCurrentCodeBlock();
        
        addCodeLineWithIndentation(statement + ";");
    }
    
    /* -------------------------------------------------- Contracts -------------------------------------------------- */
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    protected void addContract(@Nonnull Class<? extends Constraint> contractType, @Nullable Contract generatedContract) {
        if (generatedContract != null) {
            addStatement(importIfPossible(contractType) + ".that(" + generatedContract.getCondition() + ").orThrow(" + Quotes.inDouble(generatedContract.getMessage()) + generatedContract.getArguments().join(", ", "", "") + ")");
        }
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addPrecondition(@Nullable Contract generatedContract) {
        addContract(Require.class, generatedContract);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addPostcondition(@Nullable Contract generatedContract) {
        addContract(Ensure.class, generatedContract);
    }
    
    @Impure
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addInvariant(@Nullable Contract generatedContract) {
        addContract(Validate.class, generatedContract);
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    @Impure
    @Override
    @NonWrittenRecipient
    protected void writeOnce() throws IOException {
        requireCurrentCodeBlock(NONE);
        
        final @Nonnull JavaFileObject javaFileObject = StaticProcessingEnvironment.environment.get().getFiler().createSourceFile(qualifiedClassName, sourceClassElement);
        try (@Nonnull Writer writer = javaFileObject.openWriter(); @Nonnull PrintWriter printWriter = new PrintWriter(writer)) {
            printPackage(printWriter);
            printImports(printWriter);
            printSourceCode(printWriter);
        }
    }
    
}
