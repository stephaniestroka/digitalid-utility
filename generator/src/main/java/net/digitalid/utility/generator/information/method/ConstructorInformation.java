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
package net.digitalid.utility.generator.information.method;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.generator.generators.SubclassGenerator;
import net.digitalid.utility.generator.information.type.TypeInformation;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a constructor for generating a {@link SubclassGenerator subclass} and {@link BuilderGenerator builder}.
 */
public class ConstructorInformation extends ExecutableInformation {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConstructorInformation(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType, @Nonnull TypeInformation typeInformation) {
        super(element, containingType, typeInformation);
        
        Require.that(element.getKind() == ElementKind.CONSTRUCTOR).orThrow("The element $ has to be a constructor.", SourcePosition.of(element));
    }
    
    /**
     * Returns the constructor information of the given constructor element and containing type.
     * 
     * @require element.getKind() == ElementKind.CONSTRUCTOR : "The element has to be a constructor.";
     */
    @Pure
    public static @Nonnull ConstructorInformation of(@Nonnull ExecutableElement element, @Nonnull DeclaredType containingType, @Nonnull TypeInformation typeInformation) {
        return new ConstructorInformation(element, containingType, typeInformation);
    }
    
    public @Nonnull String toString() {
        return getElement() + "(" + getElement().getParameters() + ")";
    }
    
}
