package com.friends.test.automation.controller.converter;

import com.friends.test.automation.controller.dto.DriverDto;
import com.friends.test.automation.entity.Driver;
import org.springframework.core.convert.converter.Converter;

public class DriverDtoToDriver implements Converter<DriverDto,Driver> {

    @Override
    public Driver convert(DriverDto source) {
        Driver driver = new Driver();
        driver.setAddress(source.getAddress());
        driver.setPort(source.getPort());
        driver.setId(source.getId());
        return driver;
    }
}
