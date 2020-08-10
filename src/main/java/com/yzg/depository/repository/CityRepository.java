package com.yzg.depository.repository;

import com.yzg.depository.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CityRepository extends JpaRepository<City,Long> {

    City save(City city);

    void deleteById(Long id);

    List<City> findAll();

    Page<City> findAll(Pageable  pageable);

    List<City> findCityById(Long id);

    List<City> findByDescriptionAndScore(String description, Integer score);

    List<City> findByDescriptionOrScore(String description, Integer score);

    Page<City> findByDescription(String description, Pageable page);

    Page<City> findByDescriptionLike(String description, Pageable page);


}
