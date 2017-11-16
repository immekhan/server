package com.itob.service.dto;

//todo delete this class it is used in UserResource --> just to convert the response

/**
 * A Car DTO
 */

public class CarDTO  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String price;

    private  String creation_date;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

}
