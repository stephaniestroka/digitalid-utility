package net.digitalid.utility.generator;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.generator.information.TypeInformation;
import net.digitalid.utility.validation.annotations.type.Utiliy;

/**
 * Description.
 */
@Utiliy
public class BuilderGenerator {
    
    public static void generateBuilderFor(@Nonnull TypeInformation typeInformation) {
        Require.that(typeInformation.generatable).orThrow("No subclass can be generated for " + typeInformation);
        
        // TODO!
    }
    
}
