package com.yzg.depository.service.imp;

import com.yzg.depository.domain.City;
import com.yzg.depository.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityESServiceImpl implements CityService{

    private static final Logger LOGGER= LoggerFactory.getLogger(CityESServiceImpl.class);

    //分页参数，代码可以迁移到具体项目的公共common模块
    private static final Integer pageNumber=0;
    private static final Integer pageSize=10;
    Pageable pageable=PageRequest.of (pageNumber,pageSize);

    @Autowired
    CityRepository cityRepository;

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    @Override
    public List<City> findCityById(Long id) {
        return cityRepository.findCityById(id);
    }

    @Override
    public List<City> findByDescriptionAndScore(String description, Integer score) {
        return cityRepository.findByDescriptionAndScore(description,score);
    }

    @Override
    public List<City> findByDescriptionOrScore(String description, Integer score) {
        return cityRepository.findByDescriptionOrScore(description,score);
    }

    @Override
    public List<City> findByDescription(String description) {
        return cityRepository.findByDescription(description,pageable).getContent();
    }

    @Override
    public List<City> findByDescriptionLike(String description) {
        return cityRepository.findByDescriptionLike(description, pageable).getContent();
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
