package net.digitalid.utility.processing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.processing.logging.ProcessingLog;

/**
 * This annotation indicates that the annotated method logs a processing error when returning null.
 * The caller can thus simply check whether the return value is null without having to log another error.
 * The reason for not throwing exceptions instead is that the annotation processing shall continue to the end.
 * 
 * @see ProcessingLog#error(java.lang.CharSequence, net.digitalid.utility.processing.logging.SourcePosition, java.lang.Object...)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface LogsErrorWhenReturningNull {}
