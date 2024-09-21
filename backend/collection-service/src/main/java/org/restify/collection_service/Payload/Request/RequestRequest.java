package org.restify.collection_service.Payload.Request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestRequest(
    @NotNull(message = "Method cannot be null")
    String method,

    @NotBlank(message = "URL cannot be blank")
    @Size(min = 5, max = 2083, message = "URL must be between 5 and 2083 characters")
    String url,

    @Size(max = 5000, message = "Request body cannot exceed 5000 characters")
    String body,

    Map<String, String> headers,

    @NotNull(message = "Auth type cannot be null")
    String authType,

    @Size(max = 1000, message = "Auth token cannot exceed 1000 characters")
    String authToken
) {
    
}
