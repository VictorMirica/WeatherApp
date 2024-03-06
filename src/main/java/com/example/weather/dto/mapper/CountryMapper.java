package com.example.weather.dto.mapper;

import com.example.weather.dto.CountryDto;
import com.example.weather.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CountryMapper {

  @Mapping(target = "id", ignore = true)
  Country toEntity(CountryDto countryDto);
}
