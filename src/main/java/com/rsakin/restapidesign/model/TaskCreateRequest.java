package com.rsakin.restapidesign.model;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateRequest(@NotBlank String headline, String detail) {
}