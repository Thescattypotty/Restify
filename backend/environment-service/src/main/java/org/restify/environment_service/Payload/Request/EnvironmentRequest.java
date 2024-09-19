package org.restify.environment_service.Payload.Request;

import java.util.Map;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EnvironmentRequest(
    
    @NotEmpty
    String name,

    @NotNull
    Map<String, String> variables
    
) {
    
}
