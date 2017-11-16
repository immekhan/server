package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Credential Type.
 */
@Entity
@Table(name = "ITOB_CREDENTIAL_TYPES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CredentialType extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_CREDENTIAL_TYPE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_CREDENTIAL_TYPE" , nullable = false)
    private String credentialType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CredentialType credentialType = (CredentialType) o;
        return !(credentialType.getId() == null || getId() == null) && Objects.equals(getId(), credentialType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCredentialType : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, credentialType : \'" + credentialType + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
