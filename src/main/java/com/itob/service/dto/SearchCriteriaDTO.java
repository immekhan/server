package com.itob.service.dto;

public class SearchCriteriaDTO {

    //all kind of search criteria beans  belong here
    Long [] userTypeList;
    Long idCity;
    Long idCountry;
    String roleCategory;

    public String getRoleCategory() {
        return roleCategory;
    }

    public void setRoleCategory(String roleCategory) {
        this.roleCategory = roleCategory;
    }

    public Long[] getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(Long[] userTypeList) {
        this.userTypeList = userTypeList;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public Long getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
    }
}
