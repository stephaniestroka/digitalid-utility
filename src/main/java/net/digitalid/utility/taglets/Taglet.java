package net.digitalid.utility.taglets;

import com.sun.javadoc.Tag;
import java.beans.Introspector;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class serves as a template for custom block tags.
 */
@Stateless
public abstract class Taglet implements com.sun.tools.doclets.Taglet {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Registration –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Registers the given taglet at the given map.
     * 
     * @param map the map at which the taglet is registered.
     * @param taglet the taglet to be registered at the map.
     */
    static void register(@Nonnull Map<String, Taglet> map, @Nonnull Taglet taglet) {
        // System.out.println("Registering: " + taglet.getName());
        try {
            final @Nonnull String name = taglet.getName();
            final @Nullable Object other = map.get(name);
            if (other != null) { map.remove(name); }
            map.put(name, taglet);
        } catch (@Nonnull Throwable throwable) {
            System.err.println("Failed to register taglet '" + taglet.getName() + "':");
            throwable.printStackTrace();
            throw throwable;
        }
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Default Methods –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public boolean inField() {
        return false;
    }
    
    @Pure
    @Override
    public boolean inConstructor() {
        return false;
    }
    
    @Pure
    @Override
    public boolean inMethod() {
        return false;
    }
    
    @Pure
    @Override
    public boolean inOverview() {
        return false;
    }
    
    @Pure
    @Override
    public boolean inPackage() {
        return false;
    }
    
    @Pure
    @Override
    public boolean inType() {
        return false;
    }
    
    @Pure
    @Override
    public boolean isInlineTag() {
        return false;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Name –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public abstract @Nonnull String getName();
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Title –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Returns the title of this taglet.
     * 
     * @return the title of this taglet.
     */
    @Pure
    protected abstract @Nonnull String getTitle();
    
    /**
     * Returns the title of this taglet with HTML formatting.
     * 
     * @return the title of this taglet with HTML formatting.
     */
    @Pure
    private @Nonnull String getTitleWithHTML() {
        return "<dt><span class=\"strong\">" + getTitle() + ":</span></dt>\n";
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Text –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Stores the pattern that a text needs to match in order to be displayed as code.
     */
    private static final @Nonnull Pattern pattern = Pattern.compile("(.+) : \"(.+)\";");
    
    /**
     * Formats the given text.
     * 
     * @param text the text to format.
     * 
     * @return the formatted text.
     */
    @Pure
    protected @Nonnull String getText(@Nonnull String text) {
        final @Nonnull Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) return "<code> " + matcher.group(1) + "</code> - " + Introspector.decapitalize(matcher.group(2));
        else return text;
    }
    
    /**
     * Formats the given text with HTML.
     * 
     * @param text the text to format.
     * 
     * @return the formatted text with HTML.
     */
    @Pure
    private @Nonnull String getTextWithHTML(@Nonnull String text) {
        return "<dd>" + getText(text) + "</dd>\n";
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Output –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Returns the string representation of the given tag, which is output to the generated page.
     * 
     * @param tag the tag to be formatted as a string.
     * 
     * @return the string representation of the given tag, which is output to the generated page.
     */
    @Pure
    @Override
    public @Nonnull String toString(@Nonnull Tag tag) {
        return getTitleWithHTML()+ getTextWithHTML(tag.text());
    }
    
    /**
     * Returns the string representation of the given tags, which is output to the generated page.
     * 
     * @param tags the tags to be formatted as a string.
     * 
     * @return the string representation of the given tags, which is output to the generated page.
     */
    @Pure
    @Override
    public @Nonnull String toString(@Nonnull Tag[] tags) {
        if (tags.length == 0) return "";
        final @Nonnull StringBuilder string = new StringBuilder(getTitleWithHTML());
        for (final @Nonnull Tag tag : tags) string.append(getTextWithHTML(tag.text()));
        return string.toString();
    }
    
}
