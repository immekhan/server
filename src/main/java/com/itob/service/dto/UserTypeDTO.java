package com.itob.service.dto;

import com.itob.domain.UserType;

public class UserTypeDTO {

    Long id;
    String type;
    String role;

    public UserTypeDTO() {

    }

    public UserTypeDTO(UserType userType) {

        this.setId(userType.getId());
        this.setRole(userType.getIdRole());
        this.setType(userType.getUserType());
    }

    public UserTypeDTO(Long id, String type, String role) {
        this.id = id;
        this.type = type;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
