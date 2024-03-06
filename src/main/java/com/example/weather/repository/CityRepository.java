package com.example.weather.repository;

import com.example.weather.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

	Optional<City> findByNume(String numeOras);


	Optional<City> findById(Integer id);

	Optional<City> findByNumeAndCountry_Id(String numeOras, Integer idTara);

	void deleteById(Integer id);
}
