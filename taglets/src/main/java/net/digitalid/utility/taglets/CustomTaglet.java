package net.digitalid.utility.taglets;

import java.beans.Introspector;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

/**
 * This class is the parent of all custom taglets.
 */
public abstract class CustomTaglet implements Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers the given non-nullable custom taglet at the given non-nullable map.
     */
    static void register(Map<String, Taglet> registeredTaglets, CustomTaglet customTaglet) {
        assert registeredTaglets != null;
        assert customTaglet != null;
        
        final String name = customTaglet.getName();
        // com.sun.tools.doclets.internal.toolkit.taglets.SimpleTaglet implements com.sun.tools.doclets.internal.toolkit.taglets.Taglet
        // and cannot be cast to com.sun.tools.doclets.Taglet. Thus, we can only expect to get a java.lang.Object from the given map.
        // (And no, removing the following two lines does not help either as this results in another java.lang.ClassCastException.)
        final Object existingTaglet = registeredTaglets.get(name);
        if (existingTaglet != null) { registeredTaglets.remove(name); }
        registeredTaglets.put(name, customTaglet);
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    @Override
    public boolean inField() {
        return false;
    }
    
    @Override
    public boolean inConstructor() {
        return false;
    }
    
    @Override
    public boolean inMethod() {
        return false;
    }
    
    @Override
    public boolean inOverview() {
        return false;
    }
    
    @Override
    public boolean inPackage() {
        return false;
    }
    
    @Override
    public boolean inType() {
        return false;
    }
    
    @Override
    public boolean isInlineTag() {
        return false;
    }
    
    /* -------------------------------------------------- Title -------------------------------------------------- */
    
    /**
     * Returns the non-nullable title of this taglet.
     */
    protected abstract String getTitle();
    
    /**
     * Returns the non-nullable title of this taglet with HTML formatting.
     */
    private String getTitleWithHTML() {
        return "<dt><span class=\"strong\">" + getTitle() + ":</span></dt>\n";
    }
    
    /* -------------------------------------------------- Text -------------------------------------------------- */
    
    /**
     * Stores the pattern that a text needs to match in order to be displayed as code.
     */
    private static final Pattern pattern = Pattern.compile("(.+) : \"(.+)\";");
    
    /**
     * Returns the given non-nullable text formatted as code if it matches the above pattern.
     */
    protected String getText(String text) {
        final Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) { return "<code> " + matcher.group(1) + "</code> - " + Introspector.decapitalize(matcher.group(2)); }
        else { return text; }
    }
    
    /**
     * Returns the given non-nullable text with HTML formatting.
     */
    private String getTextWithHTML(String text) {
        return "<dd>" + getText(text) + "</dd>\n";
    }
    
    /* -------------------------------------------------- Output -------------------------------------------------- */
    
    /**
     * Returns the string representation of the given non-nullable tag, which is output to the generated page.
     */
    @Override
    public String toString(Tag tag) {
        return getTitleWithHTML() + getTextWithHTML(tag.text());
    }
    
    /**
     * Returns the string representation of the given non-nullable tags, which are output to the generated page.
     */
    @Override
    public String toString(Tag[] tags) {
        if (tags.length == 0) { return ""; }
        final StringBuilder string = new StringBuilder(getTitleWithHTML());
        for (final Tag tag : tags) { string.append(getTextWithHTML(tag.text())); }
        return string.toString();
    }
    
}
