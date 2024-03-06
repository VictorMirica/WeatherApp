package com.example.weather.repository;

import com.example.weather.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

  Optional<Country> findByNume(String numeTara);

  Optional<Country> findById(Integer id);

  void deleteById(Integer id);
}
