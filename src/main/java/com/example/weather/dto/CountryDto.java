package com.example.weather.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class CountryDto {

  String nume;
  Double lat;
  Double lon;
}
