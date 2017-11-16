package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Credential Policy.
 */
@Entity
@Table(name = "CREDENTIAL_POLICIES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CredentialPolicy extends AbsAbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_CREDENTIAL_POLICY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "STR_CREDENTIAL_POLICY" , nullable = false)
    private String credentialPolicy;

    @Column(name = "INT_MIN_CRED_LENGTH" , nullable = false)
    private Integer minLength;

    @Column(name = "INT_MAX_CRED_LENGTH" , nullable = false)
    private Integer maxLength;

    @Column(name = "INT_DAYS_PASS_TIMEOUT" , nullable = true)
    private Integer countOfDaysPasswordTimeout;

    @Column(name = "INT_BLOCK_SIZE" , nullable = false)
    private Integer countBlockSize;

    @Column(name = "INT_BLOCK_THRESHOLD" , nullable = true)
    private Integer thresholdVal;

    @Column(name = "INT_PASS_RETENTION" , nullable = false)
    private Integer passwordRetention;

    @Column(name = "INT_MIN_BLOCK_ACCOUNT" , nullable = false)
    private Integer minBlockAccount;

    @Column(name = "INT_DAYS_UNUSED_TIMEOUT" , nullable = true)
    private Integer noOfDaysUnusedTimeout;

    @Column(name = "INT_DAYS_TEMP_TIMEOUT" , nullable = true)
    private Integer noOfDaysTempTimeout;

    @Column(name = "STR_REGEX" , nullable = false)
    private String regex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCredentialPolicy() {
        return credentialPolicy;
    }

    public void setCredentialPolicy(String credentialPolicy) {
        this.credentialPolicy = credentialPolicy;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getCountOfDaysPasswordTimeout() {
        return countOfDaysPasswordTimeout;
    }

    public void setCountOfDaysPasswordTimeout(Integer countOfDaysPasswordTimeout) {
        this.countOfDaysPasswordTimeout = countOfDaysPasswordTimeout;
    }

    public Integer getCountBlockSize() {
        return countBlockSize;
    }

    public void setCountBlockSize(Integer countBlockSize) {
        this.countBlockSize = countBlockSize;
    }

    public Integer getThresholdVal() {
        return thresholdVal;
    }

    public void setThresholdVal(Integer thresholdVal) {
        this.thresholdVal = thresholdVal;
    }

    public Integer getPasswordRetention() {
        return passwordRetention;
    }

    public void setPasswordRetention(Integer passwordRetention) {
        this.passwordRetention = passwordRetention;
    }

    public Integer getMinBlockAccount() {
        return minBlockAccount;
    }

    public void setMinBlockAccount(Integer minBlockAccount) {
        this.minBlockAccount = minBlockAccount;
    }

    public Integer getNoOfDaysUnusedTimeout() {
        return noOfDaysUnusedTimeout;
    }

    public void setNoOfDaysUnusedTimeout(Integer noOfDaysUnusedTimeout) {
        this.noOfDaysUnusedTimeout = noOfDaysUnusedTimeout;
    }

    public Integer getNoOfDaysTempTimeout() {
        return noOfDaysTempTimeout;
    }

    public void setNoOfDaysTempTimeout(Integer noOfDaysTempTimeout) {
        this.noOfDaysTempTimeout = noOfDaysTempTimeout;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CredentialPolicy credentialPolicy = (CredentialPolicy) o;
        return !(credentialPolicy.getId() == null || getId() == null) && Objects.equals(getId(), credentialPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nCredentialPolicy : {" +
            "\n\tid: \'" + id + '\'' +
            "\n\t, credentialPolicy : '" + credentialPolicy + '\'' +
            "\n\t, minLength : " + minLength  +
            "\n\t, maxLength : " + maxLength +
            "\n\t, countOfDaysPasswordTimeout : " + countOfDaysPasswordTimeout +
            "\n\t, countBlockSize : " + countBlockSize  +
            "\n\t, thresholdVal : " + thresholdVal  +
            "\n\t, passwordRetention : " + passwordRetention +
            "\n\t, minBlockAccount : " + minBlockAccount +
            "\n\t, noOfDaysUnusedTimeout : " + noOfDaysUnusedTimeout +
            "\n\t, noOfDaysTempTimeout : " + noOfDaysTempTimeout +
            "\n\t, idCreatedBy : \'" + getIdCreatedBy() + '\'' +
            "\n\t, dateCreation : \'" + getDateCreation() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n\t, idUpdatedBy : \'" + getIdUpdatedBy() + '\'' +
            "\n}";
    }
}
