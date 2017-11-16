package com.itob.service.dto;

import com.itob.domain.Country;

public class CountryDTO {

    Long id;
    String name;
    String code;

    public CountryDTO(){

    }

    public CountryDTO(Country country){
        this.setId(country.getId());
        this.setName(country.getCountry());
        this.setCode(country.getCountryCode());
    }


    public CountryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
