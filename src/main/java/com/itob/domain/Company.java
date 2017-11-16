package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "COMPANY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_COMPANY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private String id;

    @Column(name = "STR_COMPANY_NAME" , nullable = false)
    private String companyName;

    @Column(name = "ID_COMPANY_TYPE" , nullable = false)
    private Long companyType;

    @Column(name = "STR_EMAIL" )
    private String email;

    @Basic(optional = false)
    @Column(name = "BOL_IS_ACTIVE", nullable = false)
    private Character dbActive = Character.valueOf('Y');

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Long companyType) {
        this.companyType = companyType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Character getDbActive() {
        return dbActive;
    }

    public void setDbActive(Character dbActive) {
        this.dbActive = dbActive;
    }

    public boolean isActive() {
        return this.dbActive.equals(Character.valueOf('Y'));
    }

    public void setActive(boolean active) {
        this.dbActive = Character.valueOf(active ? 'Y' : 'N');
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;
        return !(company.getId() == null || getId() == null) && Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCompany : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, companyName : \'" + companyName + '\'' +
            "\n\t, companyType : \'" + companyType + '\'' +
            "\n\t, email : \'" + email + '\'' +
            "\n\t, isActive : \'" + isActive() + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n};";
    }
}
