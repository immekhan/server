package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Company Device Type.
 */
@Entity
@Table(name = "COMPANY_DEVICE_TYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDeviceType extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_APP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ID_COMPANY" , nullable = false)
    private String idCompany;

    @Column(name = "ID_DEVICE_TYPE" , nullable = false)
    private Long idDeviceType;

    @Column(name = "ID_ORGUNIT" , nullable = false)
    private String idOrgUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public Long getIdDeviceType() {
        return idDeviceType;
    }

    public void setIdDeviceType(Long idDeviceType) {
        this.idDeviceType = idDeviceType;
    }

    public String getIdOrgUnit() {
        return idOrgUnit;
    }

    public void setIdOrgUnit(String idOrgUnit) {
        this.idOrgUnit = idOrgUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDeviceType deviceType = (CompanyDeviceType) o;
        return !(deviceType.getId() == null || getId() == null) && Objects.equals(getId(), deviceType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCompanyDeviceType : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, idDeviceType : \'" + idDeviceType + '\'' +
            "\n\t, idCompany : \'" + idCompany + '\'' +
            "\n\t, idOrgUnit : \'" + idOrgUnit + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n};";
    }
}
