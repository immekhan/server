package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A privilege.
 */
@Entity
@Table(name = "ITOB_PRIVILEGES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privileges extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_PRIVILEGE",unique = true, nullable = false)
    private String id;

    @NotNull
    @Column(name = "STR_PRIVILEGE" , nullable = false)
    private String privilege;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Privileges privileges = (Privileges) o;
        return !(privileges.getId() == null || getId() == null) && Objects.equals(getId(), privileges.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nPrivilege : {" +
            "\n\t, privilege : '" + privilege + '\''+
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
