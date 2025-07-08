package com.example.blogsyserver.exception;

import com.example.blogsyserver.errorcode.AuthErrorCode;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(AuthErrorCode.DUPLICATE_EMAIL);
    }
}