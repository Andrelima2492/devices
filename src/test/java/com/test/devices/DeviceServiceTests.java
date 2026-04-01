package com.test.devices;

import com.test.devices.dto.DeviceDTO;
import com.test.devices.exceptions.DeviceInUseException;
import com.test.devices.exceptions.DeviceNotFoundException;
import com.test.devices.repository.DeviceRepository;
import com.test.devices.service.DeviceService;
import com.test.devices.util.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.test.devices.model.Device;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTests {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void shouldCreate(){
        DeviceDTO deviceDTO = new DeviceDTO(null, "PS5", "Sony", State.AVAILABLE, 2020);
        Device saved = new Device("PS5", "Sony", State.AVAILABLE, 2020);
       saved.setId(1);
        Mockito.when(deviceRepository.save(Mockito.any(Device.class))).thenReturn(saved);

        DeviceDTO result = deviceService.createDevice(deviceDTO);
        Assertions.assertEquals(1,result.getId());
        Assertions.assertEquals("PS5",result.getName());
        Assertions.assertEquals("Sony", result.getBrand());
        Assertions.assertEquals(State.AVAILABLE, result.getState());
        Assertions.assertEquals(2020,result.getCreationTime());
        Mockito.verify(deviceRepository).save(Mockito.any(Device.class));
    }

     @Test
    void shouldReturnDeviceById(){
        Device device = new Device("PS5", "Sony", State.AVAILABLE,2020);
        device.setId(1);

        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        DeviceDTO result = deviceService.getDeviceById(1);
         Assertions.assertEquals("PS5", result.getName());
         Assertions.assertEquals("Sony",result.getBrand());
         Assertions.assertEquals(State.AVAILABLE, result.getState());
         Assertions.assertEquals(2020,result.getCreationTime());
     }

     @Test
    void shouldThrowWhenDeviceNotFound(){
        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(DeviceNotFoundException.class, ()-> deviceService.getDeviceById(1));
     }

     @Test
    void shouldReturnDevicesByBrand(){
        Device device1 = new Device("PS5", "Sony", State.AVAILABLE, 2020);
        Device device2 = new Device("PS2", "Sony", State.AVAILABLE, 2000);
        Device device3 = new Device("iPhone 17","Apple",State.AVAILABLE,2025);

        Mockito.when(deviceRepository.findDevicesByBrand("Sony")).thenReturn(List.of(device1,device2));

        List<DeviceDTO> result = deviceService.getDevicesByBrand("Sony");
         Assertions.assertEquals(2,result.size());
          Assertions.assertEquals("Sony", result.get(0).getBrand());
          Assertions.assertEquals("Sony",result.get(1).getBrand());
     }

     @Test
    void shouldReturnDevicesByState(){
         Device device1 = new Device("PS5", "Sony", State.AVAILABLE, 2020);
         Device device2 = new Device("PS2", "Sony", State.AVAILABLE, 2000);
         Device device3 = new Device("iPhone 17","Apple",State.IN_USE,2025);

         Mockito.when(deviceRepository.findDevicesByState(State.IN_USE)).thenReturn(List.of(device3));

         List<DeviceDTO> result = deviceService.getDevicesByState("in-use");
         Assertions.assertEquals(1,result.size());
         Assertions.assertEquals("iPhone 17",result.getFirst().getName());
         Assertions.assertEquals("Apple",result.getFirst().getBrand());
         Assertions.assertEquals(State.IN_USE,result.getFirst().getState());
         Assertions.assertEquals(2025,result.getFirst().getCreationTime());
     }

     @Test
    void shouldUpdateDeviceWhenNotInUse(){
        Device device =  new Device("PS5", "Sony", State.AVAILABLE, 2020);
        device.setId(1);

        DeviceDTO deviceDTO = new DeviceDTO(null,"iPhone 17","Apple",State.AVAILABLE,2025);

        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        deviceService.updateDevice(deviceDTO,1);

        Assertions.assertEquals("iPhone 17", device.getName());
        Assertions.assertEquals("Apple", device.getBrand());
        Assertions.assertEquals(2020, device.getCreationTime());
        Assertions.assertEquals(State.AVAILABLE,deviceDTO.getState());

    }

    @Test
    void shouldNotUpdateInUse(){
        Device device =  new Device("PS5", "Sony", State.IN_USE, 2020);
        device.setId(1);

        DeviceDTO deviceDTO = new DeviceDTO(null,"iPhone 17","Apple",State.IN_USE,2025);

        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        deviceService.updateDevice(deviceDTO,1);

        Assertions.assertEquals("PS5",device.getName());
        Assertions.assertEquals("Sony",device.getBrand());
        Assertions.assertEquals(State.IN_USE,device.getState());
        Assertions.assertEquals(2020,device.getCreationTime());
    }

    @Test
    void shouldDeleteDeviceWhenNotInUse(){
        Device device =  new Device("PS5", "Sony", State.AVAILABLE, 2020);

        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        deviceService.deleteDevice(1);
        Mockito.verify(deviceRepository).deleteById(1);
    }

    @Test
    void shouldThrowWhenDeletingInUseException(){
        Device device =  new Device("PS5", "Sony", State.IN_USE, 2020);

        Mockito.when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        Assertions.assertThrows(DeviceInUseException.class,()->deviceService.deleteDevice(1));
    }
}
