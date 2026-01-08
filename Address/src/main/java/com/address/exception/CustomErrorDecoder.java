package com.address.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        switch (status) {
            case 400:
                return new RuntimeException("Bad Request from downstream service");

            case 401:
                return new RuntimeException("Unauthorized request");

            case 403:
                return new RuntimeException("Forbidden request");

            case 404:
                return new RuntimeException("Requested resource not found");

            case 500:
                return new RuntimeException("Internal Server Error from downstream service");

            case 503:
                return new RuntimeException("Service is temporarily unavailable. Please try again later.");

            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
