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
package net.digitalid.utility.generator.utility;

import java.util.Comparator;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.generator.information.field.FieldInformation;
import net.digitalid.utility.validation.annotations.generation.OrderOfAssignment;

/**
 *
 */
public class OrderOfAssignmentComparator implements Comparator<@Nonnull FieldInformation> {
    
    public static @Nonnull OrderOfAssignmentComparator INSTANCE = new OrderOfAssignmentComparator();
    
    @Pure
    @Override
    public int compare(@Nonnull FieldInformation fieldInformation1, @Nonnull FieldInformation fieldInformation2) {
        if (!fieldInformation1.hasAnnotation(OrderOfAssignment.class) && !fieldInformation2.hasAnnotation(OrderOfAssignment.class)) {
            return 0;
        } else if (!fieldInformation1.hasAnnotation(OrderOfAssignment.class)) {
            return 1;
        } else if (!fieldInformation2.hasAnnotation(OrderOfAssignment.class)) {
            return -1;
        } else {
            final @Nonnull OrderOfAssignment orderOfAssignment1 = fieldInformation1.getAnnotation(OrderOfAssignment.class);
            final @Nonnull OrderOfAssignment orderOfAssignment2 = fieldInformation2.getAnnotation(OrderOfAssignment.class);
            return orderOfAssignment1.value() - orderOfAssignment2.value();
        }
    }
    
}
