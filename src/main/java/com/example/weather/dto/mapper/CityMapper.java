package com.example.weather.dto.mapper;

import com.example.weather.dto.CityDto;
import com.example.weather.model.City;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CountryMapper.class}
)
@Component
public interface CityMapper {

  @Mapping(target = "id", ignore = true)
  City toEntity(CityDto cityDto);
}
