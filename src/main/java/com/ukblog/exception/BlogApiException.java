package com.ukblog.exception;

public class BlogApiException extends RuntimeException {
    public BlogApiException(String message){
        super(message);
    }
}
