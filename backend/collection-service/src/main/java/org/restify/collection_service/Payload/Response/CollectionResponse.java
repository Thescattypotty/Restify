package org.restify.collection_service.Payload.Response;

import java.time.LocalDateTime;
import java.util.List;

public record CollectionResponse(
    String id,
    String name,
    String description,
    String environmentId,
    List<RequestResponse> requests,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
