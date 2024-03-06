package com.example.weather.resource;

import com.example.weather.dto.CountryDto;
import com.example.weather.model.Country;
import com.example.weather.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Api
@Component
@AllArgsConstructor
@Slf4j
public class CountryResource {

	private final CountryService countryService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiOperation(value = "Add a country")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 409, message = "Country already exists", response = Error.class)})
	public ResponseEntity<String> addCountry(@RequestBody CountryDto countryDto) {
		if (countryDto.getNume() == null || countryDto.getLat() == null || countryDto.getLon() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (countryService.countryExists(countryDto)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		Country country = countryService.addCountry(countryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("{\"id\":" + country.getId() + "}");
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(value = "Get all countries", response = Country.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<Country>> getCountries() {
		return ResponseEntity.ok(countryService.getCountries());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a country")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 409, message = "Country already exists", response = Error.class),
			@ApiResponse(code = 404, message = "Country not found", response = Error.class)})
	public ResponseEntity<Void> updateCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto) {
		if (countryDto.getNume() == null || countryDto.getLat() == null || countryDto.getLon() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!countryService.countryExistsId(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (countryService.countryExists(countryDto) && !countryService.getCountryById(id).getNume().equals(countryDto.getNume())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		countryService.updateCountry(id, countryDto);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a country")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 404, message = "Country not found", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
	public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!countryService.countryExistsId(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		countryService.deleteCountry(id);
		return ResponseEntity.ok().build();
	}
}
