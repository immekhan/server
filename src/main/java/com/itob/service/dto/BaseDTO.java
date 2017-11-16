package com.itob.service.dto;

import java.time.Instant;

public class BaseDTO {

    private String idOrgUnit;

    private Long createdBy;

    private Instant createdDate;

    private Long lastModifiedBy;

    private Instant lastModifiedDate;

    public String getIdOrgUnit() {
        return idOrgUnit;
    }

    public void setIdOrgUnit(String idOrgUnit) {
        this.idOrgUnit = idOrgUnit;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
