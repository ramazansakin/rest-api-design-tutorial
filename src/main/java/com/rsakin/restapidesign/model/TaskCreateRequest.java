package com.rsakin.restapidesign.model;

import java.util.Objects;

public record TaskCreateRequest(String headline, String detail) {

    public TaskCreateRequest {
        Objects.requireNonNull(headline); // Set headline as NotNull
    }

}