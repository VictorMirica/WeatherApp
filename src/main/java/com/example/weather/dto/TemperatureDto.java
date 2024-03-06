package com.example.weather.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class TemperatureDto {

	Integer id;
	Integer idOras;
	Double valoare;
}
