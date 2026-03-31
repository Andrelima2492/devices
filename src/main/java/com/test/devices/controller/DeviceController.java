package com.test.devices.controller;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DeviceController {
    private DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO){
       return deviceService.createDevice(deviceDTO);
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
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable Integer id){
       return deviceService.updateDevice(deviceDTO, id);
    }

    @DeleteMapping("/device/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer id){
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

}
