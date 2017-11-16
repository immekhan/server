package com.itob.service.dto;


import com.itob.domain.DummyForm;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AbsDummyFormBlob entity.
 */
public class DummyFormDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String firstName;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String lastName;

    private String email;

    private String imageFileName;

    @Lob
    private byte[] imageFile;
    private String imageFileContentType;

    private String binaryFileName;

    @Lob
    private byte[] binaryFile;
    private String binaryFileContentType;

    @Lob
    private String clobTextField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public byte[] getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
    }

    public String getBinaryFileContentType() {
        return binaryFileContentType;
    }

    public void setBinaryFileContentType(String binaryFileContentType) {
        this.binaryFileContentType = binaryFileContentType;
    }

    public String getClobTextField() {
        return clobTextField;
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
        if(absDummyForm.getId() == null || getId() == null) {
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
            ", binaryFile='" + getBinaryFile() + "'" +
            ", clobTextField='" + getClobTextField() + "'" +
            "}";
    }
}
