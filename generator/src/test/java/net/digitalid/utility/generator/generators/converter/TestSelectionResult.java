package net.digitalid.utility.generator.generators.converter;

import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.zip.Inflater;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.conversion.converter.SelectionResult;
import net.digitalid.utility.functional.failable.FailableProducer;
import net.digitalid.utility.logging.exceptions.ExternalException;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.Size;

/**
 * The selection result that is used to test the functionality of the converter.
 */
class TestSelectionResult implements SelectionResult<ExternalException> {
    
    final @Nonnull Queue<@Nonnull Object> selectedObjects;
    
    TestSelectionResult(@Nonnull Queue<@Nonnull Object> selectedObjects) {
        this.selectedObjects = selectedObjects;
    }
    
    @Impure
    @Override
    public void getEmpty() {
        
    }
    
    @Impure
    @Override
    public boolean getBoolean() {
        return (boolean) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public byte getInteger08() {
        return (byte) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public short getInteger16() {
        return (short) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public int getInteger32() {
        return (int) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public long getInteger64() {
        return (long) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable BigInteger getInteger() {
        return (BigInteger) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public float getDecimal32() {
        return (float) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public double getDecimal64() {
        return (double) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public char getString01() {
        return (char) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @MaxSize(64) String getString64() {
        return (String) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable String getString() {
        return (String) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(16) byte[] getBinary128() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable @Size(32) byte[] getBinary256() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public @Nullable byte[] getBinary() {
        return (byte[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> List<T> getList(@Nonnull FailableProducer<T, ExternalException> function) {
        return (List<T>) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> T[] getArray(@Nonnull FailableProducer<T, ExternalException> function) {
        return (T[]) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <T> Set<T> getSet(@Nonnull FailableProducer<T, ExternalException> function) {
        return (Set<T>) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public <K, V> Map<K, V> getMap(@Nonnull FailableProducer<K, ExternalException> keyFunction, FailableProducer<V, ExternalException> valueFunction) {
        return (Map<K, V>) selectedObjects.poll();
    }
    
    @Impure
    @Override
    public void setDecryptionCipher(@Nonnull Cipher cipher) {
        // TODO: implement if we have a test for that.
    }
    
    @Impure
    @Override public void popDecryptionCipher() throws ExternalException {
        
    }
    
    @Impure
    @Override
    public void setDecompression(@Nonnull Inflater inflater) {
        // TODO: implement if we have a test for that.
    }
    
    @Impure
    @Override
    public void popDecompression() throws ExternalException {
        
    }
    
    @Impure
    @Override
    public void setSignatureDigest(@Nonnull MessageDigest digest) throws ExternalException {
        
    }
    
    @Impure
    @Override
    public @Nonnull DigestInputStream popSignatureDigest() throws ExternalException {
        return null;
    }
    
}
