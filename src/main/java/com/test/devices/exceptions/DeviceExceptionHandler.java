package com.test.devices.exceptions;

import com.test.devices.model.Device;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeviceExceptionHandler {
    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onDeviceNotExistsException(DeviceNotFoundException e){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(DeviceInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onDeviceInUseException(DeviceInUseException e){
        return  new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onInvalidStateException(InvalidStateException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
