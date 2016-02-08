package net.digitalid.utility.processor.files;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.exceptions.UnexpectedValueException;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.AnnotationProcessing;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

import static net.digitalid.utility.processor.files.JavaSourceFile.CodeBlock.*;

/**
 * This class makes it easier to write Java source files during annotation processing.
 */
@Mutable
public class JavaSourceFile extends GeneratedFile {
    
    /* -------------------------------------------------- Generated Class -------------------------------------------------- */
    
    private final @Nonnull String qualifiedClassName;
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return qualifiedClassName;
    }
    
    /* -------------------------------------------------- Source Class -------------------------------------------------- */
    
    private final @Nonnull TypeElement sourceClassElement;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    public JavaSourceFile(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
        this.qualifiedClassName = qualifiedClassName;
        this.sourceClassElement = sourceClassElement;
        AnnotationLog.verbose("Generating the class " + this);
    }
    
    /**
     * Returns a Java source file for the given class.
     */
    @Pure
    public static @Nonnull JavaSourceFile forClass(@Nonnull String qualifiedClassName, @Nonnull TypeElement sourceClassElement) {
        return new JavaSourceFile(qualifiedClassName, sourceClassElement);
    }
    
    /* -------------------------------------------------- Imports -------------------------------------------------- */
    
    private static class ImportGroup {
        
        final @Nonnull String prefix;
        
        ImportGroup(@Nonnull String prefix) {
            this.prefix = prefix;
        }
        
        final @Nonnull @NonNullableElements Set<String> imports = new HashSet<>();
    }
    
    private final @Nonnull @NonNullableElements ImportGroup[] importGroups = {
        new ImportGroup("java."),
        new ImportGroup("javax."),
        new ImportGroup("net.digitalid.utility."),
        new ImportGroup("net.digitalid.database"),
        new ImportGroup("net.digitalid.core"),
        new ImportGroup("")
    };
    
    @NonWrittenRecipient
    public boolean addImport(@Nonnull String qualifiedClassName) {
        requireNotWritten();
        
        for (@Nonnull ImportGroup importGroup : importGroups) {
            if (qualifiedClassName.startsWith(importGroup.prefix)) {
                return importGroup.imports.add(qualifiedClassName);
            }
        }
        throw UnexpectedValueException.with("qualifiedClassName", qualifiedClassName);
    }
    
    /* -------------------------------------------------- Stack -------------------------------------------------- */
    
    public static enum CodeBlock {
        NONE(""), JAVADOC(" * "), CLASS, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP;
        
        private final @Nonnull String indentation;
        
        @Pure
        public @Nonnull String getIndentation() {
            return indentation;
        }
        
        private CodeBlock(@Nonnull String indentation) {
            this.indentation = indentation;
        }
        
        private CodeBlock() {
            this("    ");
        }
        
    }
    
    private final @Nonnull @NonNullableElements Stack<CodeBlock> codeStack = new Stack<>();
    
    public @Nonnull CodeBlock getCurrentCodeBlock() {
        return codeStack.empty() ? CodeBlock.NONE : codeStack.peek();
    }
    
    /**
     * Requires that the {@link #getCurrentCodeBlock() current code block} is one of the given types.
     * 
     * @throws PreconditionViolationException if this is not the case.
     */
    @Pure
    @NonWrittenRecipient
    protected void requireCurrentCodeBlock(@Nonnull CodeBlock... codeBlocks) {
        requireNotWritten();
        Require.that(Arrays.asList(codeBlocks).contains(getCurrentCodeBlock())).orThrow("The current code block should have been one of " + Arrays.toString(codeBlocks) + " but was " + getCurrentCodeBlock());
    }
    
    /* -------------------------------------------------- Code -------------------------------------------------- */
    
    private final @Nonnull StringBuilder sourceCode = new StringBuilder();
    
    @NonWrittenRecipient
    protected void addCodeLineWithIndentation(@Nonnull String statement) {
        requireNotWritten();
        
        for (@Nonnull CodeBlock codeBlock : codeStack) {
            sourceCode.append(codeBlock.getIndentation());
        }
        sourceCode.append(statement).append("\n");
    }
    
    @NonWrittenRecipient
    protected void beginBlock(@Nonnull String declaration, @Nonnull CodeBlock codeBlock, @Nonnull CodeBlock... codeBlocks) {
        requireCurrentCodeBlock(codeBlocks);
        
        addCodeLineWithIndentation(declaration + (declaration.isEmpty() ? "" : " ") + "{");
        codeStack.push(codeBlock);
    }
    
    @NonWrittenRecipient
    protected void beginBlockWithParantheses(@Nonnull String keyword, @Nonnull String condition, @Nonnull CodeBlock codeBlock, @Nonnull CodeBlock... codeBlocks) {
        beginBlock(keyword + " (" + condition + ")", codeBlock, codeBlocks);
    }
    
    @NonWrittenRecipient
    protected void endBlock(@Nonnull CodeBlock... codeBlocks) {
        requireCurrentCodeBlock(codeBlocks);
        
        codeStack.pop();
        addCodeLineWithIndentation("}");
    }
    
    /* -------------------------------------------------- Javadoc -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({NONE, CLASS})
    public void beginJavadoc() {
        requireCurrentCodeBlock(NONE, CLASS);
        
        addCodeLineWithIndentation("/**");
        codeStack.push(JAVADOC);
    }
    
    @NonWrittenRecipient
    @OnlyIn({JAVADOC})
    public void addJavadoc(@Nonnull String javadoc) {
        requireCurrentCodeBlock(JAVADOC);
        
        addCodeLineWithIndentation(javadoc);
    }
    
    @NonWrittenRecipient
    @OnlyIn({JAVADOC})
    public void endJavadoc() {
        requireCurrentCodeBlock(JAVADOC);
        
        codeStack.pop();
        addCodeLineWithIndentation(" */");
    }
    
    /* -------------------------------------------------- Annotation -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({NONE, CLASS})
    public void addAnnotation(@Nonnull String annotation) {
        requireCurrentCodeBlock(NONE, CLASS);
        
        addCodeLineWithIndentation(annotation);
    }
    
    /* -------------------------------------------------- Class -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({NONE, CLASS})
    public void beginClass(@Nonnull String declaration) {
        addImport("javax.annotation.Generated");
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        addAnnotation("@Generated(value = {" + QuoteString.inDouble(caller.getClassName()) + "}, date = " + QuoteString.inDouble(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())) + ")");
        beginBlock(declaration, CLASS, NONE, CLASS);
        addCodeLineWithIndentation("");
    }
    
    @NonWrittenRecipient
    @OnlyIn({CLASS})
    public void endClass() {
        endBlock(CLASS);
    }
    
    /* -------------------------------------------------- Block -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({CLASS})
    public void beginBlock(boolean withStaticModifier) {
        beginBlock(withStaticModifier ? "static" : "", BLOCK, CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyIn({BLOCK})
    public void endBlock() {
        endBlock(BLOCK);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({CLASS})
    public void beginConstructor(@Nonnull String declaration) {
        beginBlock(declaration, CONSTRUCTOR, CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyIn({CONSTRUCTOR})
    public void endConstructor() {
        endBlock(CONSTRUCTOR);
        addCodeLineWithIndentation("");
    }
    
    /* -------------------------------------------------- Method -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({CLASS})
    public void beginMethod(@Nonnull String declaration) {
        beginBlock(declaration, METHOD, CLASS);
    }
    
    @NonWrittenRecipient
    @OnlyIn({METHOD})
    public void endMethod() {
        endBlock(METHOD);
        addCodeLineWithIndentation("");
    }
    
    /* -------------------------------------------------- If -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP})
    public void beginIf(@Nonnull String condition) {
        beginBlockWithParantheses("if", condition, IF, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyIn({IF})
    public void endIf() {
        endBlock(IF);
    }
    
    /* -------------------------------------------------- Else -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({IF, ELSE_IF})
    public void beginElse() {
        requireCurrentCodeBlock(IF, ELSE_IF);
        
        codeStack.pop();
        beginBlock("} else", ELSE, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyIn({ELSE})
    public void endElse() {
        endBlock(ELSE);
    }
    
    /* -------------------------------------------------- Else If -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({IF, ELSE_IF})
    public void beginElseIf(@Nonnull String condition) {
        requireCurrentCodeBlock(IF, ELSE_IF);
        
        codeStack.pop();
        beginBlockWithParantheses("} else if", condition, ELSE_IF, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyIn({ELSE_IF})
    public void endElseIf() {
        endBlock(ELSE_IF);
    }
    
    /* -------------------------------------------------- For Loop -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP})
    public void beginForLoop(@Nonnull String condition) {
        beginBlockWithParantheses("for", condition, FOR_LOOP, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyIn({FOR_LOOP})
    public void endForLoop() {
        endBlock(FOR_LOOP);
    }
    
    /* -------------------------------------------------- While Loop -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP})
    public void beginWhileLoop(@Nonnull String condition) {
        beginBlockWithParantheses("while", condition, WHILE_LOOP, BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
    }
    
    @NonWrittenRecipient
    @OnlyIn({WHILE_LOOP})
    public void endWhileLoop() {
        endBlock(WHILE_LOOP);
    }
    
    /* -------------------------------------------------- Statement -------------------------------------------------- */
    
    @NonWrittenRecipient
    @OnlyIn({BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP})
    public void addStatement(@Nonnull String statement) {
        requireCurrentCodeBlock(BLOCK, CONSTRUCTOR, METHOD, IF, ELSE, ELSE_IF, FOR_LOOP, WHILE_LOOP);
        
        addCodeLineWithIndentation(statement + ";");
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    @Override
    public boolean writeOnce() {
        requireCurrentCodeBlock(NONE);
        
        try {
            final @Nonnull JavaFileObject javaFileObject = AnnotationProcessing.environment.get().getFiler().createSourceFile(qualifiedClassName, sourceClassElement);
            try (@Nonnull Writer writer = javaFileObject.openWriter(); @Nonnull PrintWriter printWriter = new PrintWriter(writer)) {
                    printWriter.println("package " + qualifiedClassName.substring(0, qualifiedClassName.lastIndexOf('.')) + ";");
                    printWriter.println();
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
                    printWriter.append(sourceCode);
                    printWriter.flush();
            }
            AnnotationLog.information("Wrote the source code to the file " + this);
            return true;
        } catch (@Nonnull IOException exception) {
            AnnotationLog.error("An exception occurred while writing the source code to the file " + this + ": " + exception);
        }
        return false;
    }
    
}
