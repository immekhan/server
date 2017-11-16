package com.itob.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="ERROR_CODES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ErrorCode extends AbsAbstractAuditingEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ERROR_CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Basic(optional=false)
    @Column(name="ID_ERROR_LEVEL", nullable=false,length = 80)
    private String level;

    @Basic(optional=false)
    @Column(name="STR_INFORMATION", nullable=false,length = 200)
    private String info;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ErrorCode errorCode = (ErrorCode) o;
        return !(errorCode.getId() == null || getId() == null) && Objects.equals(getId(), errorCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nError Code : {" +
            "\n\tid: \'" + id + '\'' +
            "\n\t, level: \'" + level + '\'' +
            "\n\t, info: \'" + info + '\'' +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }

    public boolean isSetLevel() {
        return level != null;
    }

    public boolean isSetInfo() {
        return info != null;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
