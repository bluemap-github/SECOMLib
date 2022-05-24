/*
 * Copyright (c) 2022 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.secom.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * The SECOM Acknowledgement Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum AckEnum {
    DELIVERED_ACK(1),
    OPENED_ACK(2),
    ERROR(3);

    // Enum Variables
    private final int value;

    /**
     * Enum Constructor
     *
     * @param newValue the enum value
     */
    AckEnum(final int newValue) {
        value = newValue;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @JsonValue
    public int getValue() { return value; }

    /**
     * Find the enum entry that corresponds to the provided value.
     *
     * @param value the enum value
     * @return The respective enum entry
     */
    public static AckEnum fromValue(int value) {
        return Arrays.stream(AckEnum.values())
                .filter(t -> t.getValue() == value)
                .findFirst()
                .orElse(null);
    }
}
