package com.itob.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_USER_CREATED", nullable = false, updatable = false)
    @JsonIgnore
    private Long idCreatedBy;

    @Column(name = "DATE_CREATED", nullable = false)
    @JsonIgnore
    private Instant dateCreation = Instant.now();

    @Column(name = "ID_USER_UPDATED")
    @JsonIgnore
    private Long idUpdatedBy;

    @Column(name = "DATE_LAST_UPDATED")
    @JsonIgnore
    private Instant dateUpdated = Instant.now();

    public Long getIdCreatedBy() {
        return idCreatedBy;
    }

    public void setIdCreatedBy(Long idCreatedBy) {
        this.idCreatedBy = idCreatedBy;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getIdUpdatedBy() {
        return idUpdatedBy;
    }

    public void setIdUpdatedBy(Long idUpdatedBy) {
        this.idUpdatedBy = idUpdatedBy;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
