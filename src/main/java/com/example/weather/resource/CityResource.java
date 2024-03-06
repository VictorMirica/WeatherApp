package com.example.weather.resource;

import com.example.weather.dto.CityDto;
import com.example.weather.model.City;
import com.example.weather.service.CityService;
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

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cities")
@Api
@Produces(MediaType.APPLICATION_JSON)
@Component
@AllArgsConstructor
@Slf4j
public class CityResource {

	private final CityService cityService;
	private final CountryService countryService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiOperation(value = "Add a city")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 409, message = "City already exists", response = Error.class),
			@ApiResponse(code = 404, message = "Country not found", response = Error.class)})
	public ResponseEntity<String> addCity(@RequestBody CityDto cityDto) {
		if (cityDto.getNume() == null || cityDto.getLat() == null || cityDto.getLon() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!countryService.countryExistsId(cityDto.getIdTara())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (cityService.cityExists(cityDto)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		City city = cityService.addCity(cityDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("{\"id\":" + city.getId() + "}");
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(value = "Get all cities", response = City.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<City>> getCities() {
		return ResponseEntity.ok(cityService.getCities());
	}

	@RequestMapping(value = "country/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get cities by country id", response = City.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<City>> getCitiesByCountryId(@PathVariable Integer id) {
		return ResponseEntity.ok(cityService.getCitiesByCountryId(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a city")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 404, message = "City not found", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class)})
	public ResponseEntity<Void> updateCity(@PathVariable Integer id, @RequestBody CityDto cityDto) {
		if (cityDto.getNume() == null || cityDto.getLat() == null || cityDto.getLon() == null ||
				cityDto.getIdTara() == null || !Objects.equals(id, cityDto.getId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!cityService.cityExistsId(id) || !countryService.countryExistsId(cityDto.getIdTara())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (cityService.cityExistsinCountry(cityDto) && !cityService.getCityById(id).getNume().equals(cityDto.getNume())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		cityService.updateCity(id, cityDto);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a city")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 404, message = "City not found", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
	public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!cityService.cityExistsId(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		cityService.deleteCity(id);
		return ResponseEntity.ok().build();
	}
}
