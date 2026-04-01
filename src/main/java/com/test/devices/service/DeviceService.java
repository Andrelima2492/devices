package com.test.devices.service;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.exceptions.DeviceInUseException;
import com.test.devices.exceptions.DeviceNotFoundException;
import com.test.devices.model.Device;
import com.test.devices.repository.DeviceRepository;
import com.test.devices.util.State;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {
    private DeviceRepository deviceRepository;

    /**
     * Auxiliary method to convert a Device to its DTO.
     * @param device the device to be converted
     * @return the converted DTO of said device.
     */
    private DeviceDTO mapToDTO(Device device){
        return new DeviceDTO(device.getId(), device.getName(), device.getBrand(), device.getState(),
                device.getCreationTime());
    }

    /**
     * Auxiliary method to convert a DTO into a Device entity.
     * @param deviceDTO the dto of a Device entity.
     * @return tbe converted Device entity.
     */
    private Device mapFromDTO(DeviceDTO deviceDTO){
      return new Device(deviceDTO.getName(), deviceDTO.getBrand(),
                deviceDTO.getState(),deviceDTO.getCreationTime());
    }

    /**
     * Method to create a device in database.
     * @param deviceDTO the DTO of the device we want to create
     * @return a DTO of the created device.
     */
    public DeviceDTO createDevice(DeviceDTO deviceDTO) {
        Device saved= deviceRepository.save(mapFromDTO(deviceDTO));
        return mapToDTO(saved);
    }

    /**
     * Gets a device by its unique Id.
     * @param id the device's database id.
     * @return the DTO of the device.
     */
    public DeviceDTO getDeviceById(Integer id) {
        return mapToDTO(deviceRepository.findById(id).orElseThrow(()->
                new DeviceNotFoundException("No device found with id "+id)));
    }

    /**
     * Fetches a list of all the devices in the database.
     * @return a list of all devices saved.
     */
    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    /**
     * Fetches a list of all the devices that have the brand sent.
     * @param brand the brand of devices we want to filter by.
     * @return a list of all the devices of that brand.
     */
    public List<DeviceDTO> getDevicesByBrand(String brand) {
        return deviceRepository.findDevicesByBrand(brand).stream().map(this::mapToDTO).toList();
    }

    /**
     * Fetches a list of all the devices that are in the specified State.
     * @param state the state in which a device is (can be available, in-use or inactive)
     * @return all the devices that are in the recieved state.
     */
    public List<DeviceDTO> getDevicesByState(String state) {
       return deviceRepository.findDevicesByState(State.fromString(state))
               .stream().map(this::mapToDTO).toList();
    }

    /**
     * Updates a device with the information received form DTO:
     * - creationTime cannot be updated.
     * - if the device is in-use its name and brand cannot be updated.
     * @param deviceDTO a DTO of the device with the changes we want.
     * @param id the id of the device to update.
     * @return a DTO of the updated device.
     */
    @Transactional
    public DeviceDTO updateDevice(DeviceDTO deviceDTO, Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(()->
                new DeviceNotFoundException("No device found with id "+ id));
        device.setState(deviceDTO.getState());
        if(!State.IN_USE.equals(device.getState())){
            device.setName(deviceDTO.getName());
            device.setBrand(deviceDTO.getBrand());
        }
       return mapToDTO(device);
    }

    /**
     * Deletes a specific device:
     * - in-use devices will not be deleted and instead will throw an exception.
     * @param id the id of the device we want to delete.
     */
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
