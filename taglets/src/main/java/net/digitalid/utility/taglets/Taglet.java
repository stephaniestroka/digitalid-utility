package net.digitalid.utility.taglets;

import java.beans.Introspector;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.Tag;

/**
 * This class serves as a template for custom block tags.
 */
public abstract class Taglet implements com.sun.tools.doclets.Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers the given taglet at the given map.
     * 
     * @param map the map at which the taglet is registered.
     * @param taglet the taglet to be registered at the map.
     */
    static void register(Map<String, Taglet> map, Taglet taglet) {
        // System.out.println("Registering: " + taglet.getName());
        try {
            final String name = taglet.getName();
            final Object other = map.get(name);
            if (other != null) { map.remove(name); }
            map.put(name, taglet);
        } catch (Throwable throwable) {
            System.err.println("Failed to register taglet '" + taglet.getName() + "':");
            throwable.printStackTrace();
            throw throwable;
        }
    }
    
    /* -------------------------------------------------- Default Methods -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Override
    public abstract String getName();
    
    /* -------------------------------------------------- Title -------------------------------------------------- */
    
    /**
     * Returns the title of this taglet.
     * 
     * @return the title of this taglet.
     */
    protected abstract String getTitle();
    
    /**
     * Returns the title of this taglet with HTML formatting.
     * 
     * @return the title of this taglet with HTML formatting.
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
     * Formats the given text.
     * 
     * @param text the text to format.
     * 
     * @return the formatted text.
     */
    protected String getText(String text) {
        final Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) { return "<code> " + matcher.group(1) + "</code> - " + Introspector.decapitalize(matcher.group(2)); }
        else { return text; }
    }
    
    /**
     * Formats the given text with HTML.
     * 
     * @param text the text to format.
     * 
     * @return the formatted text with HTML.
     */
    private String getTextWithHTML(String text) {
        return "<dd>" + getText(text) + "</dd>\n";
    }
    
    /* -------------------------------------------------- Output -------------------------------------------------- */
    
    /**
     * Returns the string representation of the given tag, which is output to the generated page.
     * 
     * @param tag the tag to be formatted as a string.
     * 
     * @return the string representation of the given tag, which is output to the generated page.
     */
    @Override
    public String toString(Tag tag) {
        return getTitleWithHTML()+ getTextWithHTML(tag.text());
    }
    
    /**
     * Returns the string representation of the given tags, which is output to the generated page.
     * 
     * @param tags the tags to be formatted as a string.
     * 
     * @return the string representation of the given tags, which is output to the generated page.
     */
    @Override
    public String toString(Tag[] tags) {
        if (tags.length == 0) { return ""; }
        final StringBuilder string = new StringBuilder(getTitleWithHTML());
        for (final Tag tag : tags) { string.append(getTextWithHTML(tag.text())); }
        return string.toString();
    }
    
}
