package com.yzg.depository.controller;


import com.yzg.depository.domain.City;
import com.yzg.depository.service.imp.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value="/api/city/update",method= RequestMethod.PUT)
    public String updateCity(@RequestBody City city){
        City result= cityService.saveCity(city);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @RequestMapping(value="/api/city/add",method= RequestMethod.POST)
    public String createCity(@RequestBody City city){
        City result= cityService.saveCity(city);
        if(result != null){
            return "success";
        }else{
            return "error";
        }
    }

    @RequestMapping(value="/api/city/all/find",method=RequestMethod.GET)
    public List<City> findAllCity(){
        return cityService.findAll();
    }

    @RequestMapping(value="/api/city/all/find/{page}/{size}",method=RequestMethod.GET)
    public Page<City> findAllCity(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return cityService.findAll(request);
    }

    @RequestMapping(value="/api/city/all/findById",method=RequestMethod.GET)
    public List<City> findCityById(@RequestParam(value="id") Long Id){
        return cityService.findCityById(Id);
    }

    @RequestMapping(value="/api/city/and/find",method=RequestMethod.GET)
    public List<City> findByDescriptionAndScore(@RequestParam(value="description") String description,
                                                @RequestParam(value="score")Integer score){
        return cityService.findByDescriptionAndScore(description, score);
    }

    @RequestMapping(value="/api/city/or/find",method=RequestMethod.GET)
    public List<City> findByDescriptionOrScore(@RequestParam(value="description") String description,
                                                @RequestParam(value="score")Integer score){
        return cityService.findByDescriptionOrScore(description, score);
    }

    @RequestMapping(value = "/api/city/description/find", method = RequestMethod.GET)
    public List<City> findByDescription(@RequestParam(value = "description") String description) {
        return cityService.findByDescription(description);
    }

    @RequestMapping(value = "/api/city/like/find", method = RequestMethod.GET)
    public List<City> findByDescriptionLike(@RequestParam(value = "description") String description) {
        return cityService.findByDescriptionLike(description);
    }

    @DeleteMapping(value = "/api/city/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){

        cityService.deleteById(id);
        return "success";
    }
}
