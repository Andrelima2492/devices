package com.test.devices.dto;

import com.test.devices.util.State;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing the Device entity.
 */
@Data
@AllArgsConstructor
public class DeviceDTO {
    private Integer id;
    private String name;
    private String brand;
    private State state;
    private Integer creationTime;
}
