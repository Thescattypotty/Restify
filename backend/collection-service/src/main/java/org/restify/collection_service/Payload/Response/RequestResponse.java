package org.restify.collection_service.Payload.Response;

import java.time.LocalDateTime;
import java.util.Map;

import org.restify.collection_service.Enum.EAuthType;
import org.restify.collection_service.Enum.EMethod;

public record RequestResponse(
    String id,
    EMethod method,
    String url,
    String body,
    Map<String, String> headers,
    EAuthType authType,
    String authToken,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
