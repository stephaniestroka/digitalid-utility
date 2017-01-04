package net.digitalid.utility.contracts.exceptions;

import javax.annotation.Generated;

@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
class InvariantExceptionSubclass extends InvariantException {
    
    /* -------------------------------------------------- Message -------------------------------------------------- */
    
    private final String message;
    
    @Override
    public String getMessage() {
        String result = this.message;
        return result;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    InvariantExceptionSubclass(String message) {
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
    public Throwable getCause() {
        Throwable result = super.getCause();
        return result;
    }
    
    /* -------------------------------------------------- Implement Methods -------------------------------------------------- */
    
    /* -------------------------------------------------- Generated Methods -------------------------------------------------- */
    
}
