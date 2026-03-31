package com.test.devices.service;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.exceptions.DeviceInUseException;
import com.test.devices.exceptions.DeviceNotFoundException;
import com.test.devices.model.Device;
import com.test.devices.repository.DeviceRepository;
import com.test.devices.util.State;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {
    private DeviceRepository deviceRepository;

    private DeviceDTO mapToDTO(Device device){
        return new DeviceDTO(device.getId(), device.getName(), device.getBrand(), device.getState(),
                device.getCreationTime());
    }

    private Device mapFromDTO(DeviceDTO deviceDTO){
      return new Device(deviceDTO.getName(), deviceDTO.getBrand(),
                deviceDTO.getState(),deviceDTO.getCreationTime());
    }
    public ResponseEntity<DeviceDTO> createDevice(DeviceDTO deviceDTO) {
        deviceRepository.save(mapFromDTO(deviceDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDTO);
    }

    public DeviceDTO getDeviceById(Integer id) {
        return mapToDTO(deviceRepository.findById(id).orElseThrow(()->
                new DeviceNotFoundException("No device found with id "+id)));
    }

    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<DeviceDTO> getDevicesByBrand(String brand) {
        return deviceRepository.findDevicesByBrand(brand).stream().map(this::mapToDTO).toList();
    }

    public List<DeviceDTO> getDevicesByState(String state) {
       return deviceRepository.findDevicesByState(State.fromString(state))
               .stream().map(this::mapToDTO).toList();
    }

    @Transactional
    public ResponseEntity<DeviceDTO> updateDevice(DeviceDTO deviceDTO, Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(()->
                new DeviceNotFoundException("No device found with id "+ id));
        device.setState(deviceDTO.getState());
        if(!State.IN_USE.equals(device.getState())){
            device.setName(deviceDTO.getName());
            device.setBrand(deviceDTO.getBrand());
        }
        DeviceDTO response = mapToDTO(device);
        deviceDTO.setId(device.getId());
        return ResponseEntity.ok(response);
    }

    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(()->
                new DeviceNotFoundException("No device found with id "+id));
        if(!State.IN_USE.equals(device.getState())){
                deviceRepository.deleteById(id);
        }else{
            throw new DeviceInUseException("Device with id "+ id +
                    " cannot be deleted because it is in use");
        }
    }
}
