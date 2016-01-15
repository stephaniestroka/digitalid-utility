package net.digitalid.utility.taglets;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class defines a custom block tag for class authors.
 */
public final class Author extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(Map<String, Taglet> map) {
        Taglet.register(map, new Author());
    }
    
    /* -------------------------------------------------- Overrides -------------------------------------------------- */
    
    @Override
    public boolean inOverview() {
        return true;
    }
    
    @Override
    public boolean inPackage() {
        return true;
    }
    
    @Override
    public boolean inType() {
        return true;
    }
    
    @Override
    public String getName() {
        return "author";
    }
    
    @Override
    public String getTitle() {
        return "Author";
    }
    
    /* -------------------------------------------------- Text -------------------------------------------------- */
    
    /**
     * Stores the pattern that a text needs to match in order to be displayed as an email address.
     */
    private static final Pattern pattern = Pattern.compile("(.+) \\((.+)\\)");
    
    /**
     * Rotates the given string by 13 characters.
     * 
     * @param input the string to be rotated.
     * 
     * @return the given string rotated by 13 characters.
     */
    private static String rot13(String input) {
        final StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if      (c >= 'a' && c <= 'm' || c >= 'A' && c <= 'M') { c += 13; }
            else if (c >= 'n' && c <= 'z' || c >= 'N' && c <= 'Z') { c -= 13; }
            output.append(c);
        }
        return output.toString();
    }
    
    @Override
    protected String getText(String text) {
        final Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            final boolean obfuscated = true;
            if (obfuscated) {
                return "<script type=\"text/javascript\">document.write(\"<n uers=\\\"znvygb:" + rot13(matcher.group(2)) + "\\\">" + rot13(matcher.group(1)) + "</n>\".replace(/[a-zA-Z]/g,function(c){return String.fromCharCode((c<=\"Z\"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));</script>";
            } else {
                return "<a href=\"mailto:" + matcher.group(2) + "\">" + matcher.group(1) + "</a>";
            }
        }
        else { return text; }
    }
    
}
