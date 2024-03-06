package com.example.weather.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class CityDto {

  Integer id;
  Integer idTara;
  String nume;
  Double lat;
  Double lon;
}
