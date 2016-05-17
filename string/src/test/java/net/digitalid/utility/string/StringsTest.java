package net.digitalid.utility.string;

import javax.annotation.Nonnull;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {
    
    @Test
    public void testFormat() {
        assertEquals("The result should have been '42' but was '41'. ['too many']", Strings.format("The result should have been $ but was $.", 42, 41, "too many"));
        assertEquals("The result should have been '42' but was '41'.", Strings.format("The result should have been $ but was $.", 42, 41));
        assertEquals("The result should have been '42' but was $.", Strings.format("The result should have been $ but was $.", 42));
        assertEquals("['too many']", Strings.format("", "too many"));
        assertEquals("", Strings.format(""));
    }
    
    @Test
    public void testCapitalizeFirstLetters() {
        assertEquals("Hello World", Strings.capitalizeFirstLetters("hello world"));
        assertEquals("", Strings.capitalizeFirstLetters(""));
    }
    
    @Test
    public void testDecamelize() {
        assertEquals("hello world", Strings.decamelize("HelloWorld"));
        assertEquals("", Strings.decamelize(""));
    }
    
    @Test
    public void testLowercaseFirstCharacter() {
        assertEquals("hello world", Strings.lowercaseFirstCharacter("Hello world"));
        assertEquals("", Strings.lowercaseFirstCharacter(""));
    }
    
    @Test
    public void testUppercaseFirstCharacter() {
        assertEquals("Hello world", Strings.uppercaseFirstCharacter("hello world"));
        assertEquals("", Strings.uppercaseFirstCharacter(""));
    }
    
    @Test
    public void testSubstringFromLast() {
        assertEquals("", Strings.substringFromLast("", "."));
        assertEquals("net", Strings.substringFromLast("www.digitalid.net", "."));
        assertEquals("test", Strings.substringFromLast("test", "."));
    }
    
    @Test
    public void testSubstringUntilFirst() {
        assertEquals("", Strings.substringUntilFirst("", "."));
        assertEquals("www", Strings.substringUntilFirst("www.digitalid.net", "."));
        assertEquals("test", Strings.substringUntilFirst("test", "."));
    }
    
    @Test
    public void testLongestCommonPrefix() {
        assertEquals("", Strings.longestCommonPrefix("Hello", "World"));
        assertEquals("", Strings.longestCommonPrefix("", "Hello", "World"));
        assertEquals("Hello", Strings.longestCommonPrefix("Hello world.", "Hello?", "Hello there!"));
        assertEquals("net.digitalid.", Strings.longestCommonPrefix("net.digitalid.utility", "net.digitalid.database", "net.digitalid.core"));
    }
    
    @Test
    public void testStartsWithAny() {
        assertFalse(Strings.startsWithAny("", "Hello", "World"));
        assertFalse(Strings.startsWithAny("Bye", "Hello", "World"));
        assertTrue(Strings.startsWithAny("Hello World!", "Hello", "World"));
    }
    
    private static class Student {
        
        private final @Nonnull String name;
        
        private final int age;
        
        private Student(@Nonnull String name, int age) {
            this.name = name;
            this.age = age;
        }
        
    }
    
    @Test
    public void testToString() {
        assertEquals("Student(name: \"John Doe\", age: 42)", Strings.toString(new Student("John Doe", 42)));
    }
    
    @Test
    public void testGetCardinal() {
        assertEquals("zero", Strings.getCardinal(0));
        assertEquals("twelve", Strings.getCardinal(12));
        assertEquals("minus twelve", Strings.getCardinal(-12));
        assertEquals("13", Strings.getCardinal(13));
        assertEquals("-13", Strings.getCardinal(-13));
    }
    
    @Test
    public void testGetOrdinal() {
        assertEquals("first", Strings.getOrdinal(1));
        assertEquals("twelfth", Strings.getOrdinal(12));
        assertEquals("13th", Strings.getOrdinal(13));
        assertEquals("20th", Strings.getOrdinal(20));
        assertEquals("21st", Strings.getOrdinal(21));
        assertEquals("22nd", Strings.getOrdinal(22));
        assertEquals("23rd", Strings.getOrdinal(23));
        assertEquals("24th", Strings.getOrdinal(24));
    }
    
    @Test
    public void testGetOrdinalWithLatinRoot() {
        assertEquals("primary", Strings.getOrdinalWithLatinRoot(1));
        assertEquals("duodenary", Strings.getOrdinalWithLatinRoot(12));
        assertEquals("13.", Strings.getOrdinalWithLatinRoot(13));
    }
    
    @Test
    public void testPluralize() {
        assertEquals("", Strings.pluralize(""));
        assertEquals("tests", Strings.pluralize("test"));
        assertEquals("men", Strings.pluralize("man"));
        assertEquals("women", Strings.pluralize("woman"));
        assertEquals("feet", Strings.pluralize("foot"));
        assertEquals("inches", Strings.pluralize("inch"));
        assertEquals("halves", Strings.pluralize("half"));
        assertEquals("viruses", Strings.pluralize("virus"));
        assertEquals("indices", Strings.pluralize("index"));
        assertEquals("parties", Strings.pluralize("party"));
        assertEquals("formulae", Strings.pluralize("formula"));
    }
    
    @Test
    public void testPrependWithNumberAndPluralize() {
        assertEquals("one bug", Strings.prependWithNumberAndPluralize(1, "bug"));
        assertEquals("ten bugs", Strings.prependWithNumberAndPluralize(10, "bug"));
    }
    
    @Test
    public void testPrependWithIndefiniteArticle() {
        assertEquals("", Strings.prependWithIndefiniteArticle(""));
        assertEquals("a bug", Strings.prependWithIndefiniteArticle("bug"));
        assertEquals("a user", Strings.prependWithIndefiniteArticle("user"));
        assertEquals("an error", Strings.prependWithIndefiniteArticle("error"));
    }
    
}
