package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
class RecoveryExceptionSubclass extends RecoveryException {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    private final String message;
    
    @Override
    public String getMessage() {
        String result = this.message;
        return result;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    RecoveryExceptionSubclass(String message) {
        super();
        
        this.message = message;
    }
    
    /* -------------------------------------------------- Overridden Methods -------------------------------------------------- */
    
    @Override
    public String getSummary() {
        String result = super.getSummary();
        return result;
    }
    
    @Override
    public @Nullable Throwable getCause() {
        @Nullable Throwable result = super.getCause();
        return result;
    }
    
    /* -------------------------------------------------- Implement Methods -------------------------------------------------- */
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
}
