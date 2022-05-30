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

package org.grad.secom.interfaces;

import org.grad.secom.exceptions.SecomGenericException;
import org.grad.secom.exceptions.SecomNotAuthorisedException;
import org.grad.secom.exceptions.SecomNotFoundException;
import org.grad.secom.models.GetSummaryResponseObject;
import org.grad.secom.models.enums.ContainerTypeEnum;
import org.grad.secom.models.enums.SECOM_DataProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * The SECOM Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetSummaryInterface extends GenericInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String GET_SUMMARY_INTERFACE_PATH = "/v1/object/summary";

    /**
     * GET /v1/object/summary :  A list of information shall be returned from
     * this interface. The summary contains identity, status and short
     * description of each information object. The actual information object
     * shall be retrieved using the Get interface.
     *
     * @param containerType the object data container type
     * @param dataProductType the object data product type
     * @param productVersion the object data product version
     * @param geometry the object geometry
     * @param unlocode the object UNLOCODE
     * @param validFrom the object valid from time
     * @param validTo the object valid to time
     * @param pageable the pageable information
     * @return the summary response object
     */
    @GetMapping(GET_SUMMARY_INTERFACE_PATH)
    ResponseEntity<GetSummaryResponseObject> getSummary(@RequestParam(value = "containerType", required = false) ContainerTypeEnum containerType,
                                                        @RequestParam(value = "dataProductType", required = false) SECOM_DataProductType dataProductType,
                                                        @RequestParam(value = "productVersion", required = false) String productVersion,
                                                        @RequestParam(value = "geometry", required = false) String geometry,
                                                        @RequestParam(value = "unlocode", required = false) @Valid @Pattern(regexp = "[A-Z]{5}") String unlocode,
                                                        @RequestParam(value = "validFrom", required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validFrom,
                                                        @RequestParam(value = "validTo", required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime validTo,
                                                        @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({
            SecomGenericException.class,
            ValidationException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    default ResponseEntity<Object> handleGetSummaryInterfaceExceptions(Exception ex,
                                                                       HttpServletRequest request,
                                                                       HttpServletResponse response) {
        // Create the get summary response
        HttpStatus httpStatus;
        GetSummaryResponseObject getSummaryResponseObject = new GetSummaryResponseObject();

        // Handle according to the exception type
        if(ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            getSummaryResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            getSummaryResponseObject.setResponseText("Not authorized to requested information");
        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            getSummaryResponseObject.setResponseText("Information not found");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            getSummaryResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(getSummaryResponseObject);
    }

}
