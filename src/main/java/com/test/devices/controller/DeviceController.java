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

    /**
     * Creates a new Device.
     * @param deviceDTO the device information to create.
     * @return ResponseEntity with the created device.
     */
    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO){
       return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(deviceDTO));
    }

    /**
     * Fetches a specific Device.
     * @param id the id of the device we want to fetch.
     * @return the device.
     */
    @GetMapping("/id/{id}")
    public DeviceDTO getDeviceById(@PathVariable Integer id){
        return deviceService.getDeviceById(id);
    }

    /**
     * Fetches all devices.
     * @return all the devices.
     */
    @GetMapping
    public List<DeviceDTO> getAllDevices(){
        return deviceService.getAllDevices();
    }

    /**
     * Fetch devices by brand.
     * @param brand the specific brand to fetch.
     * @return all the devices from that brand.
     */
    @GetMapping("/brand/{brand}")
    public List<DeviceDTO> getDevicesByBrand(@PathVariable String brand){
        return deviceService.getDevicesByBrand(brand);
    }

    /**
     * Fetch devices by state
     * @param state the specific state to fetch.
     * @return all the devices in that state.
     */
    @GetMapping("/devices/state/{state}")
    public List<DeviceDTO> getDevicesByState(@PathVariable String state){
        return deviceService.getDevicesByState(state);
    }

    /**
     * Fully or partially updates a device.
     * - In-use devices will not have their name or brand updated.
     * - Creation time cannot be updated.
     * @param deviceDTO the device information to update.
     * @param id the id of the device to update.
     * @return ResponseEntity with the updated device.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable Integer id){
       return ResponseEntity.ok().body(deviceService.updateDevice(deviceDTO, id));
    }

    /**
     * Deletes a specific device.
     * In-use devices will not be deleted. Instead it will throw a DeviceInUseException.
     * @param id the id of the device we want to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer id){
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

}
