package com.itob.service.dto;

public class AbsUserDTO  extends BaseDTO {

    Long id;
    String displayName;
    String firstName;
    String middleName;
    String lastName;
    String username;
    Long userType;
    String email;
    boolean isActive;
    String password;
    private Long idCity;
    private Long idCountry;
    private String phone;
    private String fax;
    private String address;
    private FileDTO csvFile;
    private FileDTO imgFile;

    public AbsUserDTO() {

    }

    public AbsUserDTO(Long id, String displayName, String firstName,
                      String middleName, String lastName, String username,
                      Long userType, String email, boolean isActive, String password,
                      Long idCity, Long idCountry, String phone, String fax, String address, FileDTO csvFile,
                      FileDTO imgFile) {
        this.id = id;
        this.displayName = displayName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.userType = userType;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.idCity = idCity;
        this.idCountry = idCountry;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.csvFile = csvFile;
        this.imgFile = imgFile;
    }

    @Override
    public String toString() {
        return "UserDTO : {" +
            "\n\tid: \'" + id + '\'' +
            "\n\t, displayName : \'" + displayName + '\'' +
            "\n\t, firstName : \'" + firstName + '\'' +
            "\n\t, middleName : \'" + middleName + '\'' +
            "\n\t, lastName : \'" + lastName + '\'' +
            "\n\t, username : \'" + username + '\'' +
            "\n\t, userType : \'" + userType + '\'' +
            "\n\t, email : \'" + email + '\'' +
            "\n\t, isActive : \'" + isActive + '\'' +
            "\n\t, idCity : \'" + idCity + '\'' +
            "\n\t, idCountry : \'" + idCountry + '\'' +
            "\n\t, phone : \'" + phone + '\'' +
            "\n\t, fax : \'" + fax + '\'' +
            "\n\t, address : \'" + address + '\'' +
            "\n\t, csvFile : \'" + csvFile + '\'' +
            "\n\t, imgFile : \'" + imgFile + '\'' +
            "\n}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public Long getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FileDTO getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(FileDTO csvFile) {
        this.csvFile = csvFile;
    }

    public FileDTO getImgFile() {
        return imgFile;
    }

    public void setImgFile(FileDTO imgFile) {
        this.imgFile = imgFile;
    }
}
