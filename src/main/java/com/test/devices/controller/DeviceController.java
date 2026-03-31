package com.test.devices.controller;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DeviceController {
    private DeviceService deviceService;

    @PostMapping("/device")
    public void createDevice(@RequestBody DeviceDTO deviceDTO){
        deviceService.createDevice(deviceDTO);
    }

    @GetMapping("/device/id/{id}")
    public DeviceDTO getDeviceById(@PathVariable Integer id){
        return deviceService.getDeviceById(id);
    }

    @GetMapping("/devices")
    public List<DeviceDTO> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/devices/brand/{brand}")
    public List<DeviceDTO> getDevicesByBrand(@PathVariable String brand){
        return deviceService.getDevicesByBrand(brand);
    }

    @GetMapping("/devices/state/{state}")
    public List<DeviceDTO> getDevicesByState(@PathVariable String state){
        return deviceService.getDevicesByState(state);
    }

    @PutMapping("/device/{id}")
    public void updateDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable Integer id){
        deviceService.updateDevice(deviceDTO, id);
    }

    @DeleteMapping("/device/{id}")
    public void deleteDevice(@PathVariable Integer id){
        deviceService.deleteDevice(id);
    }

}
