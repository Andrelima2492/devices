package com.test.devices.exceptions;

public class DeviceInUseException extends RuntimeException {
    public DeviceInUseException(String message) {
        super(message);
    }
}
