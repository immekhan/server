package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * ABS_USER_TYPE mapping
 */
@Entity
@Table(name = "ITOB_USER_TYPE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserType extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_USER_TYPE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_USER_TYPE", nullable = false)
    private String userType;

    @Column(name = "ID_ROLE", nullable = false)
    private String idRole;

    @Column(name = "ID_ORGUNIT", nullable = false)
    private String idOrgUnit;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_SESSION_POLICY", nullable = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private SessionPolicy sessionPolicy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public SessionPolicy getSessionPolicy() {
        return sessionPolicy;
    }

    public void setSessionPolicy(SessionPolicy sessionPolicy) {
        this.sessionPolicy = sessionPolicy;
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

        UserType user = (UserType) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserType: {" +
            "\n\t, idUserType : '" + id + '\'' +
            "\n\t,  userType: '" + userType + '\'' +
            "\n\t, idRole : " + idRole +
            "\n\t, idOrgUnit : " + idOrgUnit +
            (sessionPolicy!=null? "\n\t, sessionPolicy : \'" + sessionPolicy.toString()+ '\'':"") +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
