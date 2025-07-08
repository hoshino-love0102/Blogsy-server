package com.example.blogsyserver.exception;

import com.example.blogsyserver.errorcode.AuthErrorCode;

public class LoginFailException extends BusinessException {
    public LoginFailException() {
        super(AuthErrorCode.LOGIN_FAIL);
    }
}