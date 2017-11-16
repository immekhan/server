package com.itob.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * User Credentials Mapping
 */
@Entity
@Table(name = "USER_CREDENTIALS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserCredentials extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_USER_CREDENTIAL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ID_USER", nullable = false)
    private Long idUser;

    @Column(name = "ID_CREDENTIAL_TYPE", nullable = false)
    private Long credentialType;

    @JsonIgnore
    @Column(name = "STR_CREDENTIAL", nullable = false)
    private String credential;

    @Basic(optional = false)
    @Column(name = "BOL_IS_ACTIVE", nullable = false)
    private Character dbActive = Character.valueOf('Y');

    @Column(name = "ID_CREDENTIAL_STATUS", nullable = false)
    private Long credentialStatus;

    @Column(name = "INT_COUNT_WRNG_CRED", nullable = false)
    private Integer countWrongCredential;

    @JsonIgnore
    @Column(name = "DATE_BLOCK_UNTIL")
    private Instant dateBlockUntil = Instant.now();

    @JsonIgnore
    @Column(name = "DATE_LAST_USED")
    private Instant dateLastUsed = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(Long credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public Character getDbActive() {
        return dbActive;
    }

    public void setDbActive(Character dbActive) {
        this.dbActive = dbActive;
    }

    public Long getCredentialStatus() {
        return credentialStatus;
    }

    public void setCredentialStatus(Long credentialStatus) {
        this.credentialStatus = credentialStatus;
    }

    public Integer getCountWrongCredential() {
        return countWrongCredential;
    }

    public void setCountWrongCredential(Integer countWrongCredential) {
        this.countWrongCredential = countWrongCredential;
    }

    public Instant getDateBlockUntil() {
        return dateBlockUntil;
    }

    public void setDateBlockUntil(Instant dateBlockUntil) {
        this.dateBlockUntil = dateBlockUntil;
    }

    public Instant getDateLastUsed() {
        return dateLastUsed;
    }

    public void setDateLastUsed(Instant dateLastUsed) {
        this.dateLastUsed = dateLastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserCredentials userCredentails = (UserCredentials) o;
        return !(userCredentails.getId() == null || getId() == null) && Objects.equals(getId(), userCredentails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserCredentials{" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, credentialType : \'" + credentialType + '\'' +
            "\n\t, credentialStatus : \'" + credentialStatus + '\'' +
            "\n\t, countWrongCredential : \'" + countWrongCredential + '\'' +
            "\n\t, dateBlockUntil : \'" + dateBlockUntil + '\'' +
            "\n\t, dateLastUsed : \'" + dateLastUsed + '\'' +
            "\n\t, idUser : \'" + idUser + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
