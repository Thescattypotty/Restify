package org.restify.environment_service.Payload.Response;

import java.time.LocalDateTime;
import java.util.Map;

public record EnvironmentResponse(
    String id,
    String name,
    Map<String, String> variables,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
