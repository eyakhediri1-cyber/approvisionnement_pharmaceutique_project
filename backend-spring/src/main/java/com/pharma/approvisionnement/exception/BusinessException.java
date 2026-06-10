/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package com.pharma.approvisionnement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BusinessException
extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

