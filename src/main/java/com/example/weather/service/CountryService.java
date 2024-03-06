package com.example.weather.service;

import com.example.weather.dto.CountryDto;
import com.example.weather.dto.mapper.CountryMapper;
import com.example.weather.model.Country;
import com.example.weather.repository.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CountryService {

	private CountryRepository countryRepository;
	private CountryMapper countryMapper;

	public boolean countryExists(CountryDto country) {
		return countryRepository.findByNume(country.getNume()).isPresent();
	}

	public boolean countryExistsId(Integer id) {
		return countryRepository.findById(id).isPresent();
	}

	public Country addCountry(CountryDto country) {
		Country countryEntity = countryMapper.toEntity(country);
		return countryRepository.saveAndFlush(countryEntity);
	}

	public Country getCountryById(Integer id) {
		return countryRepository.findById(id).orElseThrow(() -> new NotFoundException("Country not found"));
	}

	public List<Country> getCountries() {
		return countryRepository.findAll();
	}

	public void updateCountry(Integer id, CountryDto countryDto) {
		Country country = countryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Country not found"));
		country.setNume(countryDto.getNume());
		country.setLat(countryDto.getLat());
		country.setLon(countryDto.getLon());
		countryRepository.saveAndFlush(country);
	}

	public void deleteCountry(Integer id) {
		countryRepository.deleteById(id);
	}
}
