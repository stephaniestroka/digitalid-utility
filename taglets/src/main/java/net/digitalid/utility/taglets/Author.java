package net.digitalid.utility.taglets;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.tools.doclets.Taglet;

/**
 * This class defines a custom block tag for class authors.
 */
public final class Author extends CustomTaglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given non-nullable map of registered taglets.
     */
    public static void register(Map<String, Taglet> registeredTaglets) {
        CustomTaglet.register(registeredTaglets, new Author());
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
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
    
    /* -------------------------------------------------- Content -------------------------------------------------- */
    
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
     * Returns the given non-nullable string rotated by 13 characters.
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
        } else {
            return text;
        }
    }
    
}
