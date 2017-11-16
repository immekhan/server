package com.itob.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "ITOB_COMP_DEV_TYP_CRED")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDeviceTypeCredential extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ENTITY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ID_APP" , nullable = false)
    private Long idApp;

    @Column(name = "STR_CLIENT_ID" , nullable = false)
    private String idClient;

    @JsonIgnore
    @Column(name = "STR_CREDENTIALS" )
    private String clientCredentials;

    @Column(name = "DAT_EXPIRY")
    private Instant dateExpiry = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdApp() {
        return idApp;
    }

    public void setIdApp(Long idApp) {
        this.idApp = idApp;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getClientCredentials() {
        return clientCredentials;
    }

    public void setClientCredentials(String clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    public Instant getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(Instant dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDeviceTypeCredential companyDeviceTypeCredential = (CompanyDeviceTypeCredential) o;
        return !(companyDeviceTypeCredential.getId() == null || getId() == null) && Objects.equals(getId(), companyDeviceTypeCredential.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCompanyDeviceTypeCredential : {" +
            "\n\tid : \'" + id  + '\'' +
            "\n\t, idApp : \'" + idApp + '\'' +
            "\n\t, idClient : \'" + idClient + '\'' +
            "\n\t, dateExpiry : \'" + dateExpiry + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
