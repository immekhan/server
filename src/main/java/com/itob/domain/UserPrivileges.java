package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * ABS_USER_PRIVILEGES mapping
 */
@Entity
@Table(name = "USER_PRIVILEGES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPrivileges extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ENTITY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ID_USER" , nullable = false)
    private Long idUser;

    @Column(name = "ID_PRIVILEGE" , nullable = false)
    private String idPrivilege;

    @Column(name = "ID_ORGUNIT" , nullable = false)
    private String idOrgUnit;

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

    public String getIdPrivilege() {
        return idPrivilege;
    }

    public void setIdPrivilege(String idPrivilege) {
        this.idPrivilege = idPrivilege;
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

        UserPrivileges user = (UserPrivileges) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserPrivileges : {" +
            "\n\t, id : '" + id + '\'' +
            "\n\t,  idUser: '" + idUser + '\'' +
            "\n\t, idPrivilege : " + idPrivilege +
            "\n\t, idOrgUnit : " + idOrgUnit +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
