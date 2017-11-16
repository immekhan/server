package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Company Type.
 */
@Entity
@Table(name = "ITOB_COMPANY_TYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyType extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_COMPANY_TYPE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_COMPANY_TYPE" , nullable = false)
    private String companyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyType companyType = (CompanyType) o;
        return !(companyType.getId() == null || getId() == null) && Objects.equals(getId(), companyType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCompanyType : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, companyType : \'" + companyType + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n};";
    }
}
