package com.example.weather.resource;

import com.example.weather.dto.TemperatureDto;
import com.example.weather.model.Temperature;
import com.example.weather.service.CityService;
import com.example.weather.service.TemperatureService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/temperatures")
@Api
@Produces(MediaType.APPLICATION_JSON)
@Component
@Slf4j
@AllArgsConstructor
public class TemperatureResource {

	private final TemperatureService temperatureService;
	private final CityService cityService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiOperation(value = "Add a temperature")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 409, message = "Temperature already exists", response = Error.class),
			@ApiResponse(code = 404, message = "City not found", response = Error.class)})
	public ResponseEntity<String> addTemperature(@RequestBody TemperatureDto temperatureDto) {
		if (temperatureDto.getValoare() == null || temperatureDto.getIdOras() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!cityService.cityExistsId(temperatureDto.getIdOras())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Temperature temperature = temperatureService.addTemperature(temperatureDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("{\"id\":" + temperature.getId() + "}");
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(value = "Get all temperatures", response = Temperature.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<Temperature>> getTemperatures(@RequestParam(required = false) Double lat, @RequestParam(required = false) Double lon,
									@RequestParam(required = false) String from, @RequestParam(required = false) String until) {
		if (lat == null && lon == null && from == null && until == null) {
			return ResponseEntity.ok(temperatureService.getAllTemperatures());
		}

		LocalDateTime startDate = null;
		if (from != null) {
			startDate = LocalDate.parse(from).atStartOfDay();
		}
		LocalDateTime endDate = null;
		if (until != null) {
			endDate = LocalDate.parse(until).atTime(23, 59, 59);
		}

		return ResponseEntity.ok(temperatureService.getTemperatures(lat, lon, startDate, endDate));
	}

	@RequestMapping(value = "/cities/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get temperatures by city id", response = Temperature.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<Temperature>> getTemperaturesByCityId(@PathVariable Integer id, @RequestParam(required = false) String from, @RequestParam(required = false) String until) {
		LocalDateTime startDate = null;
		if (from != null) {
			startDate = LocalDate.parse(from).atStartOfDay();
		}
		LocalDateTime endDate = null;
		if (until != null) {
			endDate = LocalDate.parse(until).atTime(23, 59, 59);
		}

		return ResponseEntity.ok(temperatureService.getTemperaturesByCityId(id, startDate, endDate));
	}

	@RequestMapping(value = "/countries/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get temperatures by country id", response = Temperature.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class)})
	public ResponseEntity<List<Temperature>> getTemperaturesByCountryId(@PathVariable Integer id, @RequestParam(required = false) String from, @RequestParam(required = false) String until) {
		LocalDateTime startDate = null;
		if (from != null) {
			startDate = LocalDate.parse(from).atStartOfDay();
		}
		LocalDateTime endDate = null;
		if (until != null) {
			endDate = LocalDate.parse(until).atTime(23, 59, 59);
		}

		return ResponseEntity.ok(temperatureService.getTemperaturesByCountryId(id, startDate, endDate));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a temperature")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 404, message = "Temperature not found", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class)})
	public ResponseEntity<Void> updateTemperature(@PathVariable Integer id, @RequestBody TemperatureDto temperatureDto) {
		if (temperatureDto.getValoare() == null || temperatureDto.getIdOras() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!temperatureService.temperatureExists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		temperatureService.updateTemperature(id, temperatureDto);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a temperature")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Something went wrong", response = Error.class),
			@ApiResponse(code = 404, message = "Temperature not found", response = Error.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
	public ResponseEntity<Void> deleteTemperature(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!temperatureService.temperatureExists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		temperatureService.deleteTemperature(id);
		return ResponseEntity.ok().build();
	}
}

