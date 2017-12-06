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
package net.digitalid.utility.collaboration.enumerations;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the possible priorities of issues.
 */
@Immutable
public enum Priority {
    
    /**
     * The priority of the issue was not specified.
     */
    UNSPECIFIED,
    
    /**
     * The issue should be resolved within a few days.
     */
    HIGH,
    
    /**
     * The issue should be resolved within a few weeks.
     */
    MIDDLE,
    
    /**
     * The issue should be resolved within a few months.
     */
    LOW,
    
    /**
     * The issue is optional and does not have to be resolved.
     */
    OPTIONAL;
    
}
