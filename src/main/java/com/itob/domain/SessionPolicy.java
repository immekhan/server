package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A role.
 */
@Entity
@Table(name = "ITOB_SESSION_POLICIES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SessionPolicy extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_SESSION_POLICY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "STR_SESSION_POLICY" , nullable = false)
    private String sessionPolicy;

    @NotNull
    @Column(name = "INT_MAX_SESSION_ALLOWED" , nullable = false)
    private Long countMaxSessionAllowed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionPolicy() {
        return sessionPolicy;
    }

    public void setSessionPolicy(String sessionPolicy) {
        this.sessionPolicy = sessionPolicy;
    }

    public Long getCountMaxSessionAllowed() {
        return countMaxSessionAllowed;
    }

    public void setCountMaxSessionAllowed(Long countMaxSessionAllowed) {
        this.countMaxSessionAllowed = countMaxSessionAllowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionPolicy sessionPolicy = (SessionPolicy) o;
        return !(sessionPolicy.getId() == null || getId() == null) && Objects.equals(getId(), sessionPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nSessionPolicy : {" +
            "\n\t, id : '" + id + '\'' +
            "\n\t, sessionPolicy : '" + sessionPolicy + '\'' +
            "\n\t, countMaxSessionAllowed : " + countMaxSessionAllowed +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
