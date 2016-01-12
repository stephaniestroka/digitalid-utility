/**
 * Provides classes to support multi-argument custom block tags in the Javadoc generated for this project.
 * <p>
 * The options for the Javadoc Generator are (please note that you need to adapt the paths):
 * <p>
 * {@code -quiet -linksource -taglet net.digitalid.taglets.Author -taglet net.digitalid.taglets.Require -taglet net.digitalid.taglets.Ensure -taglet net.digitalid.taglets.Invariant -tagletpath "/DigitalID/build/classes" -link "http://docs.oracle.com/javase/7/docs/api/" -overview "/DigitalID/src/overview.html" -doctitle "Digital ID Reference Implementation"}
 * <p>
 * or, if the custom taglets are not working,
 * <p>
 * {@code -quiet -linksource -tag require:cm:"Requires:" -tag ensure:cm:"Ensures:" -tag invariant:tf:"Invariant:" -link "http://docs.oracle.com/javase/7/docs/api/" -overview "/DigitalID/src/overview.html" -doctitle "Digital ID Reference Implementation"}
 * 
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/javadoc.html">Javadoc Documentation</a>
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/javadoc/taglet/overview.html">Taglet Overview</a>
 */
package net.digitalid.utility.taglets;
