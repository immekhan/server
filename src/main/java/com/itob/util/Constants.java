package com.itob.util;

public interface Constants {

    //Identification Types
    long IDENTIFICATION_TYPE_MOBILE_NO=0;
    long IDENTIFICATION_TYPE_USER_ID=1;
    long IDENTIFICATION_TYPE_USERNAME=5;
    long IDENTIFICATION_TYPE_EMAIL=7;

    //Credential Types
    long CREDENTIAL_TYPE_PIN=0;
    long CREDENTIAL_TYPE_PASSWORD=1;

    //Credential Status
    long CREDENTIAL_STATUS_OK = 0;
    long CREDENTIAL_STATUS_BLOCKED = 2;

    //Active/InActive
    char IS_ACTIVE='Y';
    char IS_INACTIVE='N';

    String DEFAULT_ORG_UNIT_ID="00";

    //Int values
    int INT_ZERO=0;
    int INT_ONE=1;

    //Boolean values
    boolean TRUE=true;
    boolean FALSE=false;

    //Language codes
    String CODE_LANG_ENGLISH="en";

    //Address Status
    long ADDRESS_VALID=1;
    long ADDRESS_NOT_VERIFIED=2;
    long ADDRESS_INVALID=3;

    //BlackListReasons
    long BLACKLIST_REASON_ACTIVE = 0l;

    //UserTypes
    long USER_TYPES_ADMINISTRATOR = 1l;

    //Address Type
    long ADDRESS_TYPE_POSTAL_AND_DELIVERY = 0l;

    // Wrong Credentials
    int COUNT_WRONG_CREDENTIALS = 3;

    //Roles
    String ROLE_ADMIN = "ROLE_ADMIN";

    String WILD_CARD_PERCENT ="%";

    String AUTH_KEY_USERNAME="username";
    String AUTH_KEY_CLIENT_ID="client_id";
    String AUTH_KEY_CLIENT_SECRET="client_secret";

    //
    String SFTP_SERVER_IP = "127.0.0.1";
    String SFTP_FILE_UPLOAD_PATH = "/UploadedFiles/";
    String SFTP_USER_NAME = "tester";
    String SFTP_USER_PASSWORD = "password";
}
