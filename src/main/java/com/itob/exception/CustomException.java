package com.itob.exception;

import com.itob.util.ErrorConstants;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    public CustomException(){
        super();
    }

    public CustomException(String message){
        super(message);
    }

    public CustomException(String message, Throwable cause){
        super(message,cause);
    }

    public CustomException(Throwable cause){
        super(ErrorConstants.DEFAULT_EXCEPTION_MSG, cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
