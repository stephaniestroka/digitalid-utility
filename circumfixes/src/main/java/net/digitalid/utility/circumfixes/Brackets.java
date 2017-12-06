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
 * This class enumerates various brackets.
 */
@Immutable
public enum Brackets implements Circumfix {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The round brackets '(' and ')'.
     */
    ROUND("(", ")"),
    
    /**
     * The square brackets '[' and ']'.
     */
    SQUARE("[", "]"),
    
    /**
     * The curly brackets '{' and '}'.
     */
    CURLY("{", "}"),
    
    /**
     * The pointy brackets '<' and '>'.
     */
    POINTY("<", ">"),
    
    /**
     * The curly brackets with a space after the opening and before the closing bracket.
     */
    JSON("{ ", " }");
    
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
    
    private Brackets(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /* -------------------------------------------------- Formatting -------------------------------------------------- */
    
    /**
     * Returns the given object surrounded by the given brackets.
     */
    @Pure
    public static @Nonnull String in(@Nullable Brackets brackets, @NonCaptured @Unmodified @Nullable Object object) {
        if (brackets == null) { return String.valueOf(object); }
        else { return brackets.getPrefix() + String.valueOf(object) + brackets.getSuffix(); }
    }
    
    /**
     * Returns the given object in round brackets.
     */
    @Pure
    public static @Nonnull String inRound(@Nullable Object object) {
        return in(Brackets.ROUND, object);
    }
    
    /**
     * Returns the given object in square brackets.
     */
    @Pure
    public static @Nonnull String inSquare(@Nullable Object object) {
        return in(Brackets.SQUARE, object);
    }
    
    /**
     * Returns the given object in curly brackets.
     */
    @Pure
    public static @Nonnull String inCurly(@Nullable Object object) {
        return in(Brackets.CURLY, object);
    }
    
    /**
     * Returns the given object in pointy brackets.
     */
    @Pure
    public static @Nonnull String inPointy(@Nullable Object object) {
        return in(Brackets.POINTY, object);
    }
    
}
