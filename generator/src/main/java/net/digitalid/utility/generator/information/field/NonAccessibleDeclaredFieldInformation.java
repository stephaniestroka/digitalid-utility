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
package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 *
 */
public class NonAccessibleDeclaredFieldInformation extends FieldInformationImplementation implements RepresentingFieldInformation {
    
    protected NonAccessibleDeclaredFieldInformation(@Nonnull Element element, @Nonnull TypeMirror type, @Nonnull DeclaredType containingType) {
        super(element, type, containingType);
    }
    
    public static @Nonnull NonAccessibleDeclaredFieldInformation of(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        return new NonAccessibleDeclaredFieldInformation(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
    }
    
    /* -------------------------------------------------- Access -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isAccessible() {
        return false;
    }
    
    @Override
    public @Nonnull String getAccessCode() {
        throw new UnsupportedOperationException("Non-accessible field " + Quotes.inSingle(getName()) + " does not have an access code.");
    }
    
    @Override
    public boolean isMutable() {
        return false;
    }
    
}
