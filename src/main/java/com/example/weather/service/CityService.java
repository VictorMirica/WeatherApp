package com.example.weather.service;

import com.example.weather.dto.CityDto;
import com.example.weather.dto.mapper.CityMapper;
import com.example.weather.model.City;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CityService {

	private CountryRepository countryRepository;
	private CityRepository cityRepository;
	private CityMapper cityMapper;

	public boolean cityExists(CityDto city) {
		return cityRepository.findByNume(city.getNume()).isPresent();
	}

	public boolean cityExistsId(Integer id) {
		return cityRepository.findById(id).isPresent();
	}

	public boolean cityExistsinCountry(CityDto cityDto) {
		return cityRepository.findByNumeAndCountry_Id(cityDto.getNume(), cityDto.getIdTara()).isPresent();
	}

	public City addCity(CityDto city) {
		City cityEntity = cityMapper.toEntity(city);
		cityEntity.setCountry(countryRepository.findById(city.getIdTara()).orElseThrow(() -> new NotFoundException("Country not found")));
		return cityRepository.saveAndFlush(cityEntity);
	}

	public City getCityById(Integer id) {
		return cityRepository.findById(id).orElseThrow(() -> new NotFoundException("City not found"));
	}

	public List<City> getCities() {
		List<City> list = cityRepository.findAll();
		for (City city : list) {
			city.setIdTara(city.getCountry().getId());
		}
		return list;
	}

	public List<City> getCitiesByCountryId(Integer id) {
		List<City> list = countryRepository.findById(id).orElseThrow(() -> new NotFoundException("Country not found")).getCities();
		for (City city : list) {
			city.setIdTara(city.getCountry().getId());
		}
		return list;
	}

	public void updateCity(Integer id, CityDto cityDto) {
		City city = cityRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("City not found"));
		city.setNume(cityDto.getNume());
		city.setLat(cityDto.getLat());
		city.setLon(cityDto.getLon());
		city.setCountry(countryRepository.findById(cityDto.getIdTara()).orElseThrow(() -> new NotFoundException("Country not found")));
		cityRepository.saveAndFlush(city);
	}

	public void deleteCity(Integer id) {
		cityRepository.deleteById(id);
	}
}
