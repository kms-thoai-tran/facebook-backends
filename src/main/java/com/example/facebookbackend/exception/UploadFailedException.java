package com.example.facebookbackend.exception;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.SdkResponse;
import software.amazon.awssdk.http.SdkHttpResponse;

import java.util.Optional;

@AllArgsConstructor
public class UploadFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Optional<String> statusText;

    public UploadFailedException(SdkResponse response) {

        SdkHttpResponse httpResponse = response.sdkHttpResponse();
        if (httpResponse != null) {
            this.statusText = httpResponse.statusText();
        } else {
            this.statusText = Optional.of("UNKNOWN");
        }

    }
}
