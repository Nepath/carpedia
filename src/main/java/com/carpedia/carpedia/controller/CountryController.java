package com.carpedia.carpedia.controller;

import com.carpedia.carpedia.model.CountryModel;
import com.carpedia.carpedia.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryRepository country;

    @RequestMapping("/savecountry")
    public String process(){
        // save a single Country
        country.save(new CountryModel("Poland"));
        country.save(new CountryModel("Germany"));
        country.save(new CountryModel("France"));
        country.save(new CountryModel("China"));
        country.save(new CountryModel("Korea"));
        country.save(new CountryModel("Russia"));
        return "Done";
    }

    @RequestMapping("/country")
    public String findAll(){
        String result = "";

        for(CountryModel country : country.findAll()){
            result += country.toString() + "<br>";
        }

        return result;
    }

    @RequestMapping("/country/id/{id}")
    public String fetchdataById(@PathVariable("id") long id){
        String result = "";
        result = country.findById(id).toString();
        return result;
    }

    @RequestMapping("/country/name/{name}")
    public String fetchDataByCompany(@PathVariable("name") String company){
        String result = "";

        for(CountryModel country: country.findByName(company)){
            result += country.toString() + "<br>";
        }

        return result;
    }

}