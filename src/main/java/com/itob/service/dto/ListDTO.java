package com.itob.service.dto;

import java.util.List;

public class ListDTO {

    List<CountryDTO> countyList;
    List<CityDTO> cityList;
    List<UserTypeDTO> userTypeList;
    List<AbsUserDTO> userList;

    public ListDTO() {

    }

    public ListDTO(List<CountryDTO> countyList,
                   List<CityDTO> cityList,
                   List<UserTypeDTO> userTypeList,
                   List<AbsUserDTO> userList) {

        this.countyList = countyList;
        this.cityList = cityList;
        this.userTypeList = userTypeList;
        this.userList = userList;
    }

    public List<CountryDTO> getCountyList() {
        return countyList;
    }

    public void setCountyList(List<CountryDTO> countyList) {
        this.countyList = countyList;
    }

    public List<CityDTO> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityDTO> cityList) {
        this.cityList = cityList;
    }

    public List<UserTypeDTO> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(List<UserTypeDTO> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public List<AbsUserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<AbsUserDTO> userList) {
        this.userList = userList;
    }
}
