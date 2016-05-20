package net.digitalid.utility.testing;

/**
 * This class allows to test the assignability of nested classes.
 */
public class TestTypes {
    
    public static class NestedSuperclass {}
    
    public static class NestedSubclass extends NestedSuperclass {}
    
    public class InnerSuperclass {}
    
    public class InnerSubclass extends InnerSuperclass {}
    
}
