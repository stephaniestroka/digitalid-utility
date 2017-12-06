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
package net.digitalid.utility.circumfixes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates various quotation marks.
 */
@Immutable
public enum Quotes implements Circumfix {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The single quotes '\'' and '\''.
     */
    SINGLE("'", "'"),
    
    /**
     * The double quotes '"' and '"'.
     */
    DOUBLE("\"", "\""),
    
    /**
     * The angle quotes '«' and '»'.
     */
    ANGLE("«", "»"),
    
    /**
     * The code quotes '"' and '"'.
     * The intention is to use the quotes
     * only in case the object is a string.
     */
    CODE("\"", "\"");
    
    /* -------------------------------------------------- Prefix -------------------------------------------------- */
    
    private final @Nonnull String prefix;
    
    @Pure
    @Override
    public @Nonnull String getPrefix() {
        return prefix;
    }
    
    /* -------------------------------------------------- Suffix -------------------------------------------------- */
    
    private final @Nonnull String suffix;
    
    @Pure
    @Override
    public @Nonnull String getSuffix() {
        return suffix;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    private Quotes(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /* -------------------------------------------------- Formatting -------------------------------------------------- */
    
    /**
     * Returns the given object surrounded by the given quotes.
     */
    @Pure
    public static @Nonnull String in(@Nullable Quotes quotes, @NonCaptured @Unmodified @Nullable Object object) {
        if (quotes == null) { return String.valueOf(object); }
        else if (quotes == Quotes.CODE) { return object instanceof CharSequence ? "\"" + String.valueOf(object) + "\"" : String.valueOf(object); }
        else { return quotes.getPrefix() + String.valueOf(object) + quotes.getSuffix(); }
    }
    
    /**
     * Returns the given object in single quotes.
     */
    @Pure
    public static @Nonnull String inSingle(@Nullable Object object) {
        return in(Quotes.SINGLE, object);
    }
    
    /**
     * Returns the given object in double quotes.
     */
    @Pure
    public static @Nonnull String inDouble(@Nullable Object object) {
        return in(Quotes.DOUBLE, object);
    }
    
    /**
     * Returns the given object in angle quotes.
     */
    @Pure
    public static @Nonnull String inAngle(@Nullable Object object) {
        return in(Quotes.ANGLE, object);
    }
    
    /**
     * Returns the given object in double quotes if it is an instance of {@link CharSequence} or without quotes otherwise.
     */
    @Pure
    public static @Nonnull String inCode(@Nullable Object object) {
        return in(Quotes.CODE, object);
    }
    
}
