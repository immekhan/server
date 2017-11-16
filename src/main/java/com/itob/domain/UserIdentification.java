package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A user.
 */
@Entity
@Table(name = "ITOB_USER_IDENTIFICATIONS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserIdentification extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_USER_IDENTIFICATION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_USER", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private AbsUser user;

    @Column(name = "ID_IDENTIFICATION_TYPE", nullable = false)
    private Long identificationType;

    @Column(name = "STR_IDENTIFICATION", nullable = false)
    private String identification;

    @Basic(optional = false)
    @Column(name = "BOL_IS_ACTIVE", nullable = false)
    private Character dbActive = Character.valueOf('Y');

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_ORGUNIT", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private OrgUnit orgUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbsUser getUser() {
        return user;
    }

    public void setUser(AbsUser user) {
        this.user = user;
    }

    public Long getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(Long identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
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

        UserIdentification userIdentification = (UserIdentification) o;
        return !(userIdentification.getId() == null || getId() == null) && Objects.equals(getId(), userIdentification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserIdentification : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, identificationType : \'" + identificationType + '\'' +
            "\n\t, identificationValue : \'" + identification + '\'' +
            "\n\t, isActive : \'" + isActive() + '\'' +
            (user!=null? "\n\t, userType : \'" + user.toString()+ '\'':"") +
            (orgUnit!=null? "\n\t, idOrgUnit : \'" + orgUnit.getId()+ '\'':"") +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
