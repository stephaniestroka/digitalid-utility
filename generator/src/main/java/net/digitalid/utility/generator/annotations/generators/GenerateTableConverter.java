/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.generator.generators.ConverterGenerator;
import net.digitalid.utility.storage.enumerations.ForeignKeyAction;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;

/**
 * Marks a class such that the {@link ConverterGenerator converter generator} generates a table for the annotated class.
 * 
 * @see GenerateBuilder
 * @see GenerateSubclass
 * @see GenerateConverter
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(GenerateAnnotationValidator.class)
@TODO(task = "Move this class to the database layer again once generators can be declared in higher artifacts.", date = "2017-04-16", author = Author.KASPAR_ETTER)
public @interface GenerateTableConverter {
    
    /* -------------------------------------------------- Module -------------------------------------------------- */
    
    /**
     * Returns the module to which the generated table belongs.
     * Leave this empty to generate a table without a parent module.
     */
    @Nonnull String module() default "";
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of the generated storage.
     * Leave this empty to use the default name.
     */
    @Nonnull String name() default "";
    
    /* -------------------------------------------------- Reference -------------------------------------------------- */
    
    /**
     * Returns the schema on which the table was created.
     * Leave this empty to use the current schema.
     */
    @Nonnull String schema() default "";
    
    /**
     * Returns the name with which the table was created.
     * Leave this empty to use the default name.
     */
    @Nonnull String table() default "";
    
    /**
     * Returns the columns of the primary key of the generate table.
     * Leave this empty to use the default mechanism to determine the primary keys.
     */
    @Nonnull String[] columns() default {};
    
    /* -------------------------------------------------- Actions -------------------------------------------------- */
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on the generated table when an entry of the generated table is deleted.
     */
    @Nonnull ForeignKeyAction onDelete() default ForeignKeyAction.CASCADE;
    
    /**
     * Returns the action that determines what happens with entries that have a foreign key constraint on the generated table when an entry of the generated table is updated.
     */
    @Nonnull ForeignKeyAction onUpdate() default ForeignKeyAction.CASCADE;
    
}
