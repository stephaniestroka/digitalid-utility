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
 * This class enumerates various comments.
 */
@Immutable
public enum Comments implements Circumfix {
    
    /* -------------------------------------------------- Enumerations -------------------------------------------------- */
    
    /**
     * The Java comment prefix '/* ' and suffix ' *\/'.
     */
    JAVA("/* ", " */"),
    
    /**
     * The XML comment prefix '<!-- ' and suffix ' -->'.
     */
    XML("<!-- ", " -->");
    
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
    
    private Comments(@Nonnull String prefix, @Nonnull String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    /* -------------------------------------------------- Formatting -------------------------------------------------- */
    
    /**
     * Returns the given object surrounded by the given comments.
     */
    @Pure
    public static @Nonnull String in(@Nullable Comments comments, @NonCaptured @Unmodified @Nullable Object object) {
        if (comments == null) { return String.valueOf(object); }
        else { return comments.getPrefix() + String.valueOf(object) + comments.getSuffix(); }
    }
    
    /**
     * Returns the given object in Java comments.
     */
    @Pure
    public static @Nonnull String inJava(@Nullable Object object) {
        return in(Comments.JAVA, object);
    }
    
    /**
     * Returns the given object in XML comments.
     */
    @Pure
    public static @Nonnull String inXML(@Nullable Object object) {
        return in(Comments.XML, object);
    }
    
}
