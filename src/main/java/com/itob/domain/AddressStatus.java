package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address Status.
 */
@Entity
@Table(name = "ITOB_ADDRESS_STATUS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressStatus extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ADDRESS_STATUS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_ADDRESS_STATUS" , nullable = false)
    private String addressStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressStatus addressStatus = (AddressStatus) o;
        return !(addressStatus.getId() == null || getId() == null) && Objects.equals(getId(), addressStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nAddressStatus : {" +
            "\n\tid: \'" + id + '\'' +
            "\n\t, addressStatus : \'" + addressStatus + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
