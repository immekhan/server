package com.itob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ITOBDummyForm.
 */
@Entity
@Table(name = "dummy_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DummyForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ENTITY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    @Column(name = "image_file_content_type")
    private String imageFileContentType;

    @Column(name = "binary_file_name")
    private String binaryFileName;

    @Lob
    @Column(name = "binary_file")
    private byte[] binaryFile;

    @Column(name = "binary_file_content_type")
    private String binaryFileContentType;

    @Lob
    @Column(name = "clob_text_field")
    private String clobTextField;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public DummyForm(){

    }

    public DummyForm(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public DummyForm firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public DummyForm lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public DummyForm email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public DummyForm imageFile(byte[] imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }

    public DummyForm imageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
        return this;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public byte[] getBinaryFile() {
        return binaryFile;
    }

    public DummyForm binaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
        return this;
    }

    public void setBinaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
    }

    public String getBinaryFileContentType() {
        return binaryFileContentType;
    }

    public DummyForm binaryFileContentType(String binaryFileContentType) {
        this.binaryFileContentType = binaryFileContentType;
        return this;
    }

    public void setBinaryFileContentType(String binaryFileContentType) {
        this.binaryFileContentType = binaryFileContentType;
    }

    public String getClobTextField() {
        return clobTextField;
    }

    public DummyForm clobTextField(String clobTextField) {
        this.clobTextField = clobTextField;
        return this;
    }

    public void setClobTextField(String clobTextField) {
        this.clobTextField = clobTextField;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getBinaryFileName() {
        return binaryFileName;
    }

    public void setBinaryFileName(String binaryFileName) {
        this.binaryFileName = binaryFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DummyForm absDummyForm = (DummyForm) o;
        if (absDummyForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), absDummyForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ITOBDummyForm{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageFile='" + getImageFile() + "'" +
            ", imageFileContentType='" + imageFileContentType + "'" +
            ", binaryFile='" + getBinaryFile() + "'" +
            ", binaryFileContentType='" + binaryFileContentType + "'" +
            ", clobTextField='" + getClobTextField() + "'" +
            "}";
    }
}
