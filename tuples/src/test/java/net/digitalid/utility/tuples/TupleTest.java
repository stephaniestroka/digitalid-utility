/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.tuples;

import javax.annotation.Nonnull;

import org.junit.Assert;

public class TupleTest {
    
    public void assertComparisonsWithEqualTuples(@Nonnull Tuple firstTuple, @Nonnull Tuple secondTuple) {
        Assert.assertTrue(firstTuple.isEqualTo(secondTuple));
        Assert.assertTrue(secondTuple.isEqualTo(firstTuple));
        
        Assert.assertFalse(firstTuple.isLessThan(secondTuple));
        Assert.assertTrue(firstTuple.isLessThanOrEqualTo(secondTuple));
        
        Assert.assertFalse(firstTuple.isGreaterThan(secondTuple));
        Assert.assertTrue(firstTuple.isGreaterThanOrEqualTo(secondTuple));
        
        Assert.assertFalse(secondTuple.isLessThan(firstTuple));
        Assert.assertTrue(secondTuple.isLessThanOrEqualTo(firstTuple));
        
        Assert.assertFalse(secondTuple.isGreaterThan(firstTuple));
        Assert.assertTrue(secondTuple.isGreaterThanOrEqualTo(firstTuple));
    }
    
    public void assertComparisonsWithUnequalTuples(@Nonnull Tuple smallerTuple, @Nonnull Tuple biggerTuple) {
        Assert.assertTrue(smallerTuple.isEqualTo(smallerTuple));
        Assert.assertTrue(biggerTuple.isEqualTo(biggerTuple));
        
        Assert.assertFalse(smallerTuple.isEqualTo(biggerTuple));
        Assert.assertFalse(biggerTuple.isEqualTo(smallerTuple));
        
        Assert.assertTrue(smallerTuple.isLessThan(biggerTuple));
        Assert.assertTrue(smallerTuple.isLessThanOrEqualTo(biggerTuple));
        
        Assert.assertFalse(smallerTuple.isGreaterThan(biggerTuple));
        Assert.assertFalse(smallerTuple.isGreaterThanOrEqualTo(biggerTuple));
        
        Assert.assertFalse(biggerTuple.isLessThan(smallerTuple));
        Assert.assertFalse(biggerTuple.isLessThanOrEqualTo(smallerTuple));
        
        Assert.assertTrue(biggerTuple.isGreaterThan(smallerTuple));
        Assert.assertTrue(biggerTuple.isGreaterThanOrEqualTo(smallerTuple));
    }
    
}
