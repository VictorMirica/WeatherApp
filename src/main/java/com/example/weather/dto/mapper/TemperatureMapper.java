package com.example.weather.dto.mapper;

import com.example.weather.dto.TemperatureDto;
import com.example.weather.model.Temperature;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		uses = {CityMapper.class}
)
@Component
public interface TemperatureMapper {

	@Mapping(target = "id", ignore = true)
	Temperature toEntity(TemperatureDto temperatureDto);
}
