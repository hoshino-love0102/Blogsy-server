package com.example.blogsyserver.exception;

import com.example.blogsyserver.errorcode.AuthErrorCode;

public class DuplicateNicknameException extends BusinessException {
    public DuplicateNicknameException() {
        super(AuthErrorCode.DUPLICATE_NICKNAME);
    }
}
