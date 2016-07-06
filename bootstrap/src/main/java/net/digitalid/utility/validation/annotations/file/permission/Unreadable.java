package net.digitalid.utility.validation.annotations.file.permission;

import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.validation.annotations.file.permission.Readable;

/**
 * This annotation indicates that the annotated {@link File file} is not {@link File#canRead() readable}.
 * 
 * @see Readable
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unreadable {}
