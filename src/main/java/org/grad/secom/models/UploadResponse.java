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

package org.grad.secom.models;

import org.grad.secom.models.enums.ResponseCodeEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Upload Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class UploadResponse {

    // Class Variables
    @NotNull
    private ResponseCodeEnum responseCode;
    @NotNull
    private String message;

    /**
     * Gets response code.
     *
     * @return the response code
     */
    public ResponseCodeEnum getResponseCode() {
        return responseCode;
    }

    /**
     * Sets response code.
     *
     * @param responseCode the response code
     */
    public void setResponseCode(ResponseCodeEnum responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
