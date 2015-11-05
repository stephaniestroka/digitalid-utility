package net.digitalid.utility.taglets;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class defines a custom block tag for class authors.
 */
@Stateless
public final class Author extends Taglet {
    
    /* -------------------------------------------------- Registration -------------------------------------------------- */
    
    /**
     * Registers this taglet at the given map.
     * 
     * @param map the map at which this taglet is registered.
     */
    public static void register(@Nonnull Map<String, Taglet> map) {
        Taglet.register(map, new Author());
    }
    
    /* -------------------------------------------------- Overrides -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean inOverview() {
        return true;
    }
    
    @Pure
    @Override
    public boolean inPackage() {
        return true;
    }
    
    @Pure
    @Override
    public boolean inType() {
        return true;
    }
    
    @Pure
    @Override
    public @Nonnull String getName() {
        return "author";
    }
    
    @Pure
    @Override
    public @Nonnull String getTitle() {
        return "Author";
    }
    
    /* -------------------------------------------------- Text -------------------------------------------------- */
    
    /**
     * Stores the pattern that a text needs to match in order to be displayed as an email address.
     */
    private static final @Nonnull Pattern pattern = Pattern.compile("(.+) \\((.+)\\)");
    
    /**
     * Rotates the given string by 13 characters.
     * 
     * @param input the string to be rotated.
     * 
     * @return the given string rotated by 13 characters.
     */
    @Pure
    private static @Nonnull String rot13(@Nonnull String input) {
        final @Nonnull StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if      (c >= 'a' && c <= 'm' || c >= 'A' && c <= 'M') c += 13;
            else if (c >= 'n' && c <= 'z' || c >= 'N' && c <= 'Z') c -= 13;
            output.append(c);
        }
        return output.toString();
    }
    
    @Pure
    @Override
    protected @Nonnull String getText(@Nonnull String text) {
        final @Nonnull Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            final boolean obfuscated = true;
            if (obfuscated) {
                return "<script type=\"text/javascript\">document.write(\"<n uers=\\\"znvygb:" + rot13(matcher.group(2)) + "\\\">" + rot13(matcher.group(1)) + "</n>\".replace(/[a-zA-Z]/g,function(c){return String.fromCharCode((c<=\"Z\"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));</script>";
            } else {
                return "<a href=\"mailto:" + matcher.group(2) + "\">" + matcher.group(1) + "</a>";
            }
        }
        else return text;
    }
    
}
