package com.test.devices.controller;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@AllArgsConstructor
public class DeviceController {
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO){
       return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(deviceDTO));
    }

    @GetMapping("/id/{id}")
    public DeviceDTO getDeviceById(@PathVariable Integer id){
        return deviceService.getDeviceById(id);
    }

    @GetMapping
    public List<DeviceDTO> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/brand/{brand}")
    public List<DeviceDTO> getDevicesByBrand(@PathVariable String brand){
        return deviceService.getDevicesByBrand(brand);
    }

    @GetMapping("/devices/state/{state}")
    public List<DeviceDTO> getDevicesByState(@PathVariable String state){
        return deviceService.getDevicesByState(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable Integer id){
       return ResponseEntity.ok().body(deviceService.updateDevice(deviceDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer id){
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

}
