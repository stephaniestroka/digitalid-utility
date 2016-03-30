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
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import net.digitalid.utility.contracts.Constraint;
import net.digitalid.utility.contracts.Ensure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.Validate;
import net.digitalid.utility.exceptions.UnexpectedValueException;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.processor.generator.annotations.NonWrittenRecipient;
import net.digitalid.utility.processor.generator.annotations.OnlyPossibleIn;
import net.digitalid.utility.string.FormatString;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;

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
    
    protected void printPackage(@Nonnull PrintWriter printWriter) {
        printWriter.println("package " + packageName + ";");
        printWriter.println();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected JavaFileGenerator(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
        this.qualifiedClassName = qualifiedClassName;
        this.sourceClassElement = sourceClassElement;
        this.packageName = qualifiedClassName.substring(0, qualifiedClassName.lastIndexOf('.'));
        ProcessingLog.verbose("Generating the class " + this);
    }
    
    /**
     * Returns a Java file generator that generates the class with the given qualified name for the given source class.
     */
    @Pure
    public static @Nonnull JavaFileGenerator forClass(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
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
        
        final @Nonnull @NonNullableElements Set<String> imports = new HashSet<>();
        
    }
    
    /**
     * Stores the import groups which will be separated by an empty line in the desired order.
     */
    private final @Nonnull @NonNullableElements ImportGroup[] importGroups = {
        new ImportGroup("java."),
        new ImportGroup("javax."),
        new ImportGroup("net.digitalid.utility."),
        new ImportGroup("net.digitalid.database"),
        new ImportGroup("net.digitalid.core"),
        new ImportGroup("static "),
        new ImportGroup("")
    };
    
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
        throw UnexpectedValueException.with("qualifiedClassName", qualifiedName);
    }
    
    /**
     * Adds a static import statement for the member with the given qualified name.
     * 
     * @return whether the member with the given qualified name has not already been imported.
     */
    @NonWrittenRecipient
    protected boolean addStaticImport(@Nonnull String qualifiedMemberName) {
        return addImport("static " + qualifiedMemberName);
    }
    
    /* -------------------------------------------------- Type Visitor -------------------------------------------------- */
    
    // TODO: Implement the type visitor here as an inner class.
    
    /* -------------------------------------------------- Type Importer -------------------------------------------------- */
    
    /**
     * Maps the non-qualified names that are currently imported to their fully qualified names.
     */
    private final @Nonnull @NonNullableElements Map<String, String> nameSpace = new HashMap<>();
    
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull String qualifiedTypeName) {
        final @Nonnull String simpleTypeName = qualifiedTypeName.substring(qualifiedTypeName.lastIndexOf('.') + 1);
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
    
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull Class<?> type) {
        return importIfPossible(type.getCanonicalName());
    }
    
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull Element typeElement) {
        Require.that(typeElement.getKind().isClass() || typeElement.getKind().isInterface()).orThrow("The element $ has to be a type.", typeElement);
        
        return importIfPossible(((QualifiedNameable) typeElement).getQualifiedName().toString());
    }
    
    public final @Nonnull ImportingTypeVisitor importingTypeVisitor = ImportingTypeVisitor.with(this);
    
    @Override
    @NonWrittenRecipient
    public @Nonnull String importIfPossible(@Nonnull TypeMirror typeMirror) {
        return importingTypeVisitor.visit(typeMirror).toString();
    }
    
    @Override
    @NonWrittenRecipient
    public @Nonnull String importStaticallyIfPossible(@Nonnull String qualifiedMemberName) {
        final @Nonnull String simpleMemberName = qualifiedMemberName.substring(qualifiedMemberName.lastIndexOf('.') + 1);
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
    
    /* -------------------------------------------------- Code Blocks -------------------------------------------------- */
    
    /**
     * This class enumerates the various code blocks which can be nested in each other to generate Java code.
     */
    @Immutable
    public static enum CodeBlock {
        NONE(""), JAVADOC(" * "), CLASS(false), INTERFACE(false), ANONYMOUS_CLASS(false), BLOCK(true), CONSTRUCTOR(true), METHOD(true), IF(true), ELSE(true), ELSE_IF(true), FOR_LOOP(true), WHILE_LOOP(true), TRY(true), TRY_CATCH(true), TRY_FINALLY(true);
        
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
    public @Nonnull CodeBlock getCurrentCodeBlock() {
        return codeBlocksStack.empty() ? CodeBlock.NONE : codeBlocksStack.peek();
    }
    
    /**
     * Requires that the {@link #getCurrentCodeBlock() current code block} is one of the given code blocks.
     * An empty array of code blocks is used to indicate that any block that {@link CodeBlock#allowsStatements() allows statements} is fine.
     * 
     * @throws net.digitalid.utility.contracts.exceptions.PreconditionViolationException if this is not the case.
     */
    @Pure
    @NonWrittenRecipient
    protected void requireCurrentCodeBlock(@Nonnull CodeBlock... codeBlocks) {
        requireNotWritten();
        
        if (codeBlocks.length == 0) {
            // TODO: It is a bit scruffy that we're assuming that the caller is "addStatement", but well, at least it's documented.
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
    
    protected void printSourceCode(@Nonnull PrintWriter printWriter) {
        printWriter.append(sourceCode);
    }
    
    /**
     * Adds the given code to the source code on a new line with the right indentation.
     */
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
    @NonWrittenRecipient
    protected void beginBlock(@Nonnull String declaration, @Nonnull CodeBlock codeBlock, @Nonnull CodeBlock... requiredCurrentCodeBlocks) {
        requireCurrentCodeBlock(requiredCurrentCodeBlocks);
        
        addCodeLineWithIndentation(declaration + (declaration.isEmpty() ? "" : " ") + "{");
        codeBlocksStack.push(codeBlock);
    }
    
    /**
     * Begins the given code block with the given keyword and condition in parentheses before a brace.
     */
    @NonWrittenRecipient
    protected void beginBlock(@Nonnull String keyword, @Nonnull String condition, @Nonnull CodeBlock codeBlock, @Nonnull CodeBlock... requiredCurrentCodeBlocks) {
        beginBlock(keyword + " (" + condition + ")", codeBlock, requiredCurrentCodeBlocks);
    }
    
    /**
     * Ends the current code block by adding the closing brace on a new line with less indentation.
     */
    @NonWrittenRecipient
    protected void endBlock(@Nonnull CodeBlock... requiredCurrentCodeBlocks) {
        requireCurrentCodeBlock(requiredCurrentCodeBlocks);
        
        codeBlocksStack.pop();
        addCodeLineWithIndentation("}");
    }
    
    /* -------------------------------------------------- Empty Line -------------------------------------------------- */
    
    @NonWrittenRecipient
    public void addEmptyLine() {
        addCodeLineWithIndentation("");
    }
    
    /* -------------------------------------------------- Comment -------------------------------------------------- */
    
    @NonWrittenRecipient
    public void addComment(@Nonnull String comment) {
        addCodeLineWithIndentation("// " + comment);
    }
    
    /* -------------------------------------------------- Section -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn(CLASS)
    public void addSection(@Nonnull String name) {
        requireCurrentCodeBlock(CLASS);
        
        addCodeLineWithIndentation("/* -------------------------------------------------- " + name + " -------------------------------------------------- */");
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Javadoc -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginJavadoc() {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE);
        
        addCodeLineWithIndentation("/**");
        codeBlocksStack.push(JAVADOC);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(JAVADOC)
    public void addJavadoc(@Nonnull String javadoc) {
        requireCurrentCodeBlock(JAVADOC);
        
        addCodeLineWithIndentation(javadoc);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(JAVADOC)
    public void endJavadoc() {
        requireCurrentCodeBlock(JAVADOC);
        
        codeBlocksStack.pop();
        addCodeLineWithIndentation(" */");
    }
    
    /* -------------------------------------------------- Annotation -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE, ANONYMOUS_CLASS})
    public void addAnnotation(@Nonnull Class<? extends Annotation> annotationType, @Nullable String optionalValues, @Nonnull Object... optionalArguments) {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE, ANONYMOUS_CLASS);
        
        addCodeLineWithIndentation("@" + importIfPossible(annotationType) + (optionalValues != null ? "(" + FormatString.format(optionalValues, QuoteString.Mark.CODE, optionalArguments) + ")" : ""));
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE, ANONYMOUS_CLASS})
    public void addAnnotation(@Nonnull Class<? extends Annotation> annotationType) {
        addAnnotation(annotationType, null);
    }
    
    /* -------------------------------------------------- Class or Interface -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    private void beginClassOrInterface(@Nonnull String declaration, @Nonnull CodeBlock classOrInterface) {
        requireCurrentCodeBlock(NONE, CLASS, INTERFACE);
        
        if (getCurrentCodeBlock() == CodeBlock.NONE) {
            addAnnotation(SuppressWarnings.class, "$", "null");
            StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
            final @Nonnull String generator = caller.getClassName();
            final @Nonnull String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
            addAnnotation(Generated.class, "value = $, date = $", generator, date);
        }
        
        beginBlock(declaration, classOrInterface, NONE, CLASS, INTERFACE);
        addEmptyLine();
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, INTERFACE})
    public void endClassOrInterface(@Nonnull CodeBlock classOrInterface) {
        endBlock(classOrInterface);
        if (getCurrentCodeBlock() != CodeBlock.NONE) {
            addEmptyLine();
        }
    }
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * Begins an interface block in a Java source file.
     */
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginInterface(@Nonnull String declaration) {
        ProcessingLog.debugging("beginInterface");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        ProcessingLog.debugging("stack trace:");
        for (StackTraceElement stackTraceElement : stackTrace) {
            ProcessingLog.debugging(stackTraceElement.toString());
        }
        beginClassOrInterface(declaration, CodeBlock.INTERFACE);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({INTERFACE})
    public void endInterface() {
        ProcessingLog.debugging("endInterface");
        endClassOrInterface(CodeBlock.INTERFACE);
    }
    
    /* -------------------------------------------------- Class -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({NONE, CLASS, INTERFACE})
    public void beginClass(@Nonnull String declaration) {
        ProcessingLog.debugging("beginClass");
        beginClassOrInterface(declaration, CodeBlock.CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS})
    public void endClass() {
        ProcessingLog.debugging("endClass");
        endClassOrInterface(CodeBlock.CLASS);
    }
    
    /* -------------------------------------------------- Anonymous Class -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, METHOD})
    public void beginAnonymousClass(@Nonnull String declaration) {
        ProcessingLog.debugging("beginAnonymousClass");
        requireCurrentCodeBlock(CLASS, METHOD);
        beginBlock(declaration, CodeBlock.ANONYMOUS_CLASS, CLASS, METHOD);
        addEmptyLine();
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({ANONYMOUS_CLASS})
    public void endAnonymousClass() {
        ProcessingLog.debugging("endAnonymousClass");
        endBlock(ANONYMOUS_CLASS);
    }
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void addField(@Nonnull String declaration) {
        requireCurrentCodeBlock(CLASS, ANONYMOUS_CLASS);
        
        addCodeLineWithIndentation(declaration + ";");
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Block -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginBlock(boolean withStaticModifier) {
        beginBlock(withStaticModifier ? "static" : "", BLOCK, CLASS, ANONYMOUS_CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(BLOCK)
    public void endBlock() {
        endBlock(BLOCK);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginConstructor(@Nonnull String declaration) {
        beginBlock(declaration, CONSTRUCTOR, CLASS, ANONYMOUS_CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(CONSTRUCTOR)
    public void endConstructor() {
        endBlock(CONSTRUCTOR);
        addEmptyLine();
    }
    
    /* -------------------------------------------------- Method Declaration -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, INTERFACE})
    public void addMethodDeclaration(@Nonnull String declaration) {
        requireCurrentCodeBlock(CLASS, INTERFACE);
        
        addCodeLineWithIndentation(declaration + ";");
    }
    
    /* -------------------------------------------------- Method -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CLASS, ANONYMOUS_CLASS})
    public void beginMethod(@Nonnull String declaration) {
        beginBlock(declaration, METHOD, CLASS, ANONYMOUS_CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(METHOD)
    public void endMethod() {
        endBlock(METHOD);
        addEmptyLine();
    }
    
    /* -------------------------------------------------- If -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginIf(@Nonnull String condition) {
        beginBlock("if", condition, IF);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(IF)
    public void endIf() {
        endBlock(IF);
    }
    
    /* -------------------------------------------------- Else -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({IF, ELSE_IF})
    public void endIfBeginElse() {
        requireCurrentCodeBlock(IF, ELSE_IF);
        
        codeBlocksStack.pop();
        beginBlock("} else", ELSE);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(ELSE)
    public void endElse() {
        endBlock(ELSE);
    }
    
    /* -------------------------------------------------- Else If -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({IF, ELSE_IF})
    public void endIfBeginElseIf(@Nonnull String condition) {
        requireCurrentCodeBlock(IF, ELSE_IF);
        
        codeBlocksStack.pop();
        beginBlock("} else if", condition, ELSE_IF);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(ELSE_IF)
    public void endElseIf() {
        endBlock(ELSE_IF);
    }
    
    /* -------------------------------------------------- For Loop -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginForLoop(@Nonnull String condition) {
        beginBlock("for", condition, FOR_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(FOR_LOOP)
    public void endForLoop() {
        endBlock(FOR_LOOP);
    }
    
    /* -------------------------------------------------- While Loop -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void beginWhileLoop(@Nonnull String condition) {
        beginBlock("while", condition, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(WHILE_LOOP)
    public void endWhileLoop() {
        endBlock(WHILE_LOOP);
    }
    
    /* -------------------------------------------------- Try - Catch -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn({CONSTRUCTOR, METHOD, BLOCK})
    public void beginTry() {
        beginBlock("try", TRY);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(TRY)
    public void endTryBeginCatch() {
        endBlock(TRY);
        beginBlock("catch", TRY_CATCH, METHOD);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn({TRY, TRY_CATCH})
    public void endTryOrTryCatchBeginFinally() {
        endBlock(TRY, TRY_CATCH);
        beginBlock("finally", TRY_FINALLY, METHOD);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(TRY_FINALLY)
    public void endTryFinally() {
        endBlock(TRY_FINALLY);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn(TRY_CATCH)
    public void endTryCatch() {
        endBlock(TRY_CATCH);
    }
    
    /* -------------------------------------------------- Statement -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addStatement(@Nonnull String statement) {
        requireCurrentCodeBlock();
        
        addCodeLineWithIndentation(statement + ";");
    }
    
    /* -------------------------------------------------- Contracts -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    protected void addContract(@Nonnull Class<? extends Constraint> contractType, @Nonnull Contract generatedContract) {
        addStatement(importIfPossible(contractType) + ".that(" + generatedContract.getCondition() + ").orThrow(" + QuoteString.inDouble(generatedContract.getMessage()) + (generatedContract.getArguments().size() != 0 ? ", " + FiniteIterable.of(generatedContract.getArguments()).join() : "") + ")");
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addPrecondition(@Nonnull Contract generatedContract) {
        addContract(Require.class, generatedContract);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addPostcondition(@Nonnull Contract generatedContract) {
        ProcessingLog.debugging("addPostcondition($)", generatedContract.getCondition());
        addContract(Ensure.class, generatedContract);
    }
    
    @NonWrittenRecipient
    @OnlyPossibleIn()
    public void addInvariant(@Nonnull Contract generatedContract) {
        addContract(Validate.class, generatedContract);
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
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
