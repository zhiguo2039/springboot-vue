package com.yzg.depository.service.imp;

import com.yzg.depository.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*
* 城市ES查询业务接口类
*
* */
public interface CityService {
    City saveCity(City city);
    void deleteById(Long id);
    List<City> findAll();
    Page<City> findAll(Pageable pageable);
    List<City> findCityById(Long id);
    List<City> findByDescriptionAndScore(String description, Integer score);
    List<City> findByDescriptionOrScore(String description,Integer score);
    List<City> findByDescription(String description);
    List<City> findByDescriptionLike(String description);

}
