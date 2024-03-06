package com.example.weather.service;

import com.example.weather.dto.TemperatureDto;
import com.example.weather.dto.mapper.TemperatureMapper;
import com.example.weather.model.Temperature;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.TemperatureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TemperatureService {

	private CityRepository cityRepository;
	private TemperatureRepository temperatureRepository;
	private TemperatureMapper temperatureMapper;

	public boolean temperatureExists(Integer id) {
		return temperatureRepository.findById(id).isPresent();
	}

	public List<Temperature> getAllTemperatures() {
		return temperatureRepository.findAll();
	}

	private List<Temperature> filterTemperatures(LocalDateTime startDate, LocalDateTime endDate, List<Temperature> temperatures) {
		if (startDate != null && endDate != null) {
			temperatures.removeIf(temperature -> temperature.getTimestamp().isBefore(startDate)
					|| temperature.getTimestamp().isAfter(endDate));
		} else if (startDate != null) {
			temperatures.removeIf(temperature -> temperature.getTimestamp().isBefore(startDate));
		} else if (endDate != null) {
			temperatures.removeIf(temperature -> temperature.getTimestamp().isAfter(endDate));
		}
		return temperatures;
	}

	public List<Temperature> getTemperatures(Double lat, Double len,
											 LocalDateTime startDate, LocalDateTime endDate) {
		List<Temperature> temperatures;

		if (lat != null && len != null) {
			temperatures = temperatureRepository.findAllByCity_LatAndCity_Lon(lat, len);
		} else if (lat != null) {
			temperatures = temperatureRepository.findAllByCity_Lat(lat);
		} else if (len != null) {
			temperatures = temperatureRepository.findAllByCity_Lon(len);
		} else {
			temperatures = temperatureRepository.findAll();
		}

		return filterTemperatures(startDate, endDate, temperatures);
	}

	public List<Temperature> getTemperaturesByCityId(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
		List<Temperature> temperatures = temperatureRepository.findAllByCity_Id(id);
		return filterTemperatures(startDate, endDate, temperatures);
	}

	public List<Temperature> getTemperaturesByCountryId(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
		List<Temperature> temperatures = temperatureRepository.findAllByCity_Country_Id(id);

		return filterTemperatures(startDate, endDate, temperatures);
	}

	public Temperature addTemperature(TemperatureDto temperature) {
		Temperature temperatureEntity = temperatureMapper.toEntity(temperature);
		temperatureEntity.setCity(cityRepository.findById(temperature.getIdOras()).orElseThrow(() -> new NotFoundException("City not found")));
		return temperatureRepository.saveAndFlush(temperatureEntity);
	}

	public void updateTemperature(Integer id, TemperatureDto temperatureDto) {
		Temperature temperature = temperatureRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Temperature not found"));
		temperature.setValoare(temperatureDto.getValoare());
		temperature.setCity(cityRepository.findById(temperatureDto.getIdOras()).orElseThrow(() -> new NotFoundException("City not found")));
		temperatureRepository.saveAndFlush(temperature);
	}

	public void deleteTemperature(Integer id) {
		temperatureRepository.deleteById(id);
	}
}
