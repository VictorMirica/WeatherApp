package com.example.weather.repository;

import com.example.weather.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {

  Optional<Temperature> findById(Integer id);

  List<Temperature> findAllByCity_Lat(Double lat);

  List<Temperature> findAllByCity_Lon(Double lon);

  List<Temperature> findAllByCity_LatAndCity_Lon(Double lat, Double lon);

  List<Temperature> findAllByCity_Id(Integer id);

  List<Temperature> findAllByCity_Country_Id(Integer id);

  void deleteById(Integer id);
}
