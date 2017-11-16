package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * ABS_SESSION mapping
 */
@Entity
@Table(name = "ABS_SESSION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "STR_SESSION_ID", unique = true, nullable = false)
    private String id;

    @Column(name = "ID_USER" ,nullable = false)
    private Long idUser;

    @Column(name = "STR_IDENTIFICATION" ,nullable = false)
    private String identification;


    @NotNull
    @Column(name = "ID_ORGUNIT", nullable = true)
    private String idOrgUnit;

    @Column(name = "DATE_LOGIN", nullable = true)
    private Instant loginDate = Instant.now();

    @Column(name = "DAT_LOGOFF", nullable = true)
    private Instant logOffDate = Instant.now();

    @Column(name = "DAT_LAST_ACTIVITY", nullable = false)
    private Instant lastActivityDate = Instant.now();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIdOrgUnit() {
        return idOrgUnit;
    }

    public void setIdOrgUnit(String idOrgUnit) {
        this.idOrgUnit = idOrgUnit;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public Instant getLogOffDate() {
        return logOffDate;
    }

    public void setLogOffDate(Instant logOffDate) {
        this.logOffDate = logOffDate;
    }

    public Instant getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Instant lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session absOrgUnit = (Session) o;
        return !(absOrgUnit.getId() == null || getId() == null) && Objects.equals(getId(), absOrgUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nAbsSession{" +
            "\n\tsessionId ='" + id + '\'' +
            "\n\t, idUser ='" + idUser + '\'' +
            "\n\t, identification ='" + identification + '\'' +
            "\n\t, idOrgUnit ='" + idOrgUnit + '\'' +
            "\n\t, loginDate ='" + loginDate + '\'' +
            "\n\t, logOffDate ='" + logOffDate + '\'' +
            "\n\t, lastActivityDate ='" + lastActivityDate + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
