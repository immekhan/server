package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A user addresses mapping
 */
@Entity
@Table(name = "USER_ADDRESSES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAddress extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ADDRESS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_USER", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private AbsUser user;

    @Column(name = "ID_ADDRESS_TYPE", nullable = false)
    private Long addressType;

    @Column(name = "ID_ADDRESS_STATUS", nullable = false)
    private Long addressStatus;

    @Column(name = "STR_ADDRESS", nullable = true)
    private String address;

    @Column(name = "ID_CITY", nullable = true)
    private Long city;

    @Column(name = "ID_COUNTRY", nullable = true)
    private Long country;

    @Column(name = "STR_PHONE", nullable = true)
    private String phone;

    @Column(name = "STR_FAX", nullable = true)
    private String fax;

    @Column(name = "STR_ADDRESS_NAME", nullable = false)
    private String addressName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAddress user = (UserAddress) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }


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

    public Long getAddressType() {
        return addressType;
    }

    public void setAddressType(Long addressType) {
        this.addressType = addressType;
    }

    public Long getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(Long addressStatus) {
        this.addressStatus = addressStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nUserAddress : {" +
            "\n\tid : \'" + id + '\'' +
            "\n\t, addressType : \'" + addressType + '\'' +
            "\n\t, addressStatus : \'" + addressStatus + '\'' +
            "\n\t, address : \'" + address + '\'' +
            "\n\t, city : \'" + city + '\'' +
            "\n\t, country : \'" + country + '\'' +
            "\n\t, phone : \'" + phone + '\'' +
            "\n\t, fax : \'" + fax + '\'' +
            "\n\t, addressName : \'" + addressName + '\'' +
            (user!=null? "\n\t, userType : \'" + user.toString()+ '\'':"") +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
