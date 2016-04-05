package net.digitalid.utility.directory.annotations;

import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a {@link File file} is a {@link File#isDirectory() directory}.
 * 
 * @see IsFile
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.CLASS)
public @interface IsDirectory {}
