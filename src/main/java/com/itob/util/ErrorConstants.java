package com.itob.util;

public interface ErrorConstants {

    String ERROR_CODE_SUCCESS="00";
    String ERROR_CODE_SUCCESS_MSG= "Success";

    int DEFAULT_EXCEPTION_CODE = 9999;
    String DEFAULT_EXCEPTION_MSG = "Unknown error occurred! Please contact support";
    int ERROR_CODE_BAD_REQUEST=400;//Bad Request
    String ERROR_CODE_BAD_REQUEST_MSG ="Bad Request";//Bad Request

    int ERROR_CODE_USER_ALREADY_EXIST=1;//Username : {0} already exist.
    int ERROR_CODE_EMAIL_ALREADY_EXIST=2;//Email : {0} already exist.
    int ERROR_CODE_ID_ORG_UNIT_INVALID=3;//OrgUnitId :  {0} is invalid.

    //Bulk file error codes
    int ERROR_CODE_DEFAULT_FILE_UPLOADING=4;//Uploaded file contain errors
    int ERROR_CODE_FILE_CONTAIN_WHITE_SPACES=5;//Uploaded file contain white spaces
    int ERROR_CODE_FILE_INCOMPLETE_INFO = 6;//Uploaded file contain incomplete information
    int ERROR_CODE_FILE_HEADER_NOT_FOUND = 7;//Uploaded file dose not contain header
    int ERROR_CODE_FILE_INVALID_INFO = 8;//Uploaded file contain invalid information

}
