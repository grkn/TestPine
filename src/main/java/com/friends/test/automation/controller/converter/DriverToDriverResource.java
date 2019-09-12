package com.friends.test.automation.controller.converter;

import com.friends.test.automation.controller.resource.DriverResource;
import com.friends.test.automation.entity.Driver;
import org.springframework.core.convert.converter.Converter;

public class DriverToDriverResource implements Converter<Driver, DriverResource> {

    @Override
    public DriverResource convert(Driver source) {
        DriverResource driverResource = new DriverResource();
        driverResource.setAddress(source.getAddress());
        driverResource.setId(source.getId());
        driverResource.setName(source.getName());
        return driverResource;
    }
}
