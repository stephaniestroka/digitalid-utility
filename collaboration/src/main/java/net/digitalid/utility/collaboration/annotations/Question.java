package net.digitalid.utility.collaboration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;

/**
 * This annotation indicates that something was not clear during review.
 * 
 * @see Review
 * @see TODO
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface Question {
    
    /**
     * Returns the question that arose during code review.
     */
    @Nonnull String content();
    
    /**
     * Returns the date when this question was asked.
     * (The desired format is 'YYYY-MM-DD'.)
     */
    @Nonnull String date();
    
    /**
     * Returns the author that asked this question.
     */
    @Nonnull Author author();
    
    /**
     * Returns the programmer that can answer this question.
     */
    @Nonnull Author assignee() default Author.ANYONE;
    
    /**
     * Returns the priority with which the question should be answered.
     */
    @Nonnull Priority priority() default Priority.UNSPECIFIED;
    
}
