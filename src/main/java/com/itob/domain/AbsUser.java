package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A user mapping
 */
@Entity
@Table(name = "ABS_USERS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AbsUser extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_USER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_DISPLAY_NAME", nullable = false)
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_ORGUNIT", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private OrgUnit orgUnit;

    @Column(name = "ID_BLACKLIST_REASON", nullable = false)
    private Long blackListReason;

    @Column(name = "STR_FIRST_NAME", nullable = true)
    private String firstName;

    @Column(name = "STR_LAST_NAME", nullable = true)
    private String lastName;

    @Column(name = "STR_Middle_NAME", nullable = true)
    private String middleName;

    @Basic(optional = false)
    @Column(name = "BOL_IS_ACTIVE", nullable = false)
    private Character dbActive = Character.valueOf('Y');

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_USER_TYPE", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private UserType userType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public Long getBlackListReason() {
        return blackListReason;
    }

    public void setBlackListReason(Long blackListReason) {
        this.blackListReason = blackListReason;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbsUser user = (AbsUser) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUser : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, displayName : \'" + displayName + '\'' +
            "\n\t, blackListReason : \'" + blackListReason + '\'' +
            "\n\t, firstName : \'" + firstName + '\'' +
            "\n\t, lastName : \'" + lastName + '\'' +
            "\n\t, middleName : \'" + middleName + '\'' +
            "\n\t, isActive : \'" + isActive() + '\'' +
            (userType!=null? "\n\t, userType : \'" + userType.toString()+ '\'':"") +
            (orgUnit!=null? "\n\t, idOrgUnit : \'" + orgUnit.getId()+ '\'':"") +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
