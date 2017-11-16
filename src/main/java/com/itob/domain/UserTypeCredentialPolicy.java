package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ABS_USER_TYPE_CRED_POLICY Mapping.
 * Based on the User type and Credential Type , Credential Policy is applied
 */
@Entity
@Table(name = "ITOB_USER_TYPE_CRED_POLICY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserTypeCredentialPolicy extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ENTITY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_USER_TYPE", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_CREDENTIAL_TYPE", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private CredentialType credentialType;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_CREDENTIAL_POLICY", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private CredentialPolicy credentialPolicy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public CredentialPolicy getCredentialPolicy() {
        return credentialPolicy;
    }

    public void setCredentialPolicy(CredentialPolicy credentialPolicy) {
        this.credentialPolicy = credentialPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserTypeCredentialPolicy user = (UserTypeCredentialPolicy) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserTypeCredentialPolicy : {" +
            "\n\t, id : '" + id + '\'' +
            (userType!=null? "\n\t, userType : \'" + userType.getId()+ '\'':"") +
            (credentialPolicy!=null? "\n\t, credentialPolicy : \'" + credentialPolicy.toString()+ '\'':"") +
            (credentialType!=null? "\n\t, credentialType : \'" + credentialType.toString()+ '\'':"") +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
