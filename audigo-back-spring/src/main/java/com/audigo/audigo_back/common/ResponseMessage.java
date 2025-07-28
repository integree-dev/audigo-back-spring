package com.audigo.audigo_back.common;

public interface ResponseMessage {
    // http status code
    // HTTP Status 200
    String SUCCESS = "SUCCESS";

    // HTTP Status 400
    String VALIDATION_FAILED = "VALIDATION_FAILED";
    String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
    String DUPLICATE_NICKNAME = "DUPLICATE_NICKNAME";
    String DUPLICATE_TEL_NUMBER = "DUPLICATE_TEL_NUMBER";
    String NOT_EXISTED_USER = "NOT_EXISTED_USER";
    String NOT_EXISTED_BOARD = "NOT_EXISTED_BOARD";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SIGN_IN_FAIL";
    String AUTHORIZATION_FAIL = "AUTHORIZATION_FAIL";

    // HTTP Status 403
    String NO_PERMISSION = "NO_PERMISSION";

    // HTTP Status 500
    String DATABASE_ERROR = "DATABASE_ERROR";

}
