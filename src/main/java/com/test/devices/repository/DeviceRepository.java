package com.test.devices.repository;

import com.test.devices.model.Device;
import com.test.devices.util.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findDevicesByBrand(String brand);
    List<Device> findDevicesByState(State state);
}
